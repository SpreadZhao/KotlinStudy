package basic

import java.util.*

// TODO 引用和地址在使用上的区别
class RefCopy {
    class People(val name: String)
}

class People(val age: Int)

fun main() {
    var p1 = RefCopy.People("Spread")
    val p2 = p1
    p1 = RefCopy.People("Zhao")
    println("p1: ${p1.name}")
    println("p2: ${p2.name}")
    var p3 = 5
    val p4 = p3
    p3 = 6
    println("p3: $p3")
    println("p4: $p4")
    val arr = intArrayOf(0, 1, 2, 3)
    val b = arr[2]
    arr[2] = 100
    println("arr: ${arr.contentToString()}")
    println("b: $b")
    val arr2 = arrayOf<People?>(
        People(0),
        People(1),
        People(2),
        People(3)
    )
    val b2 = arr2[2]
    arr2[2] = null
    print("arr2: [")
    arr2.forEach {
        print("${it?.age}, ")
    }
    println("]")
    println("b2: ${b2?.age}")
}