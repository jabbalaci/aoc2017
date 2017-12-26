package b

import java.io.File
import java.lang.Math.abs

data class Point(val x: Int, val y: Int, val z: Int) {

    fun distance(b: Point): Int {
        return (abs(x - b.x) + abs(y - b.y) + abs(z - b.z)) / 2
    }

}

fun main(args: Array<String>) {

    // production
    val input = File("input.txt").readText().trim()
    val steps = input.split(",")

    var x = 0
    var y = 0
    var z = 0
    var maxDist = 0

    val p1 = Point(x, y, z)

    for (direction in steps) {
        when(direction) {
            "n"  -> { ++y; --z }
            "ne" -> { ++x; --z }
            "se" -> { ++x; --y }
            "s"  -> { --y; ++z }
            "sw" -> { --x; ++z }
            "nw" -> { ++y; --x }
        }
        val dist = p1.distance(Point(x, y, z))
        if (dist > maxDist) {
            maxDist = dist
        }
    }

    println("Max. distance: %d".format(maxDist))

}