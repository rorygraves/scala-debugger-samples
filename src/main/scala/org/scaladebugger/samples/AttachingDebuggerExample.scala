package org.scaladebugger.samples

import org.scaladebugger.api.debuggers.{AttachingDebugger, LaunchingDebugger}
import org.scaladebugger.api.utils.JDITools

/**
 * Starts a target JVM process and connects to it using the
 * attaching debugger.
 */
object AttachingDebuggerExample extends App {
  val className = classOf[SomeAttachingMainClass].getName

  // Spawn the target JVM process using our helper function
  val targetJvmProcess = JDITools.spawn(
    className = className,
    port = 5005,
    suspend = true // Wait to start the main class until after connected
  )

  val attachingDebugger = AttachingDebugger(port = 5005)

  attachingDebugger.start { s =>
    println("Attached to JVM: " + s.uniqueId)

    // Shuts down our debugger
    attachingDebugger.stop()

    // Shuts down the remote process
    targetJvmProcess.destroy()
  }

  // Keep the sample program running while our debugger is running
  while (attachingDebugger.isRunning) Thread.sleep(1)
}

/**
 * Sample main class that does nothing.
 */
class SomeAttachingMainClass {
  def main(args: Array[String]): Unit = {
    // Does nothing
  }
}
