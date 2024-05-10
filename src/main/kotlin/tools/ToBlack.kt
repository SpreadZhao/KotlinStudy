package tools

import net.coobird.thumbnailator.Thumbnails
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import javax.imageio.ImageIO

fun main() {
    val file = File("/home/spreadzhao/temp/qrcode.jpg")
    if (file.isFile && file.exists()) {
        println("file exists")
    }
    val img = Thumbnails.of(file).scale(1.0).asBufferedImage()
    val width = img.width
    val height = img.height
    val pixels = Array(height) { Array<Color?>(width) { null } }
    for (y in 0 until height) {
        for (x in 0 until width) {
            pixels[y][x] = Color(img.getRGB(x, y))
        }
    }
    val log = File("/home/spreadzhao/temp/qrlog.txt")
    if (!log.exists()) {
        log.createNewFile()
    }
    val writer = BufferedWriter(FileWriter(log))
    writer.use {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val color = pixels[y][x] ?: continue
//                it.write("Pixel($x, $y): red[${color.red}], green[${color.green}], blue[${color.blue}]\n")
                if (color.red >= 200 && color.green >= 200 && color.blue >= 200) {
                    pixels[y][x] = Color(0, 0, 0)
                } else if (color.red in 100..180 && color.green in 60..170 && color.blue in 230..250) {
                    pixels[y][x] = Color(255, 255, 255)
                }
                val newColor = pixels[y][x]?: continue
                it.write("Pixel($x, $y): red[${newColor.red}], green[${newColor.green}], blue[${newColor.blue}]\n")
            }
        }
    }

    val newpic = File("/home/spreadzhao/temp/qrcode_new.jpg")
    if (newpic.exists()) {
        return
    }
    newpic.createNewFile()
    val newBufferedImg = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until height) {
        for (x in 0 until width) {
            val rgb = pixels[y][x]?.rgb ?: continue
            newBufferedImg.setRGB(x, y, rgb)
        }
    }
    ImageIO.write(newBufferedImg, "jpg", newpic)
}