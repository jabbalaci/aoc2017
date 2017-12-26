package a

/*
Trükk: a regiszterek értékei túl nagyok lehetnek, amik
nem férnek be egy int-be, de egy long-ba már igen!

Vagyis int helyett long típusokat kell használni!
 */

import java.io.File

object Computer {

    private val instructions = mutableListOf<String>()
    private val registers = mutableMapOf<Char, Long>()
    private var lastSoundPlayed = 0L

    init {
        for (c in 'a'..'z') {
            registers[c] = 0
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
                    val value = asInt(parts[2])
                    registers[reg] = value
                }
                "add" -> {
                    val reg = parts[1][0]
                    val value = asInt(parts[2])
                    registers[reg] = registers[reg]!! + value
                }
                "mul" -> {
                    val reg = parts[1][0]
                    val value = asInt(parts[2])
                    registers[reg] = registers[reg]!! * value
                }
                "mod" -> {
                    val reg = parts[1][0]
                    val value = asInt(parts[2])
                    registers[reg] = registers[reg]!! % value
                }
                "jgz" -> {
                    val reg = parts[1][0]
                    val value = asInt(parts[2])
                    if (registers[reg]!! > 0) {
                        i += value.toInt()
                        continue@loop
                    }
                }
                "snd" -> {
                    val reg = parts[1][0]
                    lastSoundPlayed = registers[reg]!!
                }
                "rcv" -> {
                    val reg = parts[1][0]
                    if (registers[reg]!! != 0L) {
                        break@loop
                    }
                }
            }
            //
            ++i
        } // endwhile
    }

    private fun asInt(s: String): Long {
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

    fun getLastSoundPlayed() =
        this.lastSoundPlayed

}


fun main(args: Array<String>) {

    // example
//    val fname = "example.txt"

    // production
    val fname = "input.txt"

    Computer.readFile(fname)

//    println(Computer)
//    println()

    Computer.start()
    println(Computer.getLastSoundPlayed())

}