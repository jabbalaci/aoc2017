package b

object Spiral {

    private val map = mutableMapOf<Pair<Int, Int>, Int>()

    fun put(key: Pair<Int, Int>, value: Int) {
        this.map[key] = value
    }

    fun get(key: Pair<Int, Int>) : Int {
        return this.map.getOrDefault(key, 0)
    }

    fun calculateValue(x: Int, y: Int): Int {
        var total = 0

        total += this.get(Pair(x+1, y))
        total += this.get(Pair(x+1, y+1))
        total += this.get(Pair(x, y+1))
        total += this.get(Pair(x-1, y+1))
        total += this.get(Pair(x-1, y))
        total += this.get(Pair(x-1, y-1))
        total += this.get(Pair(x, y-1))
        total += this.get(Pair(x+1, y-1))

        return total
    }

    fun size() =
        this.map.size

}

fun main(args: Array<String>) {

    val goal = 312051
    var steps = 0
    var currValue = 1
    var x = 0
    var y = 0

    Spiral.put(Pair(0, 0), 1)

    loop@
    while (true) {
        if (currValue > goal) break@loop

        ++steps
        // right
        for (i in 1..steps) {
            ++x
            currValue = Spiral.calculateValue(x, y)
            Spiral.put(Pair(x, y), currValue)
            if (currValue > goal) break@loop
        }

        // up
        for (i in 1..steps) {
            ++y
            currValue = Spiral.calculateValue(x, y)
            Spiral.put(Pair(x, y), currValue)
            if (currValue > goal) break@loop
        }

        ++steps
        // left
        for (i in 1..steps) {
            --x
            currValue = Spiral.calculateValue(x, y)
            Spiral.put(Pair(x, y), currValue)
            if (currValue > goal) break@loop
        }

        // down
        for (i in 1..steps) {
            --y
            currValue = Spiral.calculateValue(x, y)
            Spiral.put(Pair(x, y), currValue)
            if (currValue > goal) break@loop
        }

    }

    println(currValue)
    println("# map's size: %d".format(Spiral.size()))

}