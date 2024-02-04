package concurrency.thread

import java.lang.management.ManagementFactory

fun main(args: Array<String>) {
    val threadMXBean = ManagementFactory.getThreadMXBean()
    val threadInfos = threadMXBean.dumpAllThreads(false, false)
    for (info in threadInfos) {
        println("[${info.threadId}]${info.threadName}")
    }
}