package kvstore

import kvstore.Persistence.{Persist, Persisted}
import kvstore.Replica.{Get, GetResult}
import kvstore.Replicator.{Snapshot, SnapshotAck}

trait SecondaryNode {
  this: Replica =>

  private var expectedSeq = 0L

  def nextSeq(): Long = {
    val ret = expectedSeq
    expectedSeq += 1
    ret
  }

  val secondary: Receive = {
    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)

    case Snapshot(k, vOption, seq) =>
      if (seq == expectedSeq) {
        update(k, vOption, seq)
      } else if (seq < expectedSeq) {
        if (isPersistFinished(seq)) sender() ! SnapshotAck(k, seq)
      } // ignore seq > expectedSeq

    case Persisted(k, seq) =>
      val (replicator, _) = pendingPersists(seq)
      replicator ! SnapshotAck(k, seq)
      pendingPersists -= seq

    case _ =>
  }

  private def update(k: String, vOption: Option[String], seq: Long): Long = {
    if (vOption.isDefined) kv += (k -> vOption.get)
    else kv -= k

    goPersist(seq, sender(), Persist(k, vOption, seq))

    nextSeq()
  }

}
