package concurrency.fork

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask

class Fibonacci(private val n: Int) : RecursiveTask<Int>() {
    override fun compute(): Int {
        var num = 0
        when (n) {
            1 -> {
                num = 1
            }
            2 -> {
                num = 1
            }
            else -> {
                val task1 = Fibonacci(n - 1)
                val task2 = Fibonacci(n - 2)
                task1.fork()
                task2.fork()
                val sum1 = task1.join()
                val sum2 = task2.join()
                num = sum1 + sum2
            }
        }
        return num
    }
}

fun main() {
    val forkJoinPool = ForkJoinPool()
    for (i in 1 .. 10) {
        val task = Fibonacci(i)
        val res = forkJoinPool.submit(task)
        print("${res.get()} ")
    }
}