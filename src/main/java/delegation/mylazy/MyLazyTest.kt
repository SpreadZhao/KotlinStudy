package delegation.mylazy

class MyLazyTest {
    private val lazyInteger by myLazy {
        println("lazy start!")
        456
    }

    fun useLazy() {
        val i = lazyInteger
        println("i = $i")
    }

    companion object {
        fun test() {
            val testLazyClass = MyLazyTest()
            Thread.sleep(3000)
            testLazyClass.useLazy()
        }
    }
}