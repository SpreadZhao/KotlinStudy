package concurrency.fork

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask

class CountTask(
    private val start: Int,
    private val end: Int
) : RecursiveTask<Int>() {

    companion object {
        private const val THRESHOLD = 2
    }

    override fun compute(): Int {
        var sum = 0
        if (end - start > THRESHOLD) {
            val middle = (start + end) / 2
            val leftTask = CountTask(start, middle)
            val rightTask = CountTask(middle + 1, end)
            leftTask.fork()
            rightTask.fork()
            val leftRes = leftTask.join()
            val rightRes = rightTask.join()
            sum = leftRes + rightRes
        } else {
            for (i in start .. end) {
                sum += i
            }
        }
        return sum
    }
}

fun main() {
    val forkJoinPool = ForkJoinPool()
    val task = CountTask(1, 100)
    val result = forkJoinPool.submit(task)
    println(result.get())
}