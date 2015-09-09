package project1

/**
 * Created by chelsea on 9/5/15.
 */

trait Message

case class DoWork(job: Job) extends Message
case class Job(numOfZeros: Long, start: Long, end: Long)