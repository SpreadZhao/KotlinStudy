package concurrency.chm

import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

fun main() {
    val map = HashMap<String, String>(2)
    val t = Thread({
        for (i in 0 until 100000) {
            thread(name = "ftf$i") {
                map[UUID.randomUUID().toString()] = ""
            }
        }
    }, "ftf")
    t.start()
    t.join()
}