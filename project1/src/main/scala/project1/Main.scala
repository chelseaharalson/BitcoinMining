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
      runAsServer(args(0).toLong, 1, 1000)
    }
    else {
      println("Worker: ")
      runAsClient(args(0))
    }
  }

  def isNumeric(input: String): Boolean = input.forall(_.isDigit)

  def runAsServer(numOfZeros: Long, start: Long, end: Long) = {
    val mapServer = new java.util.HashMap[String, Object]
    mapServer.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapServer.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapServer.put("akka.remote.netty.tcp.port", "8397")
    val akkaConfigServer = ConfigFactory.parseMap(mapServer)
    val system = ActorSystem("MasterSystem", akkaConfigServer)
    val remoteActor = system.actorOf(Props (new RemoteActor(numOfZeros,start,end)), name = "RemoteActor")
    remoteActor ! "The RemoteActor is alive"
  }

  def runAsClient(ipAddress: String) = {
    val mapClient = new java.util.HashMap[String, Object]
    mapClient.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapClient.put("akka.remote.netty.tcp.hostname", ipAddress)
    mapClient.put("akka.remote.netty.tcp.port", "0")
    val akkaConfigClient = ConfigFactory.parseMap(mapClient)
    implicit val system = ActorSystem("WorkerSystem", akkaConfigClient)
    val localActor = system.actorOf(Props[Worker], name = "LocalActor")  // the local actor
    localActor ! "START"                                                 // start the action
  }

}
