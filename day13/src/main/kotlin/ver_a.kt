package a

import java.io.File

val UP = -1
val DOWN = 1

data class Layer(val id: Int, val depth: Int) {

    private var scannerPos = 0    // where is the scanner?
    private var direction = DOWN    // direction of the next move of the scanner

    // if depth = 0, then this layer doesn't exist => empty layer,
    // it has no scanner
    fun hasScanner() =
        this.depth > 0

    fun getSeverity() =
        id * depth

    fun hasCaughtMe(): Boolean {
        if (hasScanner() == false) {
            return false
        }
        return scannerPos == 0
    }

    fun moveScanner() {
        if (atTop()) {
            direction = DOWN
        }
        if (atBottom()) {
            direction = UP
        }

        scannerPos += direction
    }

    private fun atTop() =
        scannerPos == 0

    private fun atBottom() =
        scannerPos == depth - 1

}

object Firewall {

    private val map = mutableMapOf<Int, Int>()
    private val layers = mutableListOf<Layer>()
    private var myPos = 0    // Where am I, at which layer?
    private var severity = 0

    fun readFile(fname: String) {
        File(fname).forEachLine { line ->
            val parts = line.split(":")
            val key = parts[0].trim().toInt()
            val value = parts[1].trim().toInt()
            this.map[key] = value
        }
    }

    // construct the firewall, i.e. build the layers
    fun build() {
        for (id in 0..getIdOfLastLayer()) {
            this.layers.add(Layer(id, this.map.getOrDefault(id, 0)))
        }
    }

    fun startMoving() {
        if (caught()) {
            println("You got caught at layer %d".format(myPos))
            severity += layers[myPos].getSeverity()
            println("New severity: %d".format(severity))
        }
        val lastLayer = getIdOfLastLayer()
        while (true) {
            moveScanners()
            ++myPos
            if (myPos > lastLayer) {
                break
            }

            if (caught()) {
                println("You got caught at layer %d".format(myPos))
                severity += layers[myPos].getSeverity()
                println("New severity: %d".format(severity))
            }

        }
    }

    private fun moveScanners() {
        for (layer in this.layers) {
            layer.moveScanner()
        }
    }

    private fun caught(): Boolean {
        val layer = layers[myPos]
        return layer.hasCaughtMe()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (layer in this.layers) {
            sb.append(layer.toString())
            sb.append("\n")
        }
        return sb.toString()
    }

    // get the ID of the last layer (this ID is the max of the IDs)
    private fun getIdOfLastLayer(): Int {
        return this.map.keys.max()!!
    }

}

fun main(args: Array<String>) {

//    val fname = "example.txt"
    val fname = "input.txt"

    Firewall.readFile(fname)
    Firewall.build()
    Firewall.startMoving()

//    println(Firewall)

}