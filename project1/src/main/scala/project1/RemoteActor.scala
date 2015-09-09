package project1

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

class RemoteActor(numOfZeros: Long, start: Long, end: Long) extends Actor {
  def receive = {
    case msg: String =>
      if (msg.equals("Need work!")) {
        sender ! Job(numOfZeros, start, end)
      }
      else {
        println(s"RemoteActor received message '$msg'")
      }
  }
}