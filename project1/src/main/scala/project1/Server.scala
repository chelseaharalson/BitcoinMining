package project1

import akka.actor._
import java.net.InetAddress
import java.util

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
    val system = ActorSystem("HelloRemoteSystem")
    val remoteActor = system.actorOf(Props[RemoteActor], name = "RemoteActor")
    remoteActor ! "The RemoteActor is alive"
  }

  def runAsClient = {
    val map = new java.util.HashMap[String, Object]
    //applyRemoteSettings(map)
    map.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    map.put("akka.remote.netty.tcp.hostname", "127.0.0.1")
    map.put("akka.remote.netty.tcp.port", "0")
    //ConfigFactory.parseMap(map)
    val akkaConfig = ConfigFactory.parseMap(map)
    implicit val system = ActorSystem("LocalSystem", akkaConfig)
    val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")  // the local actor
    localActor ! "START"                                                     // start the action
  }

  /*def applyRemoteSettings(map: util.HashMap[String, Object]) = {
    //map.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    //map.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    map.put("akka.remote.netty.tcp.port", "0")
  }*/

}

class RemoteActor extends Actor {
  def receive = {
    case msg: String =>
      println(s"RemoteActor received message '$msg'")
      sender ! "Hello from the RemoteActor"
  }
}

class LocalActor extends Actor {
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@127.0.0.1:5150/user/RemoteActor")
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