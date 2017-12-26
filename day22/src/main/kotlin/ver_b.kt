package b

import java.io.File

object Matrix {

    /*
    key: coordinate
    value: state (clean, infected, weakened, flagged)
     */
    private val map = mutableMapOf<Pair<Int, Int>, Int>()

    private val UP = 1
    private val RIGHT = 2
    private val DOWN = 3
    private val LEFT = 4

    private val CLEAN = 0
    private val INFECTED = 1
    private val WEAKENED = 2
    private val FLAGGED = 3

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

    private fun getValue(pos: Pair<Int, Int>) =
        map.getOrDefault(pos, CLEAN)

    fun start() {
        val bursts = 10_000_000

        for (i in 1..bursts) {
            if (i % 1_000_000 == 0) {
                println("# %d".format(i / 1_000_000))
            }
            //
            val currPos = Pair(currRow, currCol)
            val state = getValue(currPos)

            when (state) {
                CLEAN       -> turnLeft()
                WEAKENED    -> {}    // do nothing
                INFECTED    -> turnRight()
                FLAGGED     -> {    // turn around
                    turnLeft()
                    turnLeft()
                }
            }

            when (state) {
                CLEAN       -> map[currPos] = WEAKENED
                WEAKENED    -> {
                    map[currPos] = INFECTED
                    ++infectionsCaused
                }
                INFECTED    -> map[currPos] = FLAGGED
                FLAGGED     -> map.remove(currPos)    // clean
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
                when (getValue(Pair(i, j))) {
                    0 -> sb.append(".")
                    1 -> sb.append("#")
                }
            }
            sb.append("\n")
        }

        return sb.toString()
    }

    fun debug() {
        println("# map size: ${map.size}")
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
    Matrix.debug()
    println(Matrix.numberOfInfectionsCaused())

}