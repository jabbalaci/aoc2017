package b

import java.io.File

fun main(args: Array<String>) {

    val input = File("input.txt").readText()
//    val input = "12131415"
    val pairs = getPairs(input)
    val result = pairs.filter { it.first == it.second }.map { it.first }.sum()
//    println(pairs)
    println(result)

}

fun getPairs(input: String): List<Pair<Int, Int>> {
    var li = ArrayList<Pair<Int, Int>>()
    val lastIndex = input.lastIndex
    val half = input.length / 2
    val s = input + input.substring(0, half)
//    println(s)

    for (i in 0..lastIndex) {
        li.add(Pair(s[i].toString().toInt(), s[i+half].toString().toInt()))
    }

    return li
}