package project1

import java.math.BigInteger
import java.net.InetAddress
import akka.actor.Actor
import org.apache.commons.codec.binary.Base64
import com.roundeights.hasher.Implicits._

/**
 * Created by chelsea on 9/6/15.
 */
class Worker extends Actor {
  //println("Num of Zeros: " + numOfZeros)
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://MasterSystem@" + InetAddress.getLocalHost.getHostAddress + ":8397/user/RemoteActor")
  var counter = 0

  def receive = {
    case Job(numOfZeros, start, end) => {
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
    case "START" => {
      remote ! "Need work!"
    }
    case msg: String => {
      println(s"Worker received message: '$msg'")
      if (counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
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