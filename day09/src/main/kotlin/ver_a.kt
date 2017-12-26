package a

import java.io.File

data class River(val stream: String) {

    private val groupScores = mutableListOf<Int>()
    private var currScore = 0

    fun analyze() {
        var i = 0
        var inGarbage = false

        while (i <= stream.lastIndex) {
            val c = stream[i]
            when (c) {
                '{' -> if (inGarbage == false) { up() }
                '}' -> if (inGarbage == false) { down() }
                '<' -> if (inGarbage == false) { inGarbage = true }
                '>' -> if (inGarbage) { inGarbage = false }
                '!' -> if (inGarbage) { ++i }
            }
            ++i
        }
    }

    private fun down() {
        --currScore
    }

    private fun up() {
        ++currScore
        groupScores.add(currScore)
    }

    fun getGroupScores() =
        this.groupScores

    fun totalScore() =
        this.groupScores.sum()

}

fun main(args: Array<String>) {

    // example
//    val input = "{{<a!>},{<a!>},{<a!>},{<ab>}}"

    // production
    val input = File("input.txt").readText()

    val river = River(input)
    println(river)

    river.analyze()
    println(river.getGroupScores())
    println(river.totalScore())

}