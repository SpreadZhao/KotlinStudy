package concurrency.itc

import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter

class Piped {
    class Print(private val reader: PipedReader) : Runnable {
        override fun run() {
            var receive = 0
            try {
                receive = reader.read()
                while (receive != -1) {
                    print(receive.toChar())
                    receive = reader.read()
                }
            } catch (_: IOException) {
            }
        }
    }
}

fun main() {
    val writer = PipedWriter()
    val reader = PipedReader()
    writer.connect(reader)
    val printThread = Thread(Piped.Print(reader), "PrintThread")
    printThread.start()
    var ch = 0
    writer.use {
        ch = System.`in`.read()
        while (ch != -1) {
            it.write(ch)
            ch = System.`in`.read()
        }
    }
}