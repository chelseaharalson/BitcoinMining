package project1

import akka.actor._
import java.net.InetAddress

import com.typesafe.config.{ConfigFactory, Config}

/**
 * Created by chelsea on 9/6/15.
 */
object Server /*extends App*/ {
  def main(args: Array[String]) = {
    println("Hello, " + args(0))
    println(InetAddress.getLocalHost().getHostAddress())
    if (isNumeric(args(0)))
      //println("Yes")
      runAsServer
    else
      //println("No")
      runAsClient
  }

  def isNumeric(input: String): Boolean = input.forall(_.isDigit)

  def runAsServer = {
    val mapServer = new java.util.HashMap[String, Object]
    mapServer.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapServer.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapServer.put("akka.remote.netty.tcp.port", "5150")
    val akkaConfigServer = ConfigFactory.parseMap(mapServer)
    val system = ActorSystem("HelloRemoteSystem", akkaConfigServer)
    val remoteActor = system.actorOf(Props[RemoteActor], name = "RemoteActor")
    remoteActor ! "The RemoteActor is alive"
  }

  def runAsClient = {
    val mapClient = new java.util.HashMap[String, Object]
    mapClient.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapClient.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapClient.put("akka.remote.netty.tcp.port", "0")
    val akkaConfigClient = ConfigFactory.parseMap(mapClient)
    implicit val system = ActorSystem("LocalSystem", akkaConfigClient)
    val localActor = system.actorOf(Props(new LocalActor(1,2,3)), name = "LocalActor")  // the local actor
    localActor ! "START"                                                     // start the action
  }

}

class RemoteActor extends Actor {
  def receive = {
    case msg: String =>
      println(s"RemoteActor received message '$msg'")
      sender ! "Hello from the RemoteActor"
  }
}

class LocalActor(numOfZeros: Integer, startNum: Integer, endNum: Integer) extends Actor {
  println("Num of Zeros: " + numOfZeros)
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@" + InetAddress.getLocalHost.getHostAddress + ":5150/user/RemoteActor")
  var counter = 0

  def receive = {
    case "START" =>
      remote ! "Hello from the LocalActor"
    case msg: String =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
  }
}