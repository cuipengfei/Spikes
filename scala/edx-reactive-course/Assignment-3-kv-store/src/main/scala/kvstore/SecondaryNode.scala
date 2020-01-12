package kvstore

import kvstore.Persistence.{Persist, Persisted}
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
      } else if (seq < expectedSeq && isPersistFinished(seq)) {
        sender() ! SnapshotAck(k, seq)
      } // ignore seq > expectedSeq

    case Persisted(k, seq) =>
      pendingPersists.get(seq).foreach { case (replicator, _) =>
        replicator ! SnapshotAck(k, seq)
        pendingPersists -= seq
      }

    case _ =>
  }

  private def updateAndPersist: PartialFunction[Snapshot, Unit] = {
    case Snapshot(k, v, seq) =>
      if (v.isDefined) kv += (k -> v.get)
      else kv -= k

      goPersist(seq, sender(), Persist(k, v, seq))

      nextSeq()
  }

}
