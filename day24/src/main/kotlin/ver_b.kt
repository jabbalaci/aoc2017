package b

import java.io.File

val NOT_FOUND = -1
val LEFT = 0
val RIGHT = 1

data class Component(val id: Int, val line: String) {

    var left : Int = 0    // will be changed
    var right : Int = 0    // will be changed

    init {
        val parts = line.split("/")
        left = parts[0].toInt()
        right = parts[1].toInt()
    }

    fun swap() {
        val tmp = left
        left = right
        right = tmp
    }

    override fun toString(): String {
        return "Component($left/$right)"
    }

    /*
    NOT_FOUND: the value is different from left and right
    LEFT: the value equals the left part
    RIGHT: the value equals the right part
     */
    fun find(value: Int): Int {
        if (value == left) { return LEFT }
        else if (value == right) { return RIGHT }
        else { return NOT_FOUND }
    }

}

object BridgeMaker {

    val components = mutableListOf<Component>()
    var strongest = 0
    var longest = 0

    fun readFile(fname: String) {
        var id = 0
        File(fname).forEachLine { line ->
            components.add(Component(id, line))
            ++id
        }
    }

    fun debug() {
        for (comp in components) {
            println(comp)
        }
    }

    fun strength(bridge: List<Component>): Int {
        var total = 0
        for (comp in bridge) {
            total += comp.left
            total += comp.right
        }
        return total
    }

    private fun findBridge(bridge: List<Component>, rest: List<Component>) {
        val currStrength = strength(bridge)
        val currLength = bridge.size
        if (currLength >= longest && currStrength > strongest) {
            longest = currLength
            strongest = currStrength
        }
        //
//        println(bridge)
//        println(rest)
//        println("Press ENTER...")
//        readLine()
        //
        if (rest.isEmpty()) {
            return    // stop recursion
        }

        if (bridge.isEmpty()) {
            for (comp in rest) {
                val found = comp.find(0)
                if (found != NOT_FOUND) {
                    if (found == RIGHT) {
                        comp.swap()
                    }
                    findBridge(listOf(comp), rest.filter { it.id != comp.id })
                }
            }
        }
        else {    // the bridge is not empty
            val lastComp = bridge.last()
            for (comp in rest) {
                val found = comp.find(lastComp.right)
                if (found != NOT_FOUND) {
                    if (found == RIGHT) {
                        comp.swap()
                    }
                    findBridge(bridge + comp, rest.filter { it.id != comp.id })
                }
            }
        }

    }

    fun start() {
        val bridge = mutableListOf<Component>()
        findBridge(bridge, components)
    }

}


fun main(args: Array<String>) {

    // example
//    val fname = "example.txt"

    // production
    val fname = "input.txt"

    BridgeMaker.readFile(fname)

    BridgeMaker.start()

    println(BridgeMaker.strongest)

}

//fun deepCopy(li: List<Component>) =
//    li.map { it.copy() }.toMutableList()
