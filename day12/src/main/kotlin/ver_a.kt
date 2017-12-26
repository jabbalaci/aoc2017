package a

import java.io.File

fun main(args: Array<String>) {

//    val fname = "example.txt"
    val fname = "input.txt"

    val map = readFile(fname)
    val bag = mutableSetOf(0)    // init
    val li = mutableListOf(0)    // init

    var i = 0
    while (i < li.size) {
        val currValue = li[i]
        for (n in map[currValue]!!) {
            if (n !in bag) {
                li.add(n)
                bag.add(n)
            }
        }
        ++i
    }

    println(li.size)

}

fun readFile(fname: String): Map<Int, List<Int>> {

    val map = mutableMapOf<Int, List<Int>>()

    File(fname).forEachLine { line ->
        val parts = line.split("<->")
        val key = parts[0].trim().toInt()
        val value = parts[1].split(",").map { it.trim().toInt() }
        map[key] = value
    }

    return map

}
