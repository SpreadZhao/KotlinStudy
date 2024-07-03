package concurrency.lock

import java.util.concurrent.locks.ReentrantLock

class ReentrantLockExample {
    var a = 0
    val lock = ReentrantLock(true)

    fun writer() {
        lock.lock()
        try {
            a++
        } finally {
            lock.unlock()
        }
    }

    fun reader() {
        lock.lock()
        try {
            val i = a
        } finally {
            lock.unlock()
        }
    }
}

fun main() {
    ReentrantLockExample().lock.unlock()
}