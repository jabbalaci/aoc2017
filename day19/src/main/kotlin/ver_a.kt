package a

/*
It contains the solutions for both parts.
 */

import java.io.File

class Matrix(val matrix: List<String>, val lastChar: Char) {

    private val UP = 1
    private val DOWN = 2
    private val LEFT = 3
    private val RIGHT = 4
    //
    private var direction : Int
    private var row : Int
    private var col : Int
    private val sb = StringBuilder()
    private var stepsTaken : Int

    init {
        val firstRow = matrix[0]
        row = 0
        col = firstRow.indexOf('|')
        direction = DOWN
        stepsTaken = 1
    }

    private fun step(direction: Int): Int {
        return when (direction) {
            UP -> -1
            DOWN -> 1
            LEFT -> -1
            RIGHT -> 1
            else -> 0    // should never arrive here
        }
    }

    fun start() {
        var curr = matrix[row][col]
        if (curr in 'A'..'Z') {
            sb.append(curr)
        }

        while (curr != lastChar) {
//            println("# row: %d, col: %d, char: %c".format(row, col, curr))
            when (curr) {
                '|' -> {
                    if (direction == UP || direction == DOWN) {
                        row += step(direction)
                    }
                    else if (direction == LEFT || direction == RIGHT) {
                        col += step(direction)    // step over
                    }
                }
                '-' -> {
                    if (direction == LEFT || direction == RIGHT) {
                        col += step(direction)
                    }
                    else if (direction == UP || direction == DOWN) {
                        row += step(direction)    // step over
                    }
                }
                in 'A'..'Z' -> {
                    if (direction == UP || direction == DOWN) {
                        row += step(direction)
                    }
                    else if (direction == LEFT || direction == RIGHT) {
                        col += step(direction)
                    }
                }
                '+' -> {
                    if (direction == DOWN || direction == UP) {
                        direction = leftOrRight(row, col)
                        col += step(direction)
                    }
                    else if (direction == LEFT || direction == RIGHT) {
                        direction = upOrDown(row, col)
                        row += step(direction)
                    }
                }
            }
//            println("# new values: row: %d, col: %d".format(row, col))
            curr = matrix[row][col]
            ++stepsTaken
            if (curr in 'A'..'Z') {
                sb.append(curr)
            }
        }
    }

    private fun leftOrRight(row: Int, col: Int): Int {
        try {
            val left = matrix[row][col-1]
            if (left != ' ') {
                return LEFT
            }
            return RIGHT
        }
        catch (e: StringIndexOutOfBoundsException) {
            return RIGHT
        }
    }

    private fun upOrDown(row: Int, col: Int): Int {
        try {
            val up = matrix[row-1][col]
            if (up != ' ') {
                return UP
            }
            return DOWN
        }
        catch (e: StringIndexOutOfBoundsException) {
            return DOWN
        }
    }

    fun partOne() =
        this.sb.toString()

    fun partTwo() =
        this.stepsTaken

}

fun main(args: Array<String>) {

    // example
//    val fname = "example.txt"
//    val lastChar = 'F'

    // production
    val fname = "input.txt"
    val lastChar = 'H'

    val matrix = File(fname).readLines()

    val m = Matrix(matrix, lastChar)

    m.start()

    println("Part One (path): %s".format(m.partOne()))
    println("Part Two (steps taken): %d".format(m.partTwo()))

}