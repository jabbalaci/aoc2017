package a

import java.io.File

object Matrix {

    /*
    key: currRow, currCol coordinates
    value: 1 - infected; 0 - not infected
     */
    private val map = mutableMapOf<Pair<Int, Int>, Int>()

    private val UP = 1
    private val RIGHT = 2
    private val DOWN = 3
    private val LEFT = 4

    private var rows = 0    // will be changed
    private var cols = 0    // will be changed

    private var currRow = 0    // will be changed
    private var currCol = 0    // will be changed
    private var currDirection = UP

    private var infectionsCaused = 0

    // ----------------------------------------------------------------------

    fun readFile(fname: String) {
        var row = -1

        File(fname).forEachLine { line ->
            ++row
            this.cols = line.length
            line.forEachIndexed { col, c ->
                when (c) {
                    '#' -> map[Pair(row, col)] = 1
                }
            }
        }
        this.rows = row + 1    // if there were 3 rows, this.rows should be 3 then

        this.currRow = rows / 2
        this.currCol = cols / 2
    }

    private fun turnLeft() {
        --currDirection
        if (currDirection < UP) {
            currDirection = LEFT
        }
    }

    private fun turnRight() {
        ++currDirection
        if (currDirection > LEFT) {
            currDirection = UP
        }
    }

    private fun getValue(x: Int, y: Int) =
        map.getOrDefault(Pair(x, y), 0)

    fun start() {
        val bursts = 10000

        for (i in 1..bursts) {
            if (getValue(currRow, currCol) == 1) {    // infected
                turnRight()
                map.remove(Pair(currRow, currCol))    // clean it
            } else {    // not infected (clean)
                turnLeft()
                map[Pair(currRow, currCol)] = 1    // make it infected
                ++infectionsCaused
            }
            moveForward()
        }
    }

    private fun moveForward() {
        when (currDirection) {
            UP      -> --currRow
            RIGHT   -> ++currCol
            DOWN    -> ++currRow
            LEFT    -> --currCol
        }
    }

    fun numberOfInfectionsCaused() =
        this.infectionsCaused

    override fun toString(): String {
        val sb = StringBuilder()

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                when (getValue(i, j)) {
                    0 -> sb.append(".")
                    1 -> sb.append("#")
                }
            }
            sb.append("\n")
        }

        return sb.toString()
    }

    fun debug() {
        println("rows: ${rows}; cols: ${cols}")
        println("center: $currRow, $currCol")
    }

}

fun main(args: Array<String>) {

    // example
//    val fname = "example.txt"

    // production
    val fname = "input.txt"

    Matrix.readFile(fname)
//    Matrix.debug()
//    println(Matrix)

    Matrix.start()
    println(Matrix.numberOfInfectionsCaused())

}