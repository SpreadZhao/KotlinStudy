package concurrency.itc

import concurrency.thread.SleepUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class WaitNotify {
    companion object {
        @JvmField
        var needWait = true

        @JvmField
        var lock = Object()

        @JvmStatic
        fun getDate(): String {
            return SimpleDateFormat("HH:mm:ss").format(Date())
        }

        @JvmStatic
        fun log(msg: String) {
            println("${Thread.currentThread()} $msg ${getDate()}")
        }
    }

    class Wait : Runnable {
        override fun run() {
            synchronized(lock) {
                while (needWait) {
                    try {
                        log("need wait. wa @")
                        lock.wait()
                    } catch (_: InterruptedException) {

                    }
                }
                log("running @")
            }
        }
    }

    class Notify : Runnable {
        override fun run() {
            synchronized(lock) {
                log("hold lock. notify @")
                lock.notifyAll()
                needWait = false
                SleepUtils.second(5)
            }
            synchronized(lock) {
                log("hold lock again. sleep @")
                SleepUtils.second(5)
            }
        }
    }
}

fun main() {
    val waitThread = Thread(WaitNotify.Wait(), "WaitThread")
    waitThread.start()
    TimeUnit.SECONDS.sleep(1)
    val notifyThread = Thread(WaitNotify.Notify(), "NotifyThread")
    notifyThread.start()
}