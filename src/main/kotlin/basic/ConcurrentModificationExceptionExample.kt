package basic

object ConcurrentModificationExceptionExample {
    fun test() {
        val list = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val sublist = list.subList(0, 3)
        list.clear()        // ok if no this
        list.addAll(sublist)
        println("list: $list")
    }
}

fun main() {
    ConcurrentModificationExceptionExample.test()
}