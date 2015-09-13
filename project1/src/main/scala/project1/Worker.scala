package project1

import java.net.InetAddress
import akka.actor.{Actor}

/**
 * Created by chelsea on 9/6/15.
 */
class Worker extends Actor {
  // create the remote actor
  // needs to be same IP address as the Boss/server
  val remote = context.actorSelection("akka.tcp://BossSystem@10.0.0.4:8397/user/BossActor")

  def receive = {
    case DoWork(numOfZeros,start,end) => {
      val dm = new DataMining()
      remote ! "WORKER!!!!!!! " + dm.mine(numOfZeros,start,end)
      remote ! CoinCount(dm.getCoinCount())
      context.system.shutdown
    }
    case "START WORKER" => {
      remote ! "Worker needs work!"
    }
  }
}