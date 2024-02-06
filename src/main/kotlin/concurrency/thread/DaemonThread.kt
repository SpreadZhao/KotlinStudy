package concurrency.thread

class DaemonThread {
    class DaemonRunner : Runnable {
        override fun run() {
            try {
                SleepUtils.second(10)
            } finally {
                println("DaemonThread finally run.")
            }
        }
    }
}

fun main() {
    val thread = Thread(DaemonThread.DaemonRunner(), "DaemonThread")
    thread.isDaemon = true
    thread.start()
}