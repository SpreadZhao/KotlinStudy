package concurrency.chm

import kotlin.concurrent.thread

fun main() {
    val map = HashMap<String, String>(2)
    val t = Thread({
        for (i in 0 until 100000) {
            thread(name = "ftf$i") {
                map["$i"] = ""
            }
        }
    }, "ftf")
    t.start()
    t.join()
}