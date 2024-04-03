package basic

// TODO 引用和地址在使用上的区别
class RefCopy {
    class People(val name: String)
}

fun main() {
    var p1 = RefCopy.People("Spread")
    val p2 = p1
    p1 = RefCopy.People("Zhao")
    println("p1: ${p1.name}")
    println("p2: ${p2.name}")
}