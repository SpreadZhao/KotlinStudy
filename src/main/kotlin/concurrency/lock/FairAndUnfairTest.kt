package concurrency.lock

import java.util.concurrent.locks.ReentrantLock

class FairAndUnfairTest {
    companion object {
        private val fairLock = ReentrantLock2(true)
        private val unfairLock = ReentrantLock2(false)

        fun testFair() = testLock(fairLock)

        fun testUnfair() = testLock(unfairLock)

        private fun testLock(lock: ReentrantLock2) {
            repeat(5) {
                val job = Job(lock, it)
                job.start()
            }
        }
    }

    private class Job(private val lock: ReentrantLock2, num: Int) : Thread("$num") {
        override fun run() {
            repeat(3) {
                lockAndLook()
            }
        }

        private fun lockAndLook() {
            lock.lock()
            try {
                println("locked by: ${currentThread().name}, wait queue: ${lock.queuedThreads.map { it.name }}")
            } finally {
                lock.unlock()
            }
        }
    }

    class ReentrantLock2(fair: Boolean) : ReentrantLock(fair) {
        public override fun getQueuedThreads(): MutableCollection<Thread> {
            return super.getQueuedThreads().reversed().toMutableList()
        }
    }
}

fun main() {
    FairAndUnfairTest.testUnfair()
}