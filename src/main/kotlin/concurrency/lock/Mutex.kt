package concurrency.lock

import concurrency.thread.SleepUtils
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.AbstractQueuedSynchronizer
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import kotlin.concurrent.thread

class Mutex : Lock {

    private val sync = Sync()

    private class Sync : AbstractQueuedSynchronizer() {

        val heldExclusively: Boolean
            get() = state == 1

        override fun isHeldExclusively(): Boolean {
            return heldExclusively
        }

        fun tryLock(): Boolean {
            return tryAcquire(1)
        }

        override fun tryAcquire(arg: Int): Boolean {
            if (compareAndSetState(0, 1)) {
                exclusiveOwnerThread = Thread.currentThread()
                return true
            }
            return false
        }

        override fun tryRelease(release: Int): Boolean {
            if (state == 0) {
                throw IllegalMonitorStateException()
            }
            exclusiveOwnerThread = null
            state = 0
            return true
        }

        fun newCondition() = ConditionObject()

    }

    override fun lock() {
        sync.acquire(1)
    }

    override fun lockInterruptibly() {
        sync.acquireInterruptibly(1)
    }

    override fun tryLock(): Boolean {
        return sync.tryLock()
    }

    override fun tryLock(time: Long, unit: TimeUnit): Boolean {
        return sync.tryAcquireNanos(1, unit.toNanos(time))
    }

    override fun unlock() {
        sync.release(1)
    }

    override fun newCondition(): Condition {
        return sync.newCondition()
    }

    val isLocked: Boolean
        get() = sync.heldExclusively

}


fun test() {
    val mutex = Mutex()
    thread {
        if (!mutex.tryLock()) println("th1 try lock failed!") else println("th1 try lock success")
        println("th1 exit")
    }
    SleepUtils.second(1)
    thread {
        if (!mutex.tryLock()) println("th2 try lock failed!") else println("th2 try lock success")
        println("th2 exit")
    }
}

fun testPrint() {
    val pt1 = MutexTest.MutexPrintThread(1, 2)
    val pt2 = MutexTest.MutexPrintThread(2, 3)
    val pt3 = MutexTest.MutexPrintThread(3, 1)
    pt1.start()
    pt2.start()
    pt3.start()
}

fun startNThread(n: Int) {
    val threads = ArrayList<MutexTest.MutexPrintThread>(n)
    repeat(n) {
        threads.add(MutexTest.MutexPrintThread(it + 1, next(it + 1, n)))
    }
    threads.forEach { it.start() }
}

fun startNThread2(n: Int) {
    val threads = ArrayList<MutexTest.MutexPrintThread2>(n)
    repeat(n) {
        threads.add(MutexTest.MutexPrintThread2(it + 1, next(it + 1, n)))
    }
    threads.forEach { it.start() }
}

fun startNThread3(n: Int) {
    val test = MutexTestN(n)
    val threads = ArrayList<MutexTestN.MutexPrintThread>(n)
    repeat(n) {
        threads.add(MutexTestN.MutexPrintThread(it + 1, next(it + 1, n), test.conditions))
    }
    threads.forEach { it.start() }
}

private fun next(i: Int, n: Int): Int {
    return if (i == n) 1
    else i + 1
}

fun testReenter() {
    val mutex = Mutex()
    mutex.lock()
    mutex.lock()
}


fun main() {
//    val start = System.currentTimeMillis()
//    startNThread(99)
//    startNThread2(99)
    startNThread3(3)
//    testReenter()

}