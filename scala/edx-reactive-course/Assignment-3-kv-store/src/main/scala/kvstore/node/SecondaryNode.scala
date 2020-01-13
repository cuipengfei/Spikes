package kvstore.node

import kvstore.Persistence.{Persist, Persisted}
import kvstore.Replica
import kvstore.Replica.{Get, GetResult}
import kvstore.Replicator.{Snapshot, SnapshotAck}

trait SecondaryNode {
  this: Replica =>

  private var expectedSeq = 0L

  private def nextSeq(): Long = {
    val ret = expectedSeq
    expectedSeq += 1
    ret
  }

  val secondary: Receive = {
    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)

    case snapshot@Snapshot(k, _, seq) =>
      if (seq == expectedSeq) {
        updateAndPersist(snapshot)
      } else if (seq < expectedSeq) {
        println("seq < expected seq happened")
        sender() ! SnapshotAck(k, seq)
      } // ignore seq > expectedSeq

    case Persisted(k, seq) =>
      pendingPersists.get(seq).foreach { case (replicator, _) =>
        replicator ! SnapshotAck(k, seq)
        nextSeq()
        pendingPersists -= seq
      }

    case _ =>
  }

  private val updateAndPersist: PartialFunction[Snapshot, Unit] = {
    case Snapshot(k, vOption, seq) =>
      if (vOption.isDefined) kv += (k -> vOption.get)
      else kv -= k

      goPersist(seq, sender(), Persist(k, vOption, seq))
  }

}
