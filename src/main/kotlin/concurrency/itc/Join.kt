package concurrency.itc

import java.util.concurrent.TimeUnit

class Join {
    class Domino(private val previous: Thread) : Runnable {
        override fun run() {
            try {
                previous.join()
            } catch (_: InterruptedException) {
            }
            println("${Thread.currentThread().name} terminate.")
        }
    }
}

fun main() {
    var previous = Thread.currentThread()
    repeat(10) {
        val th = Thread(Join.Domino(previous), "$it")
        th.start()
        previous = th
    }
    TimeUnit.SECONDS.sleep(5)
    println("${Thread.currentThread().name} terminate.")
}