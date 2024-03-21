package tools

import net.coobird.thumbnailator.Thumbnails
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import javax.imageio.ImageIO

object ImageSizeDecider {

    val executor: ExecutorService = Executors.newFixedThreadPool(10)

    val count = AtomicInteger(0)

    val exceptionCount = AtomicInteger(0)

    fun isLandscapeImage(width: Int, height: Int): Boolean {
        return width > height
    }

    fun isPortraitImage(width: Int, height: Int): Boolean {
        return height > width
    }


}

fun testRotate() {
    val srcUrl = "D:\\downloads\\telegram\\ChatExport_2024-02-24\\photos\\photo_1@18-08-2020_14-15-23.jpg"
    val srcFile = File(srcUrl)
    if (srcFile.exists() && srcFile.isFile) {
        rotateImage(srcFile)
    }
}

fun testCopy() {
    val srcUrl = "D:\\temp\\files\\photo_618@21-10-2020_07-59-45.jpg"
    val destUrl = "D:\\temp\\wallpaper5\\photo_618@21-10-2020_07-59-45.jpg"
    val srcFile = File(srcUrl)
    if (srcFile.exists() && srcFile.isFile) {
        copyFile(srcFile, destUrl)
    }
}

fun blockImgs(folderUrl: String) {
    val folder = File(folderUrl)
    if (folder.isDirectory) {
        val files = folder.listFiles() ?: return
        val size = files.size
        val step = 200
        var start = 0
        var count = 17
        while (start < size) {
            for (i in start until start + step) {
                if (i >= size) {
                    break
                }
                val file = files[i]
                val destFolderUrl = "D:\\temp\\landscape\\out\\wallpaper${count}"
                val destFolder = File(destFolderUrl)
                if (!destFolder.exists()) {
                    destFolder.mkdir()
                }
                val destFileUrl = "${destFolderUrl}\\${file.name}"
                ImageSizeDecider.executor.execute {
                    copyFile(file, destFileUrl)
                }
            }
            start += step
            count++
        }
    }
}


const val CHOOSE_DEST_URL = "D:\\temp\\landscape\\2"
const val CHOOSE_SRC_URL = "D:\\files\\chatfiles\\telegram\\ChatExport_2024-02-25\\photos"
const val CHOOSE_DEST_URL_2 = "D:\\temp\\landscape\\3"
const val CHOOSE_SRC_URL_2 = "D:\\downloads\\telegram\\ChatExport_2024-02-25\\photos"

fun rotateImage(srcFile: File) {
    val destFileUrl = "${CHOOSE_DEST_URL}\\${srcFile.name}"
    val destFile = File(destFileUrl)
    if (!destFile.exists()) {
        destFile.createNewFile()
    } else {
        ImageSizeDecider.count.getAndIncrement()
        return
    }
    try {
        Thumbnails.of(srcFile).scale(1.0).rotate(90.0).outputQuality(1.0).toFile(destFile)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun copyFile(srcFile: File) {
    val destFileUrl = "${CHOOSE_DEST_URL}\\${srcFile.name}"
//    println("dest url: $destFileUrl")
    val destFile = File(destFileUrl)
    if (!destFile.exists()) {
        destFile.createNewFile()
        ImageSizeDecider.count.getAndIncrement()
    } else {
        return
    }
    val sourceChannel = FileInputStream(srcFile).channel
    val destChannel = FileOutputStream(destFile).channel
    destChannel.transferFrom(sourceChannel, 0, sourceChannel.size())
//    println("transfer finished")
    sourceChannel.close()
    destChannel.close()
    println("count: ${ImageSizeDecider.count.get()}, file: ${destFile.name}")
}

fun copyFile(srcFile: File, destUrl: String) {
    val destFile = File(destUrl)
    if (!destFile.exists()) {
        destFile.createNewFile()
        ImageSizeDecider.count.getAndIncrement()
    } else {
        println("file existed: $destUrl, return")
        return
    }
    val sourceChannel = FileInputStream(srcFile).channel
    val destChannel = FileOutputStream(destFile).channel
    destChannel.transferFrom(sourceChannel, 0, sourceChannel.size())
    sourceChannel.close()
    destChannel.close()
    println("count: ${ImageSizeDecider.count.get()}, file: ${destFile.name}")
}

fun main() {
//    deleteRedundant(CHOOSE_SRC_URL)
//    deleteRedundant(CHOOSE_SRC_URL_2)
//    try {
//        choose(CHOOSE_SRC_URL, CHOOSE_DEST_URL)
//        choose(CHOOSE_SRC_URL_2, CHOOSE_DEST_URL_2)
//    } catch (e: Exception) {
//        ImageSizeDecider.exceptionCount.getAndIncrement()
//    }

//    while (true) {
//        println("exception count: ${ImageSizeDecider.exceptionCount.get()}")
//        TimeUnit.SECONDS.sleep(10)
//    }

//
//    testRotate()
//    testCopy()
    blockImgs(CHOOSE_DEST_URL_2)
}

fun choose(srcFolder: String, destFolder: String) {
//    val folderUrl = "D:\\downloads\\telegram\\ChatExport_2024-02-25\\photos"

    val imageFolder = File(srcFolder)
    if (imageFolder.isDirectory) {
        val allFiles = imageFolder.listFiles() ?: return
        for (file in allFiles) {
            ImageSizeDecider.executor.execute {
                val img = ImageIO.read(file)
                val width = img.width
                val height = img.height
                if (ImageSizeDecider.isLandscapeImage(width, height)) {
                    val destUrl = "${destFolder}\\${file.name}"
                    copyFile(file, destUrl)
                }
            }
        }
    }
}

fun deleteRedundant(url: String) {
    val folder = File(url)
    if (folder.isDirectory) {
        folder.listFiles()?.forEach {
            if (it.name.contains("thumb")) {
                ImageSizeDecider.executor.execute {
                    it.delete()
                }
            }
        }
    }
}