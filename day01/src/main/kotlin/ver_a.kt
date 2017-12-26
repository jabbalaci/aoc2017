package a

import java.io.File

fun main(args: Array<String>) {

    val input = File("input.txt").readText()
//    val input = "91212129"
    val pairs = getPairs(input)
    val result = pairs.filter { it.first == it.second }.map { it.first }.sum()
//    println(pairs)
    println(result)

}

fun getPairs(input: String): List<Pair<Int, Int>> {
    var li = ArrayList<Pair<Int, Int>>()
    val s = input + input[0]

    for (i in 1..(s.lastIndex)) {
        li.add(Pair(s[i-1].toString().toInt(), s[i].toString().toInt()))
    }

    return li
}
