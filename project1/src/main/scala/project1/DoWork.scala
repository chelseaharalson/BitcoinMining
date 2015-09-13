package project1

/**
 * Created by chelsea on 9/10/15.
 */

case class DoWorkBoss(numOfZeros: Long,start: Long,end: Long)
case class DoWorkWorker(numOfZeros: Long,start: Long,cycles: Long,workSize: Long)
case class CoinCount(count: Integer)