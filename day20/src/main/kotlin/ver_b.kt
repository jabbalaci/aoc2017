package b

import java.math.BigInteger
import java.io.File

data class Point(var x: BigInteger, var y: BigInteger, var z: BigInteger)

data class Particle(val id: Int, val line: String) {

    private val points = mutableListOf<Point>()

    init {
        val REGEX = Regex("""<(.*?)>""")
        val matchedResults = REGEX.findAll(line)
        for (matchedText in matchedResults) {
            val parts = matchedText.value.replace("<", "").replace(">", "").split(",").map { it.trim().toInt().toBigInteger() }
            points.add(Point(parts[0], parts[1], parts[2]))
        }
    }

    // these MUST come after the init!
    val position = points[0]
    private val velocity = points[1]
    private val acceleration = points[2]

    fun move() {
        velocity.x += acceleration.x
        velocity.y += acceleration.y
        velocity.z += acceleration.z
        //
        position.x += velocity.x
        position.y += velocity.y
        position.z += velocity.z
    }

    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("p(${id})=")
        sb.append(position.toString())

        return sb.toString()
    }

}

object Space {

    private val particles = mutableListOf<Particle>()

    fun readInput(fname: String) {
        var id = 0
        File(fname).forEachLine { line ->
            particles.add(Particle(id, line))
            ++id
        }
    }

    fun start() {
        var cnt = 0
        val limit = 100

        while (true) {
//            println(this)
//            println("-------------------------------------")

            for (p in particles) {
                p.move()
            }
            removeCollisions()

            ++cnt
            if (cnt > limit) {
                break
            }
        }
    }

    private fun removeCollisions() {
        val map = mutableMapOf<Point, MutableList<Int>>()
        for (p in particles) {
            if (p.position !in map) {
                map[p.position] = mutableListOf()
            }
            map[p.position]!!.add(p.id)
        }
        //
        for (li in map.values) {
            if (li.size >= 2) {
                println("particles before: %d".format(particles.size))
                li.forEach { id ->
                    particles.removeIf { p ->
                        p.id == id
                    }
                }
                println("particles after: %d".format(particles.size))
                println("-----------------------------------")
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (p in particles) {
            sb.append(p.toString())
            sb.append("\n")
        }

        return sb.toString()
    }

}


fun main(args: Array<String>) {

    // example
//    val fname = "example2.txt"

    // production
    val fname = "input.txt"

    Space.readInput(fname)
//    println(Space)
    Space.start()

}

