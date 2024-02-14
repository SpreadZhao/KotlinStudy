package concurrency.pool

interface ThreadPool<JOB : Runnable> {
    fun execute(job: JOB)
    fun shutdown()
    fun addWorkers(num: Int)
    fun removeWorker(num: Int)
    val jobSize: Int
}