package project1

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

class RemoteActor(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0

  def receive = {
    case msg: String =>
      if (msg.equals("Need work!")) {
        start = start + 10000
        end = start + 10000
        println("Start: " + start)
        sender ! Job(numOfZeros, start, end)
      }
      else {
        println(s"RemoteActor received message '$msg'")
      }
  }
}