package kvstore

import akka.testkit.TestProbe

import scala.concurrent.duration._
import kvstore.Arbiter.{Join, JoinedSecondary}
import org.junit.Assert._
import org.junit.Test

import scala.util.Random
import scala.util.control.NonFatal

trait Step2_SecondarySpec { this: KVStoreSuite =>

  @Test def `Step2-case1: Secondary (in isolation) should properly register itself to the provided Arbiter`(): Unit = {
    val arbiter = TestProbe()
        system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case1-secondary")
    
    arbiter.expectMsg(Join)
    ()
  }

  @Test def `Step2-case2: Secondary (in isolation) must handle Snapshots`(): Unit = {
    import Replicator._

    val arbiter = TestProbe()
    val replicator = TestProbe()
        val secondary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case2-secondary")
        val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(None, client.get("k1"))

    replicator.send(secondary, Snapshot("k1", None, 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(None, client.get("k1"))

    replicator.send(secondary, Snapshot("k1", Some("v1"), 1L))
    replicator.expectMsg(SnapshotAck("k1", 1L))
    assertEquals(Some("v1"), client.get("k1"))

    replicator.send(secondary, Snapshot("k1", None, 2L))
    replicator.expectMsg(SnapshotAck("k1", 2L))
    assertEquals(None, client.get("k1"))
  }

  @Test def `Step2-case3: Secondary should drop and immediately ack snapshots with older sequence numbers`(): Unit = {
    import Replicator._

    val arbiter = TestProbe()
    val replicator = TestProbe()
        val secondary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case3-secondary")
        val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(None, client.get("k1"))

    replicator.send(secondary, Snapshot("k1", Some("v1"), 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(Some("v1"), client.get("k1"))

    replicator.send(secondary, Snapshot("k1", None, 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(Some("v1"), client.get("k1"))

    replicator.send(secondary, Snapshot("k1", Some("v2"), 1L))
    replicator.expectMsg(SnapshotAck("k1", 1L))
    assertEquals(Some("v2"), client.get("k1"))

    replicator.send(secondary, Snapshot("k1", None, 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(Some("v2"), client.get("k1"))
  }

  @Test def `Step2-case4: Secondary should drop snapshots with future sequence numbers`(): Unit = {
    import Replicator._

    val arbiter = TestProbe()
    val replicator = TestProbe()
        val secondary = system.actorOf(Replica.props(arbiter.ref, Persistence.props(flaky = false)), "step2-case4-secondary")
        val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(None, client.get("k1"))

    replicator.send(secondary, Snapshot("k1", Some("v1"), 1L))
    replicator.expectNoMessage(300.milliseconds)
    assertEquals(None, client.get("k1"))

    replicator.send(secondary, Snapshot("k1", Some("v2"), 0L))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(Some("v2"), client.get("k1"))
  }

  
}
