package a

import java.io.File

fun main(args: Array<String>) {

//    val fname = "example.txt"
    val fname = "input.txt"
    val allPrograms = mutableSetOf<String>()
    val programsWithParent = mutableSetOf<String>()

    File(fname).forEachLine { line ->

        val parts = line.split(Regex(" -> "))
        allPrograms.add(parts[0].split(Regex("\\s+"))[0])
        if (parts.size == 2) {
            val pieces = parts[1].split(", ")
            pieces.forEach { prg ->
                allPrograms.add(prg)
                programsWithParent.add(prg)
            }
        }
    }

    val result = allPrograms.minus(programsWithParent).toList()[0]
    println(result)

}