package basic

class WierdThread {
    private val thread = object : Thread() {
        fun bark() {}
        override fun run() {
            super.run()
        }
    }

    private val thread2 = object : Thread() {
        fun bark2() {}
        override fun run() {
            super.run()
        }
    }

//    val thread = MyThread()

    fun start() {
        thread.bark()
        thread2.bark2()
    }

}

class MyThread : Thread() {
    fun bark() {}
    override fun run() {
        super.run()
    }
}