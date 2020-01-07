package kvstore

import akka.testkit.TestProbe

import scala.concurrent.duration._
import Arbiter._
import Persistence._
import org.junit.Assert._
import org.junit.Test

trait Step4_SecondaryPersistenceSpec { this: KVStoreSuite =>

  @Test def `Step4-case1: Secondary should not acknowledge snapshots until persisted`(): Unit = {
    import Replicator._

    val arbiter = TestProbe()
    val persistence = TestProbe()
    val replicator = TestProbe()
    val secondary = system.actorOf(Replica.props(arbiter.ref, probeProps(persistence)), "step4-case1-secondary")
    val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(None, client.get("k1"))

    replicator.send(secondary, Snapshot("k1", Some("v1"), 0L))
    val persistId = persistence.expectMsgPF() {
      case Persist("k1", Some("v1"), id) => id
    }

    assertEquals("secondary replica should already serve the received update while waiting for persistence: ", Some("v1"), client.get("k1"))

    replicator.expectNoMessage(500.milliseconds)

    persistence.reply(Persisted("k1", persistId))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(Some("v1"), client.get("k1"))
  }

  @Test def `Step4-case2: Secondary should retry persistence in every 100 milliseconds`(): Unit = {
    import Replicator._

    val arbiter = TestProbe()
    val persistence = TestProbe()
    val replicator = TestProbe()
    val secondary = system.actorOf(Replica.props(arbiter.ref, probeProps(persistence)), "step4-case2-secondary")
    val client = session(secondary)

    arbiter.expectMsg(Join)
    arbiter.send(secondary, JoinedSecondary)

    assertEquals(None, client.get("k1"))

    replicator.send(secondary, Snapshot("k1", Some("v1"), 0L))
    val persistId = persistence.expectMsgPF() {
      case Persist("k1", Some("v1"), id) => id
    }

    assertEquals("secondary replica should already serve the received update while waiting for persistence: ", Some("v1"), client.get("k1"))

    // Persistence should be retried
    persistence.expectMsg(300.milliseconds, Persist("k1", Some("v1"), persistId))
    persistence.expectMsg(300.milliseconds, Persist("k1", Some("v1"), persistId))

    replicator.expectNoMessage(500.milliseconds)

    persistence.reply(Persisted("k1", persistId))
    replicator.expectMsg(SnapshotAck("k1", 0L))
    assertEquals(Some("v1"), client.get("k1"))
  }

}
