package org.scaladebugger.samples.steps

object SingleStepMainClass {
  def main(args: Array[String]): Unit = {
    def noop(): Unit = {}
    while (true) {
      noop() // Breakpoint line is 7
      noop() // Step over should reach here
    }
  }
}
