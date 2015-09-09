package project1

import akka.actor._
import java.net.InetAddress
import com.typesafe.config.{ConfigFactory, Config}
import java.math.BigInteger
import scala.language.postfixOps
import org.apache.commons.codec.binary.Base64
import com.roundeights.hasher.Implicits._
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

trait Message

/**
 * Created by chelsea on 9/6/15.
 */

case class DoJobs(job: ArrayBuffer[Job]) extends Message
case class DoWork(job: Job) extends Message
case class Job(numOfZeros: Long, start: Long, end: Long)

object Server /*extends App*/ {
  def main(args: Array[String]) = {
    println("Hello, " + args(0))
    println(InetAddress.getLocalHost().getHostAddress())
    if (isNumeric(args(0)))
      //println("Yes")
      runAsServer(args(0).toLong,1,1000)
    else
      //println("No")
      runAsClient(args(0))
  }

  def isNumeric(input: String): Boolean = input.forall(_.isDigit)

  def runAsServer(numOfZeros: Long, start: Long, end: Long) = {
    val mapServer = new java.util.HashMap[String, Object]
    mapServer.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapServer.put("akka.remote.netty.tcp.hostname", InetAddress.getLocalHost.getHostAddress)
    mapServer.put("akka.remote.netty.tcp.port", "5150")
    val akkaConfigServer = ConfigFactory.parseMap(mapServer)
    val system = ActorSystem("HelloRemoteSystem", akkaConfigServer)
    val remoteActor = system.actorOf(Props (new RemoteActor(numOfZeros,start,end)), name = "RemoteActor")
    remoteActor ! "The RemoteActor is alive"
  }

  def runAsClient(ipAddress: String) = {
    val mapClient = new java.util.HashMap[String, Object]
    mapClient.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider")
    mapClient.put("akka.remote.netty.tcp.hostname", ipAddress)
    mapClient.put("akka.remote.netty.tcp.port", "0")
    val akkaConfigClient = ConfigFactory.parseMap(mapClient)
    implicit val system = ActorSystem("LocalSystem", akkaConfigClient)
    val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")  // the local actor
    localActor ! "START"                                                     // start the action
  }

}

class RemoteActor(numOfZeros: Long, start: Long, end: Long) extends Actor {
  def receive = {
    case msg: String =>
      if (msg.equals("Need work!"))
      {
        sender ! Job(numOfZeros, start, end)
        //sender ! "Hello from the RemoteActor"
      }
      else {
        println(s"RemoteActor received message '$msg'")
      }
  }
}

// -----------------------------------------------------------------------------------

class LocalActor extends Actor {
  //println("Num of Zeros: " + numOfZeros)
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@" + InetAddress.getLocalHost.getHostAddress + ":5150/user/RemoteActor")
  var counter = 0

  def receive = {
    case Job(numOfZeros, start, end) =>
    {
        var i = start
        while (i < end) {
          val coinHash = getCoinHash(i)
          if (coinHash._2.startsWith(getPattern(numOfZeros))) {
            remote ! coinHash._1 + '\t' + coinHash._2
          }
          i += 1
        }
        context.system.shutdown()
    }
    case "START" =>
    {
      remote ! "Need work!"
    }
    case msg: String =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
  }

  def getCoinHash(idx: Long): (String, String) = {
    val value = "chelseametcalf" + new String(Base64.encodeInteger(BigInteger.valueOf(idx)))
    val hashed = value.sha256.hex
    //println("Value: " + value)
    //hashed
    (value, hashed)
  }

  def getPattern(numOfZeros: Long) = {
    var s = ""
    var i = 0
    while (i < numOfZeros) {
      i += 1
      s ++= "0"
    }
    s
  }
}