package concurrency.thread

import java.util.concurrent.TimeUnit

class SleepUtils {
    companion object {
        @JvmStatic
        fun second(seconds: Long) {
            try {
                TimeUnit.SECONDS.sleep(seconds)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}