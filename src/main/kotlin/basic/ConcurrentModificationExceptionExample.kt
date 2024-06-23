package basic

import java.util.concurrent.CopyOnWriteArrayList

object ConcurrentModificationExceptionExample {
    fun test() {
        val list = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val sublist = list.subList(0, 3)
        list.clear()        // ok if no this
        list.addAll(sublist)
        println("list: $list")
    }

    fun test2() {
        val list = ArrayList<String>()
        list.add("item1")
        list.add("item2")
        val iterator = list.iterator()
        list.add("item3")
        while (iterator.hasNext()) {
            val item = iterator.next()
            println(item)
        }
        println("$list")
    }

    fun test3() {
        val list = CopyOnWriteArrayList<String>()
        list.add("item1")
        list.add("item2")
        val iterator = list.iterator()
        list.add("item3")
        while (iterator.hasNext()) {
            val item = iterator.next()
            println(item)
        }
        println("$list")
    }
}

fun main() {
    ConcurrentModificationExceptionExample.test3()
}