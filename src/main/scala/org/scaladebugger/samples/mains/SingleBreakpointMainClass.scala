package org.scaladebugger.samples.mains

object SingleBreakpointMainClass {
  def main(args: Array[String]): Unit = {
    def noop(): Unit = {}
    while (true) {
      noop() // Breakpoint line is 7
    }
  }
}
