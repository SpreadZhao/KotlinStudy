package concurrency.itc

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Local {
    companion object {
        private val formatter = ThreadLocal.withInitial { SimpleDateFormat("yyyyMMdd HHmm") }
        var publicValue = 0
    }

    class Formatter : Runnable {
        override fun run() {
            println("Thread name: ${Thread.currentThread().name}, default Formatter: ${formatter.get().toPattern()}")
            try {
                Thread.sleep(Random.nextLong(1000))
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            formatter.set(SimpleDateFormat())
            println("Thread name: ${Thread.currentThread().name}, Formatter: ${formatter.get().toPattern()}")
        }
    }


    class LocalThread : Thread() {
        var value = 0
        override fun run() {
            value++
            publicValue++
        }
    }

}

fun main() {
    testLocal()
}

fun format() {
    val example = Local()
    for (i in 0 until 10) {
        val t = Thread(Local.Formatter(), "$i")
        Thread.sleep(Random.nextLong(1000))
        t.start()
    }
}

fun testLocal() {
    val th1 = Local.LocalThread()
    val th2 = Local.LocalThread()
    println("th1 val: ${th1.value}, th2 val: ${th2.value}, public val: ${Local.publicValue}")
    th1.start()
    TimeUnit.SECONDS.sleep(1)
    println("th1 val: ${th1.value}, th2 val: ${th2.value}, public val: ${Local.publicValue}")
}