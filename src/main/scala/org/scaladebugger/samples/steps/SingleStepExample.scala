package org.scaladebugger.samples.steps

import org.scaladebugger.api.debuggers.LaunchingDebugger
import org.scaladebugger.api.utils.JDITools

/**
 * Creates a single step to demonstrate the process.
 */
object SingleStepExample extends App {
  // Get the executing class name (remove $ from object class name)
  val klass = SingleStepMainClass.getClass
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

    // Files are in the form of package/structure/to/class.scala
    val fileName = JDITools.scalaClassStringToFileString(className)
    val lineNumber = 7

    // On reaching a breakpoint for our class below, step to the next
    // line and then shutdown our debugger
    s.getOrCreateBreakpointRequest(fileName, lineNumber).foreach(be => {
      val path = be.location.sourcePath
      val line = be.location.lineNumber

      println(s"Reached breakpoint for $path:$line")

      // Step methods return a future that occurs when the step finishes
      import scala.concurrent.ExecutionContext.Implicits.global
      s.stepOverLine(be.thread).foreach(se => {
        val path = se.location.sourcePath
        val line = se.location.lineNumber

        println(s"Stepped to $path:$line")
        launchingDebugger.stop()
      })
    })
  }

  // Keep the sample program running while our debugger is running
  while (launchingDebugger.isRunning) Thread.sleep(1)
}
