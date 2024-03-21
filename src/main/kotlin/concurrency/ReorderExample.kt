package concurrency

import kotlin.concurrent.thread

class ReorderExample {
    var a = 1
    var flag = false

    @Synchronized
    fun writer() {
        a = 3
        Thread.sleep(10000)
        flag = true
    }

    @Synchronized
    fun reader() {
        if (flag) {
            val i = a * a
            println("i: $i")
        } else {
            println("I can't read it!")
        }
    }

    companion object {
        fun test() {
            val example = ReorderExample()
            thread { example.writer() }
            thread { example.reader() }
        }
    }
}