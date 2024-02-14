package concurrency.pool

import java.util.ArrayList
import java.util.Collections
import java.util.LinkedList
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.max

class DefaultThreadPool<JOB : Runnable> : ThreadPool<JOB> {

    companion object {
        private const val MAX_WORKER_NUMBERS = 10
        private const val DEFAULT_WORKER_NUMBERS = 5
        private const val MIN_WORKER_NUMBERS = 1
    }

    private val jobs = LinkedList<JOB>()
    private val workers = Collections.synchronizedList(ArrayList<Worker>())

    private var workerNum = DEFAULT_WORKER_NUMBERS
    private val threadNum = AtomicLong()

    constructor() {
        initializeWorkers(DEFAULT_WORKER_NUMBERS)
    }

    constructor(num: Int) {
        workerNum = if (num > MAX_WORKER_NUMBERS) MAX_WORKER_NUMBERS else max(MIN_WORKER_NUMBERS, num)
        initializeWorkers(workerNum)
    }

    inner class Worker(var thread: Thread? = null) : Runnable {

        @Volatile
        private var isRunning = true

        override fun run() {
            while (isRunning) {
                var job: JOB
                synchronized(jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait()
                        } catch (_: InterruptedException) {
                            Thread.currentThread().interrupt()
                            return
                        }
                    }
                    job = jobs.removeFirst()
                }
                try {
                    job.run()
                } catch (_: Exception) {}
            }
        }

        fun shutdown() {
            isRunning = false
            thread?.interrupt()
        }
    }

    private fun initializeWorkers(num: Int) {
        repeat(num) {
            val worker = Worker()
            workers.add(worker)
            val thread = Thread(worker, "ThreadPool-Worker-${threadNum.incrementAndGet()}")
            worker.thread = thread
            thread.start()
        }
    }

    override fun execute(job: JOB) {
        synchronized(jobs) {
            jobs.addLast(job)
            jobs.notify()
        }
    }

    override fun shutdown() {
        workers.forEach { it.shutdown() }
    }

    override fun addWorkers(num: Int) {
        var n = num
        synchronized(jobs) {
            if (n + this.workerNum > MAX_WORKER_NUMBERS) {
                n = MAX_WORKER_NUMBERS - this.workerNum
            }
            initializeWorkers(n)
            this.workerNum += n
        }
    }

    override fun removeWorker(num: Int) {
        synchronized(jobs) {
            if (num >= this.workerNum) {
                throw IllegalArgumentException("beyond workNum")
            }
            var count = 0
            while (count < num) {
                val worker = workers[count]
                if (workers.remove(worker)) {
                    worker.shutdown()
                    count++
                }
            }
            this.workerNum -= count
        }
    }

    override val jobSize: Int
        get() = jobs.size

    private fun Any.wait() =
        (this as java.lang.Object).wait()

    private fun Any.notify() =
        (this as java.lang.Object).notify()

}

fun main() {
    val pool = DefaultThreadPool<Runnable>()
    pool.execute {
        println("abc")
    }
    pool.shutdown()
}