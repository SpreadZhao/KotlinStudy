package concurrency.example

/**
 * 三个线程交替打印1-100
 * th1: 1
 * th2: 2
 * th3: 3
 * th1: 4
 * th2: 5
 * ...
 */
class OneToHundred {

    companion object {
        private val lock = Object()
        private var currThread = 1
        private var currNum = 1
        private val Int.next: Int
            get() = if (this == 3) 1 else this + 1
        private var isRunning = true
    }

    private val th1 = Thread(PrintRunnable(1))
    private val th2 = Thread(PrintRunnable(2))
    private val th3 = Thread(PrintRunnable(3))

    fun start() {
        th1.start()
        th2.start()
        th3.start()
    }

    class PrintRunnable(private val thNum: Int) : Runnable {
        override fun run() {
            while (currNum <= 100) {
                synchronized(lock) {
                    while (currThread != thNum && isRunning) {
                        try {
                            lock.wait()
                        } catch (_: InterruptedException) {
                        }
                    }
                    if (currNum == 100) {
                        /*
                            如果th1没有将isRunning关闭，就这么走下去。
                            只有th2能结束，th3依然wait。因为currThread
                            之后变成的是2。
                         */
                        isRunning = false
                    } else if (currNum > 100) {
                        return
                    }
                    println("th${thNum}: ${currNum++}")
                    lock.notifyAll()
                    currThread = currThread.next
                }
            }
        }
    }
}

fun main() {
    OneToHundred().start()
}