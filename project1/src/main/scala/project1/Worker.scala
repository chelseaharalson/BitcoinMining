package project1

import java.math.BigInteger
import java.net.InetAddress
import akka.actor.Actor
import org.apache.commons.codec.binary.Base64
import com.roundeights.hasher.Implicits._
import scala.compat.Platform

/**
 * Created by chelsea on 9/6/15.
 */
class Worker extends Actor {
  //println("Num of Zeros: " + numOfZeros)
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://MasterSystem@" + InetAddress.getLocalHost.getHostAddress + ":8397/user/RemoteActor")

  def receive = {
    case Job(numOfZeros, start, end) => {
      val dm = new DataMining()
      remote ! dm.mine(numOfZeros, start, end)
      context.system.shutdown()
    }
    case "START" => {
      remote ! "Need work!"
    }
  }

  def getCoinHash(idx: Long): (String, String) = {
    val inputString = "chelseametcalf" + new String(Base64.encodeInteger(BigInteger.valueOf(idx)))
    val hash = inputString.sha256.hex
    (inputString, hash)
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