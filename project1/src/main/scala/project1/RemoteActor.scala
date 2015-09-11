package project1

import java.net.InetAddress
import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

class RemoteActor(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0
  var counter: Integer = 0
  val workSize: Integer = 1000000
  val remote = context.actorSelection("akka.tcp://MasterSystem@" + InetAddress.getLocalHost.getHostAddress + ":8397/user/RemoteActor")

  def receive = {
    case msg: String => {
      if (msg.equals("Need work!")) {
        start = start + workSize
        end = start + workSize
        println("Start Worker: " + start)
        sender ! Job(numOfZeros, start, end)
      }
      else if (msg.equals("I need work!")) {
        runDataMine.start
      }
      else {
        println(s"RemoteActor received message '$msg'")
      }
    }
  }

  val runDataMine = new Thread(new Runnable {
    def run() {
      counter += 1
      start = start + workSize
      end = start + workSize
      println("Start Server: " + start)
      val dm = new DataMining()
      println(dm.mine(numOfZeros, start, end))
      //remote ! "I need work!"
      /*if (counter < 3) {
        remote ! "I need work!"
      }
      else {
        context.system.shutdown()
      }*/
    }
  })

  /*val runDataMine = new Thread(new Runnable {
    def run() {
      println("hello world")
    }
  })*/
}