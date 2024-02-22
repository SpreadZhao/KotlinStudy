package concurrency.lock

class LockTest {

    companion object {
        var i = 1
        var currThNum = 1
        val mutex = Mutex()
    }

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
}