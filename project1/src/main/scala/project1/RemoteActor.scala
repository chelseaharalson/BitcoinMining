package project1

import akka.actor._
import scala.language.postfixOps

/**
 * Created by chelsea on 9/6/15.
 */

class RemoteActor(numOfZeros: Long, start: Long, end: Long) extends Actor {
  def receive = {
    case msg: String =>
      if (msg.equals("Need work!"))
      {
        sender ! Job(numOfZeros, start, end)
        //sender ! "Hello from the RemoteActor"
      }
      else {
        println(s"RemoteActor received message '$msg'")
      }
  }
}