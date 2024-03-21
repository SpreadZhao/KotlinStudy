package concurrency.thread

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DeprecatedLife {
    class Runner : Runnable {
        override fun run() {
            val format = SimpleDateFormat("HH:mm:ss")
            while (true) {
                println("${Thread.currentThread().name} run at ${format.format(Date())}")
                SleepUtils.second(1)
            }
        }
    }
}

fun main() {
    val format = SimpleDateFormat("HH:mm:ss")
    val printThread = Thread(DeprecatedLife.Runner(), "PrintThread")
    printThread.isDaemon = true
    printThread.start()
    TimeUnit.SECONDS.sleep(3)
    printThread.suspend()

}