package concurrency

class Deadlock {
    companion object {
        // const means static
        private const val A = "A"
        private const val B = "B"
    }

    fun dummyDeadlock() {
        val t1 = Thread {
            synchronized(A) {
                Thread.sleep(1000 * 10)
            }
            println("1")
        }
        val t2 = Thread {
            synchronized(A) {
                println("2")
            }
        }
        t1.start()
        t2.start()
    }

    fun deadlock() {
        val t1 = Thread {
            synchronized(A) {
                Thread.sleep(1000)
                synchronized(B) {
                    println("1")
                }
            }
        }
        val t2 = Thread {
            synchronized(B) {
                synchronized(A) {
                    println("2")
                }
            }
        }
        t1.start()
        t2.start()
    }
}