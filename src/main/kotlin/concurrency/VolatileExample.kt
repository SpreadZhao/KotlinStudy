package concurrency

class VolatileExample {

    private var _integer = 0

    val integer: Int
        get() = _integer

    @Synchronized
    fun increment() {
        _integer++
    }

    companion object {
        fun test() {
            val volatileExample = VolatileExample()
            val threads = arrayListOf<Thread>()
            repeat(100) {
                val t = Thread {
                    repeat(100) { volatileExample.increment() }
                    println("thread${it} result: ${volatileExample.integer}")
                }
                threads.add(t)
            }
            threads.forEach { it.start() }
            threads.forEach {
                try {
                    it.join()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            println("final result: ${volatileExample.integer}")
        }
    }

}