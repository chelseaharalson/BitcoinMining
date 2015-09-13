package project1

import akka.actor.{Props, Actor}

/**
 * Created by chelsea on 9/6/15.
 */

class MineCoinsWorker() extends Actor {
  def receive = {
    case DoWorkBoss(numOfZeros,start,end) => {
      val dm = new DataMining()
      sender ! dm.mine(numOfZeros,start,end)
      //sender ! "WORKER: " + start + "   " + end
      sender ! CoinCount(dm.getCoinCount())
    }
  }
}

class Worker(ipAddress: String) extends Actor {
  val remote = context.actorSelection("akka.tcp://BossSystem@" + ipAddress + ":8397/user/BossActor")
  var startWork: Long = 0
  var endWork: Long = 0
  var lCycles: Long = 0
  var dataMiningDone: Long = 0

  def receive = {
    case DoWorkWorker(numOfZeros,start,cycles,workSize) => {
      lCycles = cycles
      startWork = start
      var i = 0
      while (i < cycles) {
        startWork = startWork + workSize
        endWork = startWork + workSize
        context.actorOf(Props(new MineCoinsWorker)) ! DoWorkBoss(numOfZeros,startWork,endWork)
        i += 1
      }
    }
    case "START WORKER" => {
      remote ! "Worker needs work!"
    }
    case CoinCount(count) => {
      remote ! CoinCount(count)
      dataMiningDone += 1
      if (lCycles == dataMiningDone) {
        context.system.shutdown()
      }
    }
    case msg: String => {
      remote ! msg
    }
  }
}