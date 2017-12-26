package a

import java.io.File

object Computer {

    private val instructions = mutableListOf<String>()
    private val registers = mutableMapOf<Char, Long>()
    private var mulOperations = 0

    init {
        for (c in 'a'..'h') {
            registers[c] = 0L
        }
    }

    fun readFile(fname: String) {
        File(fname).forEachLine { line ->
            instructions.add(line)
        }
    }

    fun start() {
        var i = 0

        loop@
        while (true) {
            if ((i < 0) || (i > instructions.lastIndex)) {
                break
            }
            //
            val curr = instructions[i]
            val parts = curr.split(Regex("\\s+"))
            when (parts[0]) {    // mnemonic
                "set" -> {
                    val reg = parts[1][0]
                    val value = asNum(parts[2])
                    registers[reg] = value
                }
                "sub" -> {
                    val reg = parts[1][0]
                    val value = asNum(parts[2])
                    registers[reg] = registers[reg]!! - value
                }
                "mul" -> {
                    val reg = parts[1][0]
                    val value = asNum(parts[2])
                    registers[reg] = registers[reg]!! * value
                    ++mulOperations
                }
                "jnz" -> {
                    val value = asNum(parts[1])
                    val offset = parts[2].toInt()
                    if (value != 0L) {
                        i += offset
                        continue@loop
                    }
                }
            }
            //
            ++i
        } // endwhile
    }

    private fun asNum(s: String): Long {
        try {
            return s.toLong()
        }
        catch (e: NumberFormatException) {
            return registers[s[0]]!!
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (inst in instructions) {
            sb.append(inst)
            sb.append("\n")
        }

        return sb.toString()
    }

    fun numberOfMulOperations() =
        this.mulOperations

}


fun main(args: Array<String>) {

    val fname = "input.txt"

    Computer.readFile(fname)

//    println(Computer)
//    println()

    Computer.start()
    println(Computer.numberOfMulOperations())

}