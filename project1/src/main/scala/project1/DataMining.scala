package project1

import java.math.BigInteger
import org.apache.commons.codec.binary.Base64
import com.roundeights.hasher.Implicits._
import scala.compat.Platform

/**
 * Created by chelsea on 9/9/15.
 */
class DataMining {
  def mine(numOfZeros: Long, start: Long, end: Long) = {
    val startTime = Platform.currentTime
    var output = ""
    val pattern = getPattern(numOfZeros)
    var i = start
    while (i < end) {
      val coinHash = getCoinHash(i)
      if (coinHash._2.startsWith(pattern)) {
        output = output + coinHash._1 + '\t' + coinHash._2 + '\n'
      }
      i += 1
    }
    val endTime = Platform.currentTime
    val totalTime = endTime - startTime
    output = output + "Total Time: " + totalTime + " ms" + '\n'
    output
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
