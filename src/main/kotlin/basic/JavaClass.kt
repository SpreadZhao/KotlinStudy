package basic

class JavaClass {
    open class Parent
    class Child : Parent()
}

fun main() {
    val person: List<JavaClass.Parent> = listOf(JavaClass.Child())
    val p = person[0]
    println(p::class.java === JavaClass.Child::class.java)      // true
    println(p::class.java === JavaClass.Child::javaClass)       // false
    println(p.javaClass === JavaClass.Child::class.java)        // true
    println(p.javaClass === JavaClass.Child::javaClass)         // false
}