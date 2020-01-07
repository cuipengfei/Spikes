/**
 * Copyright (C) 2013-2015 Typesafe Inc. <http://www.typesafe.com>
 */
package kvstore

import akka.actor.Props
import akka.testkit.TestProbe
import org.junit.Assert._
import org.junit.Test

trait IntegrationSpec { this: KVStoreSuite =>

  import Arbiter._

  @Test def `Integration-case1: Primary and secondaries must work in concert when persistence is unreliable (35pts)`(): Unit = {
    integrate(true, false, 1)
  }

  @Test def `Integration-case2: Primary and secondaries must work in concert when communication to secondaries is unreliable (35pts)`(): Unit = {
    integrate(false, true, 2)
  }

  @Test def `Integration-case3: Primary and secondaries must work in concert when both persistence and communication to secondaries are unreliable (35 pts)`(): Unit = {
    integrate(true, true, 3)
  }

  def integrate(flaky: Boolean, lossy: Boolean, nr: Int): Unit = {
    val arbiterProbe = TestProbe()
    val arbiter = system.actorOf(Props(classOf[given.Arbiter], lossy, arbiterProbe.ref))
    val primary = system.actorOf(Replica.props(arbiter, given.Persistence.props(flaky)), s"integration-case$nr-primary")
    val client1 = session(primary)
    try arbiterProbe.expectMsg(JoinedPrimary)
    catch {
      case _: AssertionError => fail("primary replica did not join the Arbiter within 3 seconds")
    }

    val secondary1 = system.actorOf(Replica.props(arbiter, given.Persistence.props(flaky)), s"integration-case$nr-secondary1")
    val client2 = session(secondary1)

    client1.getAndVerify("k1")
    client1.setAcked("k1", "v1")
    client1.getAndVerify("k1")
    client1.setAcked("k1", "v11")
    client1.getAndVerify("k2")
    client1.setAcked("k2", "v2")
    client1.getAndVerify("k2")

    arbiterProbe.awaitAssert {
      assertEquals(Some("v11"), client2.get("k1"))
      assertEquals(Some("v2"), client2.get("k2"))
    }

    client1.removeAcked("k1")
    client1.getAndVerify("k1")

    arbiterProbe.awaitAssert {
      assertEquals(Some("v2"), client2.get("k2"))
    }

    // Join a replica later
    val secondary2 = system.actorOf(Replica.props(arbiter, given.Persistence.props(flaky)), s"integration-case$nr-secondary2")
    val client3 = session(secondary2)

    // Wait for replication...
    arbiterProbe.awaitAssert {
      assertEquals(Some("v2"), client3.get("k2"))
      assertEquals(None, client3.get("k1"))
    }

    client1.setAcked("k1", "v111")
    client1.setAcked("k3", "v3")
    client1.removeAcked("k2")

    // Wait for replication...
    arbiterProbe.awaitAssert {
      assertEquals(None, client2.get("k2"))
      assertEquals(Some("v111"), client2.get("k1"))
      assertEquals(Some("v3"), client2.get("k3"))
    }
    arbiterProbe.awaitAssert {
      assertEquals(None, client3.get("k2"))
      assertEquals(Some("v111"), client3.get("k1"))
      assertEquals(Some("v3"), client3.get("k3"))
    }
  }
}
