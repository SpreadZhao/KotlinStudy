package concurrency.utils

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.concurrent.thread

val c = CountDownLatch(2)

class Worker(
    private val num: Int,
    private val startSignal: CountDownLatch,
    private val doneSignal: CountDownLatch
) : Runnable {
    override fun run() {
        try {
            startSignal.await()
            doWork()
            doneSignal.countDown()
        } catch (_: InterruptedException) {}
    }

    private fun doWork() {
        println("worker $num do work!")
    }
}

class WorkerRunnable(
    private val num: Int,
    private val doneSignal: CountDownLatch
) : Runnable {
    override fun run() {
        try {
            doWork()
            doneSignal.countDown()
        } catch (_: InterruptedException) {}
    }

    private fun doWork() {
        println("work $num is being done!")
    }
}

fun driver2(n: Int) {
    val doneSignal = CountDownLatch(n)
    val executor = Executors.newSingleThreadExecutor()
    for (i in 0 until n) {
        executor.execute(WorkerRunnable(i + 1, doneSignal))
    }
    doneSignal.await()
    println("all works are done")
    executor.shutdown()
}

fun driver(n: Int) {
    val startSignal = CountDownLatch(1)
    val doneSignal = CountDownLatch(n)
    for (i in 0 until n) {
        Thread(Worker(i + 1, startSignal, doneSignal)).start()
    }
    println("do something before start workers")
    Thread.sleep(1000)
    startSignal.countDown()
    println("do my own things, I don't care whether workers finished or not!")
    doneSignal.await()
    println("all workers finished!")
}

fun join() {
    val parser1 = Thread {  }
    val parser2 = Thread { println("parser2 finish") }
    /* ... */
    parser1.start()
    parser2.start()
    parser1.join()
    parser2.join()
    println("all parsers finished")
}

fun cdl() {
    thread {
        println(1)
        c.countDown()
        println(2)
        c.countDown()
    }
    c.await()
    println(3)
}

fun main() {
    driver2(5)
}