package concurrency.thread

import java.util.concurrent.TimeUnit

class ElegantlyKillThread {
    class Runner : Runnable {

        private var i = 0L

        @Volatile
        private var on = true

        override fun run() {
            while (on && !Thread.currentThread().isInterrupted) {
                i++
            }
            println("i = $i")
        }

        fun cancel() {
            on = false
        }
    }
}

fun main() {
    val one = ElegantlyKillThread.Runner()
    var countThread = Thread(one, "CountThread")
    countThread.start()
    TimeUnit.SECONDS.sleep(1)
    countThread.interrupt()
    val two = ElegantlyKillThread.Runner()
    countThread = Thread(two, "CountThread")
    countThread.start()
    TimeUnit.SECONDS.sleep(1)
    two.cancel()
}