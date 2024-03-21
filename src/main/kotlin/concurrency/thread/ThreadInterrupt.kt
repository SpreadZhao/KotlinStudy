package concurrency.thread

import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class ThreadInterrupt {
    class SleepRunner : Runnable {
        override fun run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    class BusyRunner : Runnable {
        override fun run() {
            while (true) {
            }
        }
    }
}

fun main() {
    first()
//    other()
}

fun first() {
    val sleepThread = Thread(ThreadInterrupt.SleepRunner(), "SleepThread")
//    sleepThread.isDaemon = true
    val busyThread = Thread(ThreadInterrupt.BusyRunner(), "BusyThread")
//    busyThread.isDaemon = true
    sleepThread.start()
    busyThread.start()

    TimeUnit.SECONDS.sleep(5)
    sleepThread.interrupt()
    busyThread.interrupt()
    println("SleepThread interrupted is ${sleepThread.isInterrupted}")
    println("BusyThread interrupted is ${busyThread.isInterrupted}")

    TimeUnit.SECONDS.sleep(2)
}

fun other() {
    val th = thread {
        while (true) {
        }
    }
    println("before: ${th.isInterrupted}")
    th.interrupt()
    println("after: ${th.isInterrupted}")
}