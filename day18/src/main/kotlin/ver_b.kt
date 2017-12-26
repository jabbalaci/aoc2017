package b

/*
Trükk: a regiszterek értékei túl nagyok lehetnek, amik
nem férnek be egy int-be, de egy long-ba már igen!

Vagyis int helyett long típusokat kell használni!
 */

import java.io.File
import java.util.*

class Computer(val id: Long) {

    private val instructions = mutableListOf<String>()
    private val registers = mutableMapOf<Char, Long>()
    private val queue : Queue<Long> = LinkedList()
    private var otherQueue : Queue<Long> = LinkedList()    // will be changed
    private var sent = 0
    private var i = 0    // instruction index pointer
    private var waiting = false

    init {
        for (c in 'a'..'z') {
            registers[c] = 0
        }
        registers['p'] = id
    }

    fun readFile(fname: String) {
        File(fname).forEachLine { line ->
            instructions.add(line)
        }
    }

    fun step() {
        require((i >= 0) && (i <= instructions.lastIndex)) {
            "The instruction index pointer has an invalid value."
        }
        //
        waiting = false
        //
        val curr = instructions[i]
        val parts = curr.split(Regex("\\s+"))
        when (parts[0]) {    // mnemonic
            "set" -> {
                val reg = parts[1][0]
                val value = asLong(parts[2])
                registers[reg] = value
            }
            "add" -> {
                val reg = parts[1][0]
                val value = asLong(parts[2])
                registers[reg] = registers[reg]!! + value
            }
            "mul" -> {
                val reg = parts[1][0]
                val value = asLong(parts[2])
                registers[reg] = registers[reg]!! * value
            }
            "mod" -> {
                val reg = parts[1][0]
                val value = asLong(parts[2])
                registers[reg] = registers[reg]!! % value
            }
            "jgz" -> {
                val regValue = asLong(parts[1])    // it can be a reg.'s name or simply an int
                val value = asLong(parts[2])
                if (regValue > 0) {
                    i += value.toInt()
                    return    // don't step on the next instruction (avoid ++i at the end)
                }
            }
            "snd" -> {
                val value = asLong(parts[1])
                otherQueue.add(value)
                ++sent
            }
            "rcv" -> {
                val reg = parts[1][0]
                if (queue.isEmpty() == false) {
                    val value = queue.poll()    // get the 1st element of the q (and remove it from the q)
                    registers[reg] = value
                } else {
                    // the queue is empty
                    waiting = true
                    return    // don't step on the next instruction (avoid ++i at the end)
                }
            }
        }
        //
        ++i
    }

    private fun asLong(s: String): Long {
        return try {
            s.toLong()
        }
        catch (e: NumberFormatException) {
            registers[s[0]]!!
        }
    }

    fun registerOtherQueue(other: Computer) {
        this.otherQueue = other.queue
    }

    fun waiting() =
        this.waiting

    fun sentValues() =
        this.sent

    fun showQ() {
        println(this.queue)
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (inst in instructions) {
            sb.append(inst)
            sb.append("\n")
        }

        return sb.toString()
    }

}

class MasterControlProgram(val fname: String) {

    private val prg0 = Computer(0L)
    private val prg1 = Computer(1L)

    init {
        prg0.readFile(fname)
        prg1.readFile(fname)
        //
        prg0.registerOtherQueue(prg1)
        prg1.registerOtherQueue(prg0)
    }

    fun start() {
        var cnt = 0
        while (deadlock() == false) {
            prg0.step()
            prg1.step()
            //
            ++cnt
        }
        println("# steps: %d".format(cnt))
    }

    private fun deadlock(): Boolean {
        return prg0.waiting() && prg1.waiting()
    }

    fun getPrg1() =
        prg1

}


fun main(args: Array<String>) {

    // example
//    val fname = "deadlock.txt"

    // production
    val fname = "input.txt"

    val mcp = MasterControlProgram(fname)

    mcp.start()

    println(mcp.getPrg1().sentValues())

}