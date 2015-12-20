package org.scaladebugger.samples

import org.scaladebugger.api.debuggers.ListeningDebugger
import org.scaladebugger.api.utils.JDITools

/**
 * Starts a target JVM process and connects to it using the
 * listening debugger.
 */
object ListeningDebuggerExample extends App {
  val className = classOf[SomeListeningMainClass].getName

  val listeningDebugger = ListeningDebugger(hostname = "localhost", port = 5005)

  listeningDebugger.start { s =>
    println("Received connection from JVM: " + s.uniqueId)

    // Shuts down our debugger
    listeningDebugger.stop()

    // Shuts down the remote process
    targetJvmProcess.destroy()
  }

  // Spawn the target JVM process using our helper function
  val targetJvmProcess = JDITools.spawn(
    className = className,
    port = 5005,
    server = false, // Don't listen for connections but instead do
                    // the connecting
    suspend = true // Wait to start the main class until after connected
  )

  // Keep the sample program running while our debugger is running
  while (listeningDebugger.isRunning) Thread.sleep(1)
}

/**
 * Sample main class that does nothing.
 */
class SomeListeningMainClass {
  def main(args: Array[String]): Unit = {
    // Does nothing
  }
}
