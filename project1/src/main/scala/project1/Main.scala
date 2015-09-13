package project1

import akka.actor._
import java.net.InetAddress
import com.typesafe.config.{ConfigFactory}

/**
 * Created by chelsea on 9/9/15.
 */
object Main {

  def main(args: Array[String]) = {
    println("Local IP Address: " + InetAddress.getLocalHost().getHostAddress())
    if (isNumber(args(0))) {
      println("Boss: ")
      println("Number of Zeros Specified:  " + args(0))
      runBoss(args(0).toLong)
    }
    else {
      println("Worker: ")
      runWorker(args(0))
    }
  }

  // Checks if parameter is a number
  def isNumber(inputString: String): Boolean = {
    inputString.forall(_.isDigit)
  }

  def runBoss(numOfZeros: Long) = {
    val mapServer = new java.util.HashMap[String, Object]
    mapServer.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapServer.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapServer.put("akka.remote.netty.tcp.port", "8397")
    val akkaConfigBoss = ConfigFactory.parseMap(mapServer)
    val system = ActorSystem("BossSystem", akkaConfigBoss)
    val bossActor = system.actorOf(Props (new Boss(numOfZeros)), name = "BossActor")
    bossActor ! "The BOSS is alive"
    bossActor ! "Server needs work!"
  }

  def runWorker(ipAddress: String) = {
    val mapWorker = new java.util.HashMap[String, Object]
    mapWorker.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapWorker.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapWorker.put("akka.remote.netty.tcp.port", "0")
    val akkaConfigWorker = ConfigFactory.parseMap(mapWorker)
    implicit val system = ActorSystem("WorkerSystem", akkaConfigWorker)
    val workerActor = system.actorOf(Props (new Worker(ipAddress)), name = "WorkerActor")
    workerActor ! "START WORKER"
  }

}
