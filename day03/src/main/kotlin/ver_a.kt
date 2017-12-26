package a

import kotlin.math.abs

fun main(args: Array<String>) {

    val goal = 312051
    var x = 0
    var y = 0
    var num = 1
    var steps = 0

    loop@
    while (true) {
        if (num == goal) break@loop

        ++steps
        // right
        for (i in 1..steps) {
            ++x
            ++num
            if (num == goal) break@loop
        }

        // up
        for (i in 1..steps) {
            ++y
            ++num
            if (num == goal) break@loop
        }

        ++steps
        // left
        for (i in 1..steps) {
            --x
            ++num
            if (num == goal) break@loop
        }

        // down
        for (i in 1..steps) {
            --y
            ++num
            if (num == goal) break@loop
        }
    }

    println(abs(x) + abs(y))

}