package mapreduce

import (
	"fmt"
	"log"
	"net"
	"net/rpc"
	"os"
)

// Shutdown is an RPC method that shuts down the Master's RPC server.
func (master *Master) Shutdown(_, _ *struct{}) error {
	debug("Shutdown: registration server\n")
	close(master.shutdown)
	master.listener.Close() // causes the Accept to fail
	return nil
}

// startRPCServer starts the Master's RPC server. It continues accepting RPC
// calls (Register in particular) for as long as the worker is alive.
func (master *Master) startRPCServer() {
	rpcs := rpc.NewServer()
	rpcs.Register(master)
	os.Remove(master.address) // only needed for "unix"
	listener, err := net.Listen("unix", master.address)
	if err != nil {
		log.Fatal("RegstrationServer", master.address, " err: ", err)
	}
	master.listener = listener

	// now that we are listening on the master address, can fork off
	// accepting connections to another thread.
	go func() {
	loop:
		for {
			select {
			case <-master.shutdown:
				break loop
			default:
			}
			connection, err := master.listener.Accept()
			if err == nil {
				go func() {
					rpcs.ServeConn(connection)
					connection.Close()
				}()
			} else {
				debug("RegistrationServer: accept err", err)
				break
			}
		}
		debug("RegistrationServer: done\n")
	}()
}

// stopRPCServer stops the master RPC server.
// This must be done through an RPC to avoid race conditions between the RPC
// server thread and the current thread.
func (master *Master) stopRPCServer() {
	var reply ShutdownReply
	ok := call(master.address, "Master.Shutdown", new(struct{}), &reply)
	if ok == false {
		fmt.Printf("Cleanup: RPC %s error\n", master.address)
	}
	debug("cleanupRegistration: done\n")
}
