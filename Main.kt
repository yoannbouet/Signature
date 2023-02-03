package signature

import java.io.File

const val SPACE = " "
const val FIVE_SPACES = "     "
const val TEN_SPACES = "          "
const val EIGHT = "8"

class Signature {
    private val signature: MutableList<MutableList<String>> = mutableListOf()
    private val romanText = File("C:/roman.txt").readLines()
    private val mediumText = File("C:/medium.txt").readLines()

    init {
        val (namePrompt, name) = Pair("Enter name and surname:".also { print(it) }, readln())
        val (statusPrompt, status) = Pair("Enter person's status:".also { print(it) }, readln().uppercase())

        var nameLength = 0
        name.forEach {
            if (it.toString() == SPACE) {
                nameLength += TEN_SPACES.length
            } else if (it.isUpperCase()) {
                nameLength += romanText[287 + charOffset(it) * 11].split(' ').last().toInt()
            } else if (it.isLowerCase()) {
                nameLength += romanText[1 + charOffset(it) * 11].split(' ').last().toInt()
            }
        }
        var statusLength = 0
        status.forEach {
            statusLength += if (it.toString() == SPACE) {
                FIVE_SPACES.length
            } else {
                mediumText[1 + charOffset(it) * 4].last().digitToInt()
            }
        }
        val longestLine = if (nameLength > statusLength) nameLength else statusLength

        val nameSpace = SPACE.repeat((longestLine - nameLength) / 2)
        val statusSpace = SPACE.repeat((longestLine - statusLength) / 2)

        val oddSpace = if ((nameLength - statusLength) % 2 != 0) SPACE else ""
        val oddName = if (nameLength == longestLine) "" else oddSpace
        val oddStatus = if (statusLength == longestLine) "" else oddSpace

        signature.add(mutableListOf(EIGHT.repeat(longestLine + 8)))
        for (i in 1..10) signature.add(mutableListOf(EIGHT, EIGHT, SPACE, SPACE, nameSpace))
        name.forEach { ch ->
                for (i in 1..10) {
                    if (ch.isUpperCase()) {
                        signature[i] += romanText[288 + i - 1 + charOffset(ch) * 11]
                    } else if (ch.isLowerCase()) {
                        signature[i] += romanText[2 + i - 1 + charOffset(ch) * 11]
                    } else signature[i] += TEN_SPACES
                }
        }
        for (i in 1..10) signature[i] += nameSpace + SPACE + SPACE + oddName + EIGHT + EIGHT

        for (i in 11..13) signature.add(mutableListOf(EIGHT, EIGHT, SPACE, SPACE, statusSpace))
        status.forEach { ch ->
            for (i in 11..13) {
                if (ch.isUpperCase()) {
                    signature[i] += mediumText[2 + i - 11 + charOffset(ch) * 4]
                } else signature[i] += FIVE_SPACES
            }
        }
        for (i in 11..13) signature[i] += statusSpace + SPACE + SPACE + oddStatus + EIGHT + EIGHT
        signature.add(mutableListOf(EIGHT.repeat(longestLine + 8)))

        signature.forEach { list -> list.forEach { print(it) }; println() }
    }

    private fun charOffset(ch: Char): Int = if (ch.isUpperCase()) ch.code - 65 else ch.code - 97
}

fun main() {
    Signature()
}