package a

/*
Read this to understand hex grids:

https://www.redblobgames.com/grids/hexagons/

I used cube coordinates.
 */

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

    // examples
//    val steps = "ne,ne,ne".split(",")
//    val steps = "ne,ne,sw,sw".split(",")
//    val steps = "ne,ne,s,s".split(",")
//    val steps = "se,sw,se,sw,sw".split(",")

    var x = 0
    var y = 0
    var z = 0

    val p1 = Point(x, y, z)
    println(p1)

    for (direction in steps) {
        when(direction) {
            "n"  -> { ++y; --z }
            "ne" -> { ++x; --z }
            "se" -> { ++x; --y }
            "s"  -> { --y; ++z }
            "sw" -> { --x; ++z }
            "nw" -> { ++y; --x }
        }
    }

    val p2 = Point(x, y, z)
    println(p2)

    println()

    val dist = p1.distance(p2)
    println("Distance: %d".format(dist))

}