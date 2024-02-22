package concurrency.example

class OneToHundred2 {
    companion object {

        var currTh = 1
        var num = 1
    }

    class PrintThread(
        private val thNum: Int,
        private val nextThNum: Int
    ) : Thread("thread-$thNum") {
        override fun run() {
            while (num < 100) {
                if (currTh != thNum) continue
                println("thread $thNum: $num")
                num++
                currTh = nextThNum
            }
        }
    }
}

fun main() {
    startNThread(15)
}

fun startNThread(n: Int) {
    val threads = ArrayList<OneToHundred2.PrintThread>(n)
    repeat(n) {
        threads.add(OneToHundred2.PrintThread(it + 1, next(it + 1, n)))
    }
    threads.forEach { it.start() }
}

private fun next(i: Int, n: Int): Int {
    return if (i == n) 1
    else i + 1
}