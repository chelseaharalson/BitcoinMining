package project1

import java.net.InetAddress
import akka.actor.Actor

/**
 * Created by chelsea on 9/6/15.
 */
class Worker extends Actor {
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://MasterSystem@" + InetAddress.getLocalHost.getHostAddress + ":8397/user/RemoteActor")

  def receive = {
    case Job(numOfZeros, start, end) => {
      val dm = new DataMining()
      remote ! "WORKER!!!!!!! " + dm.mine(numOfZeros, start, end)
      //context.system.shutdown()
    }
    case "START" => {
      remote ! "Need work!"
    }
  }
}