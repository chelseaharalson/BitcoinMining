package project1

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

class MineCoinsBoss() extends Actor {
  def receive = {
    case DoWorkBoss(numOfZeros,start,end) => {
      val dm = new DataMining()
      sender ! dm.mine(numOfZeros,start,end)
      //sender ! "BOSS: " + start + "   " + end
      sender ! CoinCount(dm.getCoinCount())
    }
  }
}

// The Boss acts like a server. It keeps track of all the problems and performs the job assignment.
// The variables workSize and cycles are meant to be changed.
class Boss(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0
  // -------------------- For testing (can change work size):
  //val workSize: Long = 10000
  val workSize: Long = 1000000
  //val workSize: Long = 10000000
  //val workSize: Long = 1000000000
  var totalAmtOfCoins: Integer = 0
  // -------------------- For testing (can change cycles):
  //val cycles: Long = 4
  val cycles: Long = 8
  //val cycles: Long = 10
  //val cycles: Long = 16
  var cycleCount: Long = 0

  def receive = {
    case msgFromServer: String => {
      if (msgFromServer.equals("Worker needs work!")) {
        println("Start Worker: " + start + " to " + end)
        // By using sender, one can refer to the actor that sent the message that the current actor last received
        sender ! DoWorkWorker(numOfZeros,end,cycles,workSize)
        start = start + (cycles * workSize)   // Grab this chunk of work
        end = start + workSize
        cycleCount = cycleCount + cycles
      }
      else if (msgFromServer.equals("Server needs work!")) {
        cycleCount = cycles
        var i = 0
        while (i < cycles) {
          start = start + workSize
          end = start + workSize
          context.actorOf(Props(new MineCoinsBoss)) ! DoWorkBoss(numOfZeros,start,end)
          i += 1
        }
      }
      else {
        println(msgFromServer)
      }
    }
    case CoinCount(count) => {
      cycleCount -= 1
      totalAmtOfCoins = totalAmtOfCoins + count
      if (cycleCount == 0) {
        println("TOTAL COIN COUNT: " + totalAmtOfCoins)
        // I shut down the program when it was done mining. However this line below can be commented out to leave the Boss running, so new workers can pick up work.
        context.system.shutdown
      }
    }
  }
}