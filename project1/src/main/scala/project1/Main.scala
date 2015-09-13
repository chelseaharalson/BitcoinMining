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
      println("Server: ")
      println("Number of Zeroes Specified:  " + args(0))
      runAsServer(args(0).toLong)
    }
    else {
      println("Worker: ")
      runAsClient(args(0))
    }
  }

  // Checks if parameter is a number
  def isNumber(inputString: String): Boolean = {
    inputString.forall(_.isDigit)
  }

  def runAsServer(numOfZeros: Long) = {
    val mapServer = new java.util.HashMap[String, Object]
    mapServer.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapServer.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapServer.put("akka.remote.netty.tcp.port", "8397")
    val akkaConfigServer = ConfigFactory.parseMap(mapServer)
    val system = ActorSystem("BossSystem", akkaConfigServer)
    val bossActor = system.actorOf(Props (new Boss(numOfZeros)), name = "BossActor")
    bossActor ! "The Server is alive"
    bossActor ! "Server needs work!"
  }

  def runAsClient(ipAddress: String) = {
    val mapWorker = new java.util.HashMap[String, Object]
    mapWorker.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapWorker.put("akka.remote.netty.tcp.hostname", ipAddress)
    mapWorker.put("akka.remote.netty.tcp.port", "0")
    val akkaConfigClient = ConfigFactory.parseMap(mapWorker)
    implicit val system = ActorSystem("WorkerSystem", akkaConfigClient)
    val workerActor = system.actorOf(Props[Worker], name = "WorkerActor")   // the local actor
    workerActor ! "START WORKER"                                          // start the action
  }

}
