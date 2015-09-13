package project1

import java.net.InetAddress

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

// The Boss acts like a server. It keeps track of all the problems and performs the job assignment.
class MineCoins() extends Actor {
  def receive = {
    case DoWorkBoss(numOfZeros,start,end) => {
      val dm = new DataMining()
      sender ! dm.mine(numOfZeros,start,end)
      sender ! CoinCount(dm.getCoinCount())
    }
  }
}

class Boss(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0
  val workSize: Long = 10000
  // For testing:
  //val workSize: Integer = 1000000
  var totalAmtOfCoins: Integer = 0
  val cycles: Long = 10
  var dataMiningDone: Integer = 0

  def receive = {
    case msgFromServer: String => {
      if (msgFromServer.equals("Worker needs work!")) {
        println("Start Worker: " + start + " to " + end)
        sender ! DoWorkWorker(numOfZeros,end,cycles,workSize) // By using sender, one can refer to the actor that sent the message that the current actor last received
        start = start + (cycles * workSize)
        end = start + workSize
      }
      else if (msgFromServer.equals("Server needs work!")) {
        var i = 0
        while (i < cycles) {
          start = start + workSize
          end = start + workSize
          context.actorOf(Props(new MineCoins)) ! DoWorkBoss(numOfZeros,start,end)
          i += 1
        }
      }
      else {
        println(s"Boss received message '$msgFromServer'")
      }
    }
    case CoinCount(count) => {
      dataMiningDone += 1
      totalAmtOfCoins = totalAmtOfCoins + count
      if (dataMiningDone == cycles) {
        println("Total coin count!!!: " + totalAmtOfCoins)
        //context.system.shutdown
      }
    }
  }
}