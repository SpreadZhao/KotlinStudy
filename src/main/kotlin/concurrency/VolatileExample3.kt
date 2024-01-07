package concurrency

class VolatileExample3 {

    var flag = false

    var a = 0

    fun write() {
        a = 1
        Thread.sleep(10)
        flag = true
    }

    companion object {
        @JvmStatic
        fun test() {
            val example = VolatileExample3()
            val th = Thread {
                example.write()
            }
            th.start()
            while (!example.flag) {
                synchronized(this) {}
            }
            println("a = ${example.a}")
            th.join()
        }
    }
}