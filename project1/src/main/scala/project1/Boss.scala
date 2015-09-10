package project1

/**
 * Created by chelsea on 9/9/15.
 */
class Boss {
  var start: Long = 0
  var end: Long = 0

  def getStartEnd(): (Long, Long) = {
    start = start + 10000
    end = start + 10000
    (start, end)
  }

}
