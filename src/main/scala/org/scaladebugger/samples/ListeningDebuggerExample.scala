package org.scaladebugger.samples

import org.scaladebugger.api.debuggers.ListeningDebugger
import org.scaladebugger.api.utils.JDITools
import org.scaladebugger.samples.mains.SomeListeningMainClass

/**
 * Starts a target JVM process and connects to it using the
 * listening debugger.
 */
object ListeningDebuggerExample extends App {
  // Get the executing class name (remove $ from object class name)
  val klass = SomeListeningMainClass.getClass
  val className = klass.getName.replaceAllLiterally("$", "")

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
