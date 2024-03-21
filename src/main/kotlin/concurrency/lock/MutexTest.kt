package concurrency.lock

class MutexTest {

    companion object {
        var i = 1
        var currThNum = 1
        val mutex = Mutex()
        val condition = mutex.newCondition()
    }

    // 1-100 cross print
    class MutexPrintThread(private val thNum: Int, private val otherNum: Int) : Thread("mutex-thread-$thNum") {
        override fun run() {
            while (i < 100) {
                mutex.lock()
                if (currThNum != thNum) {
                    mutex.unlock()
                    continue
                }
                println("thread $thNum print $i")
                currThNum = otherNum
                i++
                mutex.unlock()
            }
        }
    }

    class MutexPrintThread2(private val thNum: Int, private val otherNum: Int) : Thread("mutex-thread-$thNum") {
        override fun run() {
            while (i < 100) {
                mutex.lock()
                while (currThNum != thNum) {
                    condition.await()
                }
                println("thread $thNum print $i")
                currThNum = otherNum
                i++
                condition.signalAll()
                mutex.unlock()
            }
        }
    }
}