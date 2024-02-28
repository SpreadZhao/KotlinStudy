package concurrency.lock

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.thread

object TestReadWriteLock {

    private val map = HashMap<String, Any>()
    private val rwl = ReentrantReadWriteLock()
    private val rl = rwl.readLock()
    private val wl = rwl.writeLock()

    operator fun get(key: String): Any? {
        rl.lock()
        try {
            println("read locked by ${Thread.currentThread().name}")
            return map[key]
        } finally {
            rl.unlock()
        }
    }

    operator fun set(key: String, value: Any): Any? {
        wl.lock()
        try {
            println("write locked by ${Thread.currentThread().name}")
            return map.put(key, value)
        } finally {
            wl.unlock()
        }
    }

    fun clear() {
        wl.lock()
        try {
            map.clear()
        } finally {
            wl.unlock()
        }
    }
}

fun main() {
    val key = "1"
    var obj = arrayOf(1, 2, 3)
    repeat(100) {
        thread {
            TestReadWriteLock[key] = obj
            obj = obj.map { it.plus(1) }.toTypedArray()
//            print("${Thread.currentThread().name}: ")
            val i = TestReadWriteLock[key]
//            println()
        }
    }
}