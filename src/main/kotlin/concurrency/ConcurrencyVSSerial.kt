package concurrency

import kotlin.concurrent.thread

class ConcurrencyVSSerial {
    companion object {
        // 循环的次数
        private const val count = 1000000000L
        private fun concurrency() {
            val start = System.currentTimeMillis()
            // a+和b-的操作由两个线程并发
            val th = thread {
                var a = 0
                for (i in 0 until count) {
                    a += 5
                }
            }
            var b = 0
            for (i in 0 until count) {
                b--
            }
            val time = System.currentTimeMillis() - start
            th.join()
            println("concurrency: $time ms, b = $b")
        }

        private fun serial() {
            val start = System.currentTimeMillis()
            // a+和b-的操作在一个线程中串行
            var a = 0
            for (i in 0 until count) {
                a += 5
            }
            var b = 0
            for (i in 0 until count) {
                b--
            }
            val time = System.currentTimeMillis() - start
            println("serial: $time ms, b = $b, a = $a")
        }

        fun startVS() {
            concurrency()
            serial()
        }
    }
}