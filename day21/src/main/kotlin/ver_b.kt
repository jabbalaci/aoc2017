package b

import java.io.File

class Matrix(rule: String) {

    private var matrix : List<String> = rule.split("/")

    fun rotateLeft() {
        val li = mutableListOf<String>()

        val lastIndex = matrix[0].length - 1
        for (col in lastIndex downTo 0) {
            val fullColumn = matrix.map { row -> row[col] }.joinToString("")
            li.add(fullColumn)
        }

        this.matrix = li
    }

    override fun toString(): String {
        return matrix.joinToString("/")
    }

}

object Line {

    private fun flip(rule: String): String {
        return rule.split("/").map { it.reversed() }.joinToString("/")
    }

    private fun rotateLeft(rule: String): String {
        val m = Matrix(rule)
        m.rotateLeft()
        return m.toString()
    }

    private fun rotateAndFlip(original: String): Set<String> {
        val bag = mutableSetOf<String>()

        // one
        bag.add(original)
        bag.add(flip(original))
        // two (rotate left)
        var curr = rotateLeft(original)
        bag.add(curr)
        bag.add(flip(curr))
        // three (rotate left again)
        curr = rotateLeft(curr)
        bag.add(curr)
        bag.add(flip(curr))
        // four (rotate left again)
        curr = rotateLeft(curr)
        bag.add(curr)
        bag.add(flip(curr))

        return bag
    }

    fun getVariations(line: String): List<Pair<String, String>> {
        val li = mutableListOf<Pair<String, String>>()

        val parts = line.split(" => ")
        val left = parts[0]
        val right = parts[1]

        val leftVariations = rotateAndFlip(left)

        leftVariations.forEach { l ->
            li.add(Pair(l, right))
        }

        return li
    }

}

object Rules {

    private val map = mutableMapOf<String, String>()

    fun readFile(fname: String) {
        File(fname).forEachLine { line ->
            val variations = Line.getVariations(line)
            variations.forEach { v ->
                map[v.first] = v.second
            }
        }
    }

    fun debug() {
        for ((key, value) in map) {
            println("${key} => ${value}")
        }
        println("Map size: ${map.size}")
    }

    fun replaceRule(rule: String) =
        this.map[rule]

}

class Tile(var lines: List<String>) {

    fun getSquares(): List<List<String>> {
        val li = mutableListOf<List<String>>()
        val size = lines.size

        var num : Int
        var squareSize : Int

        if (size % 2 == 0) {
            num = size / 2
            squareSize = 2
        }
        else {
            num = size / 3
            squareSize = 3
        }
//        println("num: $num, square size: $squareSize")
        // generate the positions of the top left corners
        for (i in 0 until num) {
            val tilesInRow = mutableListOf<String>()
            for (j in 0 until num) {
                val row = i * squareSize
                val col = j * squareSize
                val tmp = mutableListOf<String>()
//                println("$row, $col, $squareSize")
//                println(this)
//                println("---------------------------------")
                for (k in 0 until squareSize) {
                    tmp.add(lines[row+k].substring(col, col+squareSize))
                }
                tilesInRow.add(tmp.joinToString("/"))
            }
            li.add(tilesInRow)
        }

        return li
    }

    fun applyRules(squares: List<List<String>>): List<List<String>> {
        val res = mutableListOf<List<String>>()
        squares.forEach { li ->
            val tmp =
                    li.map { rule ->
                Rules.replaceRule(rule)
            }
            res.add(tmp as List<String>)
        }
        return res
    }

    override fun toString(): String {
        return lines.joinToString("\n")
    }

    fun reconstructTileLines(newSquares: List<List<String>>): List<String> {
        val res = mutableListOf<String>()

        for (li in newSquares) {
            val newLines = getLinesFrom(li)
            newLines.forEach { res.add(it) }
        }

        return res
    }

    /*
    Not efficient, could be improved.
    How? Split just once into a list and work with that list.
     */
    fun getLinesFrom(li: List<String>): List<String> {
        val lines = mutableListOf<String>()
        val size = li[0].split("/").size

        for (i in 0 until size) {
            val sb = StringBuilder()
            for (j in 0..li.lastIndex) {
                val parts = li[j].split("/")
                sb.append(parts[i])
            }
            lines.add(sb.toString())
        }

        return lines
    }

    fun pixelsOn() =
        lines.joinToString("").count { it == '#' }

}

fun main(args: Array<String>) {

    // example
//    val fname = "example.txt"

    // production
    val fname = "input.txt"

    Rules.readFile(fname)
//    Rules.debug()

    val lines = listOf(".#.", "..#", "###")

    val iterations = 18
    var tile = Tile(lines)

    repeat(iterations) {
        val squares = tile.getSquares()
        val newSquares = tile.applyRules(squares)
        val newLines = tile.reconstructTileLines(newSquares)
        tile = Tile(newLines)
    }

//    println(tile)
//    println()
    println(tile.pixelsOn())

}