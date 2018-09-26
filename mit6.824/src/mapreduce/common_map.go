package mapreduce

import (
	"encoding/json"
	"hash/fnv"
	"log"
	"os"
)

func doMap(
	jobName string, // the name of the MapReduce job
	mapTaskNumber int, // which map task this is
	inFile string,
	nReduce int, // the number of reduce task that will be run ("R" in the paper)
	mapF func(filename string, contents string) []KeyValue,
) {
	middle_res := readFile(inFile, mapF)
	rSize := len(middle_res)

	for i := 0; i < nReduce; i++ {
		createSmallerFile(jobName, mapTaskNumber, i, rSize, middle_res, nReduce)
	}
}

func createSmallerFile(jobName string, mapTaskNumber int, i int, rSize int, middle_res []KeyValue, nReduce int) {
	mid_file, err := os.Create(reduceName(jobName, mapTaskNumber, i))
	if err != nil {
		log.Fatal("ERROR[doMap]: Create intermediate file fail ", err)
	}
	enc := json.NewEncoder(mid_file)
	for j := 0; j < rSize; j++ {
		kv := middle_res[j]
		if ihash(kv.Key)%nReduce == i {
			err := enc.Encode(&kv)
			if err != nil {
				log.Fatal("ERROR[doMap]: Encode error: ", err)
			}
		}
	}
	mid_file.Close()
}

func readFile(inFile string, mapF func(filename string, contents string) []KeyValue) []KeyValue {
	file, err := os.Open(inFile)
	if err != nil {
		log.Fatal("ERROR[doMap]: Open file error ", err)
	}

	fileStat, err := file.Stat()
	if err != nil {
		log.Fatal("ERROR[doMap]: Get file state error ", err)
	}

	fileSize := fileStat.Size()
	buffer := make([]byte, fileSize)
	_, err = file.Read(buffer)
	if err != nil {
		log.Fatal("ERROR[doMap]:Read error ", err)
	}

	middle_res := mapF(inFile, string(buffer))
	file.Close()
	return middle_res
}

func ihash(s string) int {
	h := fnv.New32a()
	h.Write([]byte(s))
	return int(h.Sum32() & 0x7fffffff)
}
