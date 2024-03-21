package security

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AESTest {

    /**
     * S盒
     */
    private val S = arrayOf(
        intArrayOf(
            0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
            0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
            0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
            0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
            0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
            0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
            0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
            0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
            0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
            0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
            0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
            0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
            0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
            0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
            0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
        )
    )

    /**
     * 逆S盒
     */
    private val S2 = arrayOf(
        intArrayOf(
            0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
            0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
            0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
            0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
            0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
            0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
            0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
            0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
            0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
            0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
            0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
            0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
            0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
            0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
            0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
            0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
        )

    )

    /**
     * 获取整形数据的低8位的左4个位
     */
    private fun getLeft4Bit(num: Int): Int {
        val left = num and 0x000000f0
        return left shr 4
    }

    /**
     * 获取整形数据的低8位的右4个位
     */
    private fun getRight4Bit(num: Int): Int {
        return num and 0x0000000f
    }

    /**
     * 根据索引，从S盒中获得元素
     */
    private fun getNumFromSBox(index: Int): Int {
        val row = getLeft4Bit(index)
        val col = getRight4Bit(index)
        return S[row][col]
    }

    /**
     * 把一个字符转变成整型
     */
    private fun getIntFromChar(c: Char): Int {
        return c.toInt() and 0x000000ff
    }

    /**
     * 把16个字符转变成4X4的数组，
     * 该矩阵中字节的排列顺序为从上到下，
     * 从左到右依次排列。
     */
    private fun convertToIntArray(str: String): Array<IntArray> {
        val pa = Array(4) { IntArray(4) }
        var k = 0
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                pa[j][i] = getIntFromChar(str[k])
                k++
            }
        }
        return pa
    }

    /**
     * 打印4X4的数组
     */
    private fun printArray(a: Array<IntArray>) {
        for (i in 0 until 4) {
            for (j in 0 until 4)
                print("a[$i][$j] = 0x${a[i][j].toString(16)} ")
            println()
        }
        println()
    }

    /**
     * 打印字符串的ASSCI，
     * 以十六进制显示。
     */
    private fun printASCII(str: String) {
        for (c in str) {
            print("0x${getIntFromChar(c).toString(16)} ")
        }
        println()
    }

    /**
     * 把连续的4个字符合并成一个4字节的整型
     */
    private fun getWordFromStr(str: String): Int {
        val one = getIntFromChar(str[0]) shl 24
        val two = getIntFromChar(str[1]) shl 16
        val three = getIntFromChar(str[2]) shl 8
        val four = getIntFromChar(str[3])
        return one or two or three or four
    }

    /**
     * 把一个4字节的数的第一、二、三、四个字节取出，
     * 入进一个4个元素的整型数组里面。
     */
    private fun splitIntToArray(num: Int): IntArray {
        val array = IntArray(4)
        val one = num shr 24
        array[0] = one and 0x000000ff
        val two = num shr 16
        array[1] = two and 0x000000ff
        val three = num shr 8
        array[2] = three and 0x000000ff
        array[3] = num and 0x000000ff
        return array
    }

    /**
     * 将数组中的元素循环左移step位
     */
    private fun leftLoop4int(array: IntArray, step: Int) {
        val temp = array.copyOf()
        var index = step % 4
        for (i in array.indices) {
            array[i] = temp[index]
            index++
            index %= 4
        }
    }

    /**
     * 把数组中的第一、二、三和四元素分别作为
     * 4字节整型的第一、二、三和四字节，合并成一个4字节整型
     */
    private fun mergeArrayToInt(array: IntArray): Int {
        val one = array[0] shl 24
        val two = array[1] shl 16
        val three = array[2] shl 8
        val four = array[3]
        return one or two or three or four
    }

    /**
     * 常量轮值表
     */
    private val Rcon = intArrayOf(
        0x01000000, 0x02000000,
        0x04000000, 0x08000000,
        0x10000000, 0x20000000,
        0x40000000, 0x80000000.toInt(),
        0x1b000000, 0x36000000
    )

    /**
     * 密钥扩展中的T函数
     */
    private fun T(temp: Int, round: Int): Int {
        val tempArray = splitIntToArray(temp)
        leftLoop4int(tempArray, 1) // 字循环
        for (i in tempArray.indices) {
            tempArray[i] = getNumFromSBox(tempArray[i]) // 字代替
        }
        tempArray[0] = tempArray[0] xor Rcon[round] // 轮常量
        return mergeArrayToInt(tempArray)
    }

    /**
     * 密钥扩展
     */
    private fun keyExpansion(key: String): Array<IntArray> {
        val w = Array(4) { IntArray(44) }
        var temp = 0
        var k = 0
        while (k < 4) {
            for (j in 0 until 4) {
                w[j][k] = getWordFromStr(key.substring(temp, temp + 4))
                temp += 4
            }
            k++
        }
        while (k < 44) {
            val tempArray = splitIntToArray(w[0][k - 1])
            if (k % 4 == 0) {
                tempArray[0] = tempArray[0] xor getNumFromSBox(tempArray[1]) xor Rcon[k / 4 - 1]
            }
            for (j in 0 until 4) {
                w[j][k] = w[j][k - 4] xor tempArray[j]
            }
            k++
        }
        return w
    }

    /**
     * 轮密钥加
     */
    private fun addRoundKey(stateArray: Array<IntArray>, roundKey: Array<IntArray>) {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                stateArray[j][i] = stateArray[j][i] xor roundKey[j][i]
            }
        }
    }

    /**
     * 字节代替
     */
    private fun subBytes(stateArray: Array<IntArray>, isInv: Boolean) {
        for (i in stateArray.indices) {
            for (j in stateArray[i].indices) {
                val temp = stateArray[i][j]
                val row = getLeft4Bit(temp)
                val col = getRight4Bit(temp)
                if (isInv) {
                    stateArray[i][j] = S2[row][col]
                } else {
                    stateArray[i][j] = S[row][col]
                }
            }
        }
    }

    /**
     * 行变换
     */
    private fun shiftRows(stateArray: Array<IntArray>, isInv: Boolean) {
        for (i in 1 until 4) {
            for (j in 0 until i) {
                if (isInv) {
                    val temp = stateArray[i][3]
                    for (k in 3 downTo 1) {
                        stateArray[i][k] = stateArray[i][k - 1]
                    }
                    stateArray[i][0] = temp
                } else {
                    val temp = stateArray[i][0]
                    for (k in 0 until 3) {
                        stateArray[i][k] = stateArray[i][k + 1]
                    }
                    stateArray[i][3] = temp
                }
            }
        }
    }

    /**
     * 列混淆
     */
    private fun mixColumns(stateArray: Array<IntArray>, isInv: Boolean) {
        for (i in 0 until 4) {
            val col = IntArray(4)
            val col2 = IntArray(4)
            for (j in 0 until 4) {
                col[j] = stateArray[j][i]
                col2[j] = stateArray[j][i] and 0x80
                col2[j] = col2[j] shl 1
                col2[j] = col2[j] xor col[j]
            }
            if (isInv) {
                stateArray[0][i] = col2[0] xor col[1] xor col2[1] xor col[2] xor col[3]
                stateArray[1][i] = col[0] xor col2[1] xor col[2] xor col2[2] xor col[3]
                stateArray[2][i] = col[0] xor col[1] xor col2[2] xor col[3] xor col2[3]
                stateArray[3][i] = col2[0] xor col[1] xor col[2] xor col[3] xor col2[3]
            } else {
                stateArray[0][i] = col2[0] xor col[0] xor col2[1] xor col[1] xor col[2]
                stateArray[1][i] = col[1] xor col2[1] xor col[2] xor col2[2] xor col[3]
                stateArray[2][i] = col[2] xor col2[2] xor col[3] xor col2[3] xor col[0]
                stateArray[3][i] = col[3] xor col2[3] xor col[0] xor col2[0] xor col2[1]
            }
        }
    }

    /**
     * AES加密
     */
    private fun aesEncrypt(input: String, key: String): String {
        val inputArray = convertToIntArray(input)
        val keyArray = keyExpansion(key)
        addRoundKey(inputArray, keyArray)

        for (i in 1..9) {
            subBytes(inputArray, false)
            shiftRows(inputArray, false)
            mixColumns(inputArray, false)
            addRoundKey(inputArray, keyArray)
        }

        subBytes(inputArray, false)
        shiftRows(inputArray, false)
        addRoundKey(inputArray, keyArray)

        val result = StringBuilder()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                result.append(inputArray[j][i].toChar())
            }
        }
        return result.toString()
    }

    companion object {
        fun encrypt(plaintext: String, key: String): String {
            val secretKey = SecretKeySpec(key.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES").apply {
                init(Cipher.ENCRYPT_MODE, secretKey)
            }
            val encryptedBytes = cipher.doFinal(plaintext.toByteArray())
            return Base64.getEncoder().encodeToString(encryptedBytes)
        }

        fun decrypt(cipherText: String, key: String): String {
            val secretKey = SecretKeySpec(key.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES").apply {
                init(Cipher.DECRYPT_MODE, secretKey)
            }
            val cipherTextBytes = Base64.getDecoder().decode(cipherText)
            val decryptedBytes = cipher.doFinal(cipherTextBytes)
            return String(decryptedBytes)
        }

        fun test() {
            print("Enter plain text: ")
            val input = Scanner(System.`in`)
            val plainText = input.nextLine()
            print("Enter key: ")
            val key = input.nextLine()
            val cipherText = encrypt(plainText, key)
            println("CipherText: $cipherText")
            println("After decrypt: ${decrypt(cipherText, key)}")
            val myCipherText = AESTest().aesEncrypt(plainText, key)
            println("My CihperText: $myCipherText")
        }
    }
}