package a

import java.io.File

fun main(args: Array<String>) {

    var valid = 0

    File("input.txt").forEachLine { line ->
        val words = line.split(Regex("\\s+"))
        if (words.size == words.toSet().size) {
            ++valid
        }
    }

    println(valid)

}