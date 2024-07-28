package concurrency.atomic

import java.util.concurrent.atomic.AtomicInteger

private val ai = AtomicInteger(1)

fun main() {
    println(ai.getAndIncrement())
    println(ai.get())
}