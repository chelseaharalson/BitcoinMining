package project1

import java.net.InetAddress

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

// The Boss acts like a server. It keeps track of all the problems and performs the job assignment.

class Boss(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0
  val workSize: Integer = 1000000
  var totalAmtOfCoins: Integer = 0

  def receive = {
    case msgFromServer: String => {
      if (msgFromServer.equals("Worker needs work!")) {
        start = start + workSize
        end = start + workSize
        println("Start Worker: " + start)
        sender ! DoWork(numOfZeros,start,end) // By using sender, one can refer to the actor that sent the message that the current actor last received
      }
      else if (msgFromServer.equals("Server needs work!")) {
        runDataMine.start
      }
      else {
        println(s"Boss received message '$msgFromServer'")
      }
    }
    case CoinCount(count) => {
      totalAmtOfCoins = totalAmtOfCoins + count
      println("Total coin count!!!: " + totalAmtOfCoins)
      //context.system.shutdown
    }
  }

  val runDataMine = new Thread(new Runnable {
    def run() {
      start = start + workSize
      end = start + workSize
      println("Start Server: " + start)
      val dm = new DataMining()
      println(dm.mine(numOfZeros, start, end))
      totalAmtOfCoins = totalAmtOfCoins + dm.getCoinCount()
      println("Total coin count!!!: " + totalAmtOfCoins)
    }
  })
}