package concurrency.lock

import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.jvm.Throws

class BoundedBuffer<E : Any> {
    private val lock: Lock = ReentrantLock()
    private val notFull: Condition = lock.newCondition()
    private val notEmpty: Condition = lock.newCondition()

    val items: Array<Any> = Array(100) {}

    // put item pointer, take item pointer, actual item count
    private var putptr = 0
    private var takeptr = 0
    private var count = 0

    @Throws(InterruptedException::class)
    fun put(x: E) {
        lock.lock()
        try {
            while (count == items.size) {
                notFull.await()
            }
            items[putptr] = x
            if (++putptr == items.size) {
                putptr = 0
            }
            ++count
            notEmpty.signal()
        } finally {
            lock.unlock()
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(InterruptedException::class)
    fun take(): E {
        lock.lock()
        try {
            while (count == 0) {
                notEmpty.await()
            }
            val item = items[takeptr] as E
            if (++takeptr == items.size) {
                takeptr = 0
            }
            --count
            notFull.signal()
            return item
        } finally {
            lock.unlock()
        }
    }
}