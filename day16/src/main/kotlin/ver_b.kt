package b

/*
"The key idea is that instead of waiting for 1,000,000,000 iterations,
we know that doing the same dance on a sequence we've already encountered
will always lead to the exact same cycle."

/u/simonsrealaccount

=> use memoization
 */

import java.io.File

fun main(args: Array<String>) {

    // example
//    var input = "abcde".toMutableList()
//    val moves = listOf("s1", "x3/4", "pe/b")
//    val REPEAT = 2

    // production
    var input = "abcdefghijklmnop".toMutableList()
    val startConfig = input.joinToString("")
    val moves = File("input.txt").readText().trim().split(",")
    val REPEAT = 1_000_000_000

    // register before and after configurations
    val map = mutableMapOf<String, String>()

    /*
    When we get to a config. that we met before, we break
    out of the loop. We have collected all the from -> to
    configurations.
     */
    while (true) {
        val before = input.joinToString("")
        input = doOneDanceCycle(input, moves)
        val after = input.joinToString("")

        map[before] = after

        if (after in map) {
            break
        }
    }

    /*
    Now, using the from -> to map and starting with the
    original config., do the repetition one billion times.
     */
    var key = startConfig
    for (i in 1..REPEAT) {
        if (i % 1_000_000 == 0) {
            println("# %d".format(i / 1_000_000))
        }
        key = map[key]!!
    }
    println(key)

}

// one cycle of dance moves (slow, thus we use memoization)
fun doOneDanceCycle(inp: MutableList<Char>, moves: List<String>): MutableList<Char> {
    var input = inp
    for (move in moves) {
        when (move[0]) {
            's' -> {
                val n = move.substring(1).toInt()
                val vege = input.subList(input.size - n, input.size)
                val eleje = input.subList(0, input.size - n)
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
                val c2 = move[move.length - 1]
                val pos1 = input.indexOf(c1)
                val pos2 = input.indexOf(c2)
                val tmp = input[pos1]
                input[pos1] = input[pos2]
                input[pos2] = tmp
            }
        }
    }
    return input
}
