package project1

import com.roundeights.hasher.Implicits._
import scala.language.postfixOps
import scala.compat.Platform

/**
 * Created by chelsea on 9/9/15.
 */

// Performs the data mining operations such as generating the coin hash and getting the prefix pattern

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
    output = output + "Total time to mine: " + totalTime + " ms" + '\n'
    output
  }

  def getCoinHash(idx: Long): (String, String) = {
    val inputString = "chelseametcalf" + Integer.toHexString(idx.toInt)
    val hash = inputString.sha256.hex     // used Hasher lib (a small Scala lib for generating hashes)
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
