package coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Coroutine {
    fun performAsyncOperation(callback: (String) -> Unit) {
        Thread.sleep(1000)
        callback("Callback result")
    }

    suspend fun performSynchronousCall(): String {
        return suspendCoroutine { continuation ->
            performAsyncOperation { result ->
                continuation.resume(result)
            }
        }
    }

    companion object {
        fun testCoroutine() {
            val coroutine = Coroutine()
            println("start")
            val job = GlobalScope.launch {
                val result = coroutine.performSynchronousCall()
                println("result: $result")
            }
            runBlocking {
                job.join()
            }
        }
    }
}