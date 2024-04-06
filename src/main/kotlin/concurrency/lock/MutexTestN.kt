package concurrency.lock

import java.util.concurrent.locks.Condition


/**
 * n个线程交替打印
 */
class MutexTestN(threadCount: Int) {

    companion object {
        private val mutex = Mutex()
        private var currThNum = 1
        private var i = 1
    }

    val conditions = Array(threadCount) { mutex.newCondition() }

    class MutexPrintThread(
        private val thNum: Int,
        private val otherNum: Int,
        private val conditions: Array<Condition>
    ) : Thread("print-thread-$thNum") {
        override fun run() {
            while (i < 100) {
                mutex.lock()
                try {
                    if (thNum != currThNum) {
                        conditions[thNum - 1].await()
                    }
                    if (i <= 100) {
                        println("${name}: $i")
                    }
                    i++
                    currThNum = otherNum
                    conditions[otherNum - 1].signal()
                } finally {
                    mutex.unlock()
                }
            }
        }
    }
}