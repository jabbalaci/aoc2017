package b

import java.io.File

data class River(val stream: String) {

    private var garbageCnt = 0

    fun analyze() {
        var i = 0
        var inGarbage = false

        while (i <= stream.lastIndex) {
            val c = stream[i]
            when (c) {
                '<' -> if (inGarbage == false) {
                    inGarbage = true
                } else {
                    ++garbageCnt
                }
                '>' -> if (inGarbage) { inGarbage = false }
                '!' -> if (inGarbage) { ++i }
                else -> if (inGarbage) { ++garbageCnt }
            }
            ++i
        }
    }

    fun garbageCounter() =
        this.garbageCnt

}

fun main(args: Array<String>) {

    // example
//    val input = "<{o\"i!a,<{i<a>"

    // production
    val input = File("input.txt").readText()

    val river = River(input)
    println(river)

    river.analyze()
    println(river.garbageCounter())

}