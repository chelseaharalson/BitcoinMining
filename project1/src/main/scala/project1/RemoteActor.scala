package project1

import java.net.InetAddress

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

class RemoteActor(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0
  val remote = context.actorSelection("akka.tcp://MasterSystem@" + InetAddress.getLocalHost.getHostAddress + ":8397/user/RemoteActor")

  def receive = {
    case msg: String => {
      if (msg.equals("Need work!")) {
        start = start + 10000
        end = start + 10000
        println("Start Worker: " + start)
        sender ! Job(numOfZeros, start, end)
      }
      else if (msg.equals("I need work!")) {
        start = start + 10000
        end = start + 10000
        println("Start Server: " + start)
        val dm = new DataMining()
        println(dm.mine(numOfZeros, start, end))
        remote ! "I need work!"
      }
      else {
        println(s"RemoteActor received message '$msg'")
      }
    }
    case Job(numOfZeros, start, end) => {
      val dm = new DataMining()
      sender ! dm.mine(numOfZeros, start, end)
    }
  }
}