package concurrency.itc

class SynchronizedExample {
    companion object {
        @Synchronized
        @JvmStatic
        fun m() {

        }
    }
}

fun main() {
    synchronized(SynchronizedExample.Companion::class.java) {

    }
    SynchronizedExample.m()
}