package project1

import akka.actor._

/**
 * Created by chelsea on 9/6/15.
 */

// The Boss acts like a server. It keeps track of all the problems and performs the job assignment.

class Boss(numOfZeros: Long) extends Actor {
  var start: Long = 0
  var end: Long = 0
  var counter: Integer = 0
  val workSize: Integer = 1000000

  def receive = {
    case msgFromWorker: String => {
      if (msgFromWorker.equals("Worker needs work!")) {
        start = start + workSize
        end = start + workSize
        println("Start Worker: " + start)
        sender ! DoWork(numOfZeros, start, end) // By using sender, one can refer to the actor that sent the message that the current actor last received
      }
      else if (msgFromWorker.equals("Server needs work!")) {
        runDataMine.start
      }
      else {
        println(s"Boss received message '$msgFromWorker'")
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
    }
  })
}