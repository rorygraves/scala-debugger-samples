package org.scaladebugger.samples.watchpoints

object SingleWatchpointMainClass {
  def main(args: Array[String]): Unit = {
    val otherClass = new SingleWatchpointOtherClass

    while (true) {
      otherClass.x += 1
    }
  }
}

class SingleWatchpointOtherClass {
  var x: Int = 0
}
