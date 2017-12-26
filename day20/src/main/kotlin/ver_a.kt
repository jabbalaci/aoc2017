package a

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
    private val position = points[0]
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
        sb.append(", ")
        sb.append("v=")
        sb.append(velocity.toString())
        sb.append(", ")
        sb.append("a=")
        sb.append(acceleration.toString())
        sb.append(", ")
        sb.append("distance=")
        sb.append(distanceFromZero())

        return sb.toString()
    }

    fun distanceFromZero(): BigInteger {
        return abs(position.x) + abs(position.y) + abs(position.z)
    }

    fun abs(n: BigInteger) =
        if (n >= BigInteger.ZERO) n else -n

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
        val limit = 10000

        while (true) {
//            println(this)
            println("closest id: %d".format(findClosestParticleId()))
//            println("-------------------------------------")

            for (p in particles) {
                p.move()
            }

            ++cnt
            if (cnt > limit) {
                break
            }
        }


    }

    private fun findClosestParticleId(): Int {
        var mini = particles[0].distanceFromZero()
        var id = 0

        for (i in 1..particles.lastIndex) {
            val dist = particles[i].distanceFromZero()
            if (dist < mini) {
                mini = dist
                id = i
            }
        }

        return id
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
//    val fname = "example1.txt"

    // production
    val fname = "input.txt"

    Space.readInput(fname)
//    println(Space)
    Space.start()

}

