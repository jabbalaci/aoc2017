package b2

import java.io.File

val UP = -1
val DOWN = 1


data class Layer(val id: Int, val depth: Int) {

    private var scannerPos = 0      // Where is the scanner?
    private var direction = DOWN    // direction of the next move of the scanner

    // if depth = 0, then this layer has no scanner
    private fun hasScanner() =
        this.depth > 0

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

    fun changeDirection() {
        direction *= -1
    }

}


object Firewall {

    private val map = mutableMapOf<Int, Int>()
    private val layers = mutableListOf<Layer>()
    private var myPos = 0    // Where am I, at which layer?

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
        for (id in 0..getLastLayerId()) {
            this.layers.add(Layer(id, this.map.getOrDefault(id, 0)))
        }
    }

    private fun rewind() {
        for (layer in this.layers) {
            layer.changeDirection()
        }
        repeat(myPos) {
            moveScanners()
            --myPos
        }
        for (layer in this.layers) {
            layer.changeDirection()
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

    fun tryToGoThrough() {

        var waitTime = 0
        val lastLayerId = getLastLayerId()

        while (true) {
            if (caught()) {
                rewind()
                moveScanners()
                ++waitTime
//                println("Got caught. New wait time: %d".format(waitTime))
                continue
            }

            moveScanners()
            ++myPos
            if (myPos > lastLayerId) {
                break
            }
        }

        println(waitTime)

    }

    // get the ID of the last layer (this ID is the max of the IDs)
    private fun getLastLayerId() =
        this.map.keys.max()!!

}


fun main(args: Array<String>) {

//    val fname = "example.txt"
    val fname = "input.txt"

    Firewall.readFile(fname)
    Firewall.build()
    Firewall.tryToGoThrough()

}