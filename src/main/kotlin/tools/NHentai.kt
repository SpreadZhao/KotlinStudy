package tools

import java.net.HttpURLConnection
import java.net.URL

object NHentai {
    const val BASIC_URL = "https://cdn.nhentai.com/nhentai/storage/images/1208/"
    fun start() {
        for (i in 1..20) {
            val url = "${BASIC_URL}${i}.jpg"
            val connection = URL(url).openConnection() as HttpURLConnection

        }
    }
}