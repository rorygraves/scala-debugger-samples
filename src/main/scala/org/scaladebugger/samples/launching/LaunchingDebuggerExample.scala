package org.scaladebugger.samples.launching

import org.scaladebugger.api.debuggers.LaunchingDebugger
import org.scaladebugger.api.utils.JDITools

/**
 * Launches a target JVM process and connects to it using the
 * launching debugger.
 */
object LaunchingDebuggerExample extends App {
  // Get the executing class name (remove $ from object class name)
  val klass = SomeLaunchingMainClass.getClass
  val className = klass.getName.replaceAllLiterally("$", "")

  // Add our main class to the classpath used to launch the class
  val classpath = JDITools.jvmClassPath
  val jvmOptions = Seq("-classpath", classpath)

  val launchingDebugger = LaunchingDebugger(
    className = className,
    jvmOptions = jvmOptions,
    suspend = true // Wait to start the main class until after connected
  )

  launchingDebugger.start { s =>
    println("Launched and connected to JVM: " + s.uniqueId)

    // Shuts down the launched target JVM and our debugger
    launchingDebugger.stop()
  }

  // Keep the sample program running while our debugger is running
  while (launchingDebugger.isRunning) Thread.sleep(1)
}
