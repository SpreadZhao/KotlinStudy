class TimeCost {
    companion object {
        fun countTime(block: () -> Unit) {
            val startTime = System.currentTimeMillis()
            block()
            val endTime = System.currentTimeMillis()
            println("time cost: ${endTime - startTime}")
        }
    }
}