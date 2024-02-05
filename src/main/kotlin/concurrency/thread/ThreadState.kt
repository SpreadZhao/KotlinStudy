package concurrency.thread

import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class ThreadState {
    class TimeWaiting : Runnable {
        override fun run() {
            while (true) {
                SleepUtils.second(100)
            }
        }
    }

    class Waiting : Runnable {

        override fun run() {
            while (true) {
                synchronized(Waiting::class.java) {
                    try {
                        // https://kotlinlang.org/docs/java-interop.html#object-methods
                        (Waiting::class.java as java.lang.Object).wait()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    class Blocked : Runnable {
        override fun run() {
            synchronized(Blocked::class.java) {
                while (true) {
                    SleepUtils.second(100)
                }
            }
        }
    }

    class SleepUtils {
        companion object {
            @JvmStatic
            fun second(seconds: Long) {
                try {
                    TimeUnit.SECONDS.sleep(seconds)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

fun main() {
    Thread(ThreadState.TimeWaiting(), "TimeWaitingThread").start()
    Thread(ThreadState.Waiting(), "WaitingThread").start()
    Thread(ThreadState.Blocked(), "BlockedThread-1").start()
    Thread(ThreadState.Blocked(), "BlockedThread-2").start()
}