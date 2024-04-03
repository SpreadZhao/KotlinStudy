package basic

object KtCollection {
    val list = listOf(1, 2, 3, 4, 5)
}

fun main() {
    println(KtCollection.list.reduce { acc, i -> acc + i })
}