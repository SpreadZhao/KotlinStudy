package concurrency.lock

import concurrency.thread.SleepUtils
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.AbstractQueuedSynchronizer
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock

class TwinsLock : Lock {

    class Worker(val lock: TwinsLock) : Thread() {
        override fun run() {
            while (true) {
                lock.lock()
                try {
                    println(currentThread().name)
                    SleepUtils.second(1)
                } finally {
                    lock.unlock()
                }
            }
        }
    }

    companion object {
        fun test() {
            val lock = TwinsLock()
            repeat(10) {
                val w = Worker(lock)
                w.isDaemon = true
                w.start()
            }
            SleepUtils.second(100)
        }
    }

    private val sync = Sync(2)

    private class Sync(count: Int) : AbstractQueuedSynchronizer() {
        init {
            if (count <= 0) {
                throw IllegalArgumentException("count >= 0 needed.")
            }
            this.state = count
        }

        fun tryLock(): Boolean {
            return tryAcquireShared(1) >= 0
        }

        override fun tryAcquireShared(acquired: Int): Int {
            while (true) {
                val curr = state
                val after = curr - acquired
                if (after < 0 || compareAndSetState(curr, after)) {
                    return after
                }
            }
        }

        override fun tryReleaseShared(released: Int): Boolean {
            while (true) {
                val curr = state
                val after = curr + released
                if (compareAndSetState(curr, after)) {
                    return true
                }
            }
        }

        fun newCondition() = ConditionObject()
    }

    override fun lock() {
        sync.acquireShared(1)
    }

    override fun unlock() {
        sync.releaseShared(1)
    }

    override fun lockInterruptibly() {
        sync.acquireInterruptibly(1)
    }

    override fun tryLock(): Boolean {
        return sync.tryLock()
    }

    override fun tryLock(time: Long, unit: TimeUnit): Boolean {
        return sync.tryAcquireNanos(1, time)
    }


    override fun newCondition(): Condition {
        return sync.newCondition()
    }

}

fun main() {
    TwinsLock.test()
}