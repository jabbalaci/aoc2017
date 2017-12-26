package a

class KnotHash(var li: MutableList<Int>, val input: List<Int>) {

    private var currPos = 0
    private var skip = 0

    fun start() {
        for (n in input) {
            val subList = getSubList(currPos, n)
            val revSubList = subList.reversed()
//            println(subList)
//            println(revSubList)
            writeBack(currPos, revSubList)
//            println(li)
            currPos = moveForward(n + skip)
            ++skip
//            println("New curr. pos.: %d".format(currPos))
//            break
//            println("--------------")
        }
    }

    private fun moveForward(steps: Int): Int {
        var i = currPos
        repeat(steps) {
            ++i
            if (i > li.lastIndex) {
                i = 0
            }
        }
        return i
    }

    private fun getSubList(currPos: Int, length: Int):  List<Int> {
        val res = mutableListOf<Int>()

        var i = currPos
        repeat(length) {
            res.add(li[i])
            ++i
            if (i > li.lastIndex) {
                i = 0
            }
        }

        return res
    }

    private fun writeBack(currPos: Int, subList: List<Int>) {
        var i = currPos
        for (e in subList) {
            li[i] = e
            ++i
            if (i > li.lastIndex) {
                i = 0
            }
        }
    }

    override fun toString() : String {
        return this.li.joinToString(", ")
    }

    operator fun get(i: Int) =
        li[i]

    fun size() =
        li.size

}

fun main(args: Array<String>) {

    // example
//    var li = (0..4).toMutableList()
//    val input = listOf(3, 4, 1, 5)

    // production
    var li = (0..255).toMutableList()
    val input = listOf(157,222,1,2,177,254,0,228,159,140,249,187,255,51,76,30)

    val kh = KnotHash(li, input)
    kh.start()

    println(kh)
//    println(kh.size())

    println("Result: %d".format(kh[0] * kh[1]))
}