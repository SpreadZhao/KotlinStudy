package concurrency

import kotlin.concurrent.thread

class VolatileExample2 {
    var a = 1

    @Volatile
    var flag = false

    fun writer() {
        a = 3
        Thread.sleep(3000)
        flag = true
    }

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
            val example = VolatileExample2()
            thread { example.writer() }
            thread { example.reader() }
        }
    }
}