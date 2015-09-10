package project1

import akka.actor._
import java.net.InetAddress
import com.typesafe.config.{ConfigFactory}

/**
 * Created by chelsea on 9/9/15.
 */
object Main {

  def main(args: Array[String]) = {
    //println("Hello, " + args(0))
    println("Local IP Address: " + InetAddress.getLocalHost().getHostAddress())
    if (isNumeric(args(0))) {
      println("Server: ")
      println("Number of Zeroes Specified:  " + args(0))
      runAsServer(args(0).toLong)
    }
    else {
      println("Worker: ")
      runAsClient(args(0))
    }
  }

  def isNumeric(input: String): Boolean = input.forall(_.isDigit)

  def runAsServer(numOfZeros: Long) = {
    val mapServer = new java.util.HashMap[String, Object]
    mapServer.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapServer.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapServer.put("akka.remote.netty.tcp.port", "8397")
    val akkaConfigServer = ConfigFactory.parseMap(mapServer)
    val system = ActorSystem("MasterSystem", akkaConfigServer)
    val remoteActor = system.actorOf(Props (new RemoteActor(numOfZeros)), name = "RemoteActor")
    remoteActor ! "The RemoteActor is alive"
    remoteActor ! "I need work!"

    /*val bs = new Boss()
    val startEnd = bs.getStartEnd()
    val dm = new DataMining()
    dm.mine(numOfZeros, startEnd._1, startEnd._2)*/
  }

  def runAsClient(ipAddress: String) = {
    val mapWorker = new java.util.HashMap[String, Object]
    mapWorker.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapWorker.put("akka.remote.netty.tcp.hostname", ipAddress)
    mapWorker.put("akka.remote.netty.tcp.port", "0")
    val akkaConfigClient = ConfigFactory.parseMap(mapWorker)
    implicit val system = ActorSystem("WorkerSystem", akkaConfigClient)
    val localActor = system.actorOf(Props[Worker], name = "LocalActor")  // the local actor
    localActor ! "START"                                                 // start the action
  }

}
