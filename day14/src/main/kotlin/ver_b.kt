package b

import java.io.File

class Matrix(val matrix: List<List<Int>>) {

    private var groupCounter = 0
    private val visited = mutableSetOf<Pair<Int, Int>>()

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in matrix) {
            sb.append(row.joinToString(""))
            sb.append("\n")
        }
        return sb.toString()
    }

    private fun explore(i: Int, j: Int) {    // recursive
        if ((i >= 0) && (i <= matrix.lastIndex) && (j >= 0) && (j <= matrix[0].lastIndex)) {
            if (Pair(i, j) !in visited) {
                if (matrix[i][j] == 1) {
                    visited.add(Pair(i, j))
                    explore(i - 1, j)
                    explore(i + 1, j)
                    explore(i, j - 1)
                    explore(i, j + 1)
                }
            }
        }
    }

    fun findGroups() {
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                val curr = Pair(i, j)
                if ((curr !in visited) && (matrix[i][j] == 1)) {
                    ++groupCounter
                    explore(i, j)
                }
            }
        }
    }

    fun groupNumber() =
        groupCounter
}

fun main(args: Array<String>) {

    // production
    // it was generated from the hashes of the word "uugsqrei",
    // which was my puzzle input
    val fname = "matrix.txt"
    val matrix = readFile(fname)

    // example 1
//    val fname = "pelda1.txt"    // ebben 4 régió van
//    var matrix = readExample(fname)

    // example 2
//    val fname = "pelda2.txt"    // ebben 8 régió van
//    var matrix = readExample(fname)

    val m = Matrix(matrix)
//    println(m)

    m.findGroups()
    println(m.groupNumber())

}

fun readFile(fname: String): List<List<Int>> {

    val matrix = mutableListOf<List<Int>>()

    File(fname).forEachLine { line ->
        matrix.add(line.map { c ->
            c.toString().toInt()
        })
    }

    return matrix

}

fun readExample(fname: String): List<List<Int>> {

    val matrix = mutableListOf<List<Int>>()

    File(fname).forEachLine { l ->
        var line = l.replace('X', '1')
        line = line.replace('O', '1')
        line = line.replace('.', '0')
        matrix.add(line.map { c ->
            c.toString().toInt()
        })
    }

    return matrix

}