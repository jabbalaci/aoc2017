package a

import java.io.File

fun main(args: Array<String>) {

    // example
//    var input = "abcde".toMutableList()
//    val moves = listOf("s1", "x3/4", "pe/b")

    // production
    var input = "abcdefghijklmnop".toMutableList()
    val moves = File("input.txt").readText().trim().split(",")

    for (move in moves) {
        when (move[0]) {
            's' -> {
                val n = move.substring(1).toInt()
                val vege = input.subList(input.size - n, input.size) //.toMutableList()
                val eleje = input.subList(0, input.size - n) //.toMutableList()
                input = (vege + eleje).toMutableList()
            }
            'x' -> {
                val parts = move.substring(1).split("/").map { it.toInt() }
                val tmp = input[parts[0]]
                input[parts[0]] = input[parts[1]]
                input[parts[1]] = tmp
            }
            'p' -> {
                val c1 = move[1]
                val c2 = move[move.length-1]
                val pos1 = input.indexOf(c1)
                val pos2 = input.indexOf(c2)
                val tmp = input[pos1]
                input[pos1] = input[pos2]
                input[pos2] = tmp
            }
        }
    }

    println(input)
    println(input.joinToString(""))
}