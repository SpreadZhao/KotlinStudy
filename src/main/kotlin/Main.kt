import concurrency.ConcurrencyVSSerial
import concurrency.Deadlock

fun main(args: Array<String>) {
//    ConcurrencyVSSerial.startVS()
    Deadlock().deadlock()
//    Deadlock().dummyDeadlock()
}