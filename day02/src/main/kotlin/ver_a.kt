package a

import java.io.File

fun main(args: Array<String>) {

    var total = 0
    File("input.txt").forEachLine { line ->
        val nums = line.split(Regex("\\s+")).map { it.toInt() }
        val maxi = nums.max() as Int
        val mini = nums.min() as Int
        total += (maxi - mini)
    }
    println(total)

}