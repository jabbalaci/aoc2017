package b

class Xor(private val li: List<Int>) {

    private var pieces = mutableListOf<List<Int>>()

    init {
        var start = 0
        repeat(16) {
            pieces.add(li.subList(start, start+16))
            start += 16
        }

    }

    fun denseHash() : List<Int> {
        return this.pieces.map { xorTogether(it) }
    }

    private fun xorTogether(li: List<Int>): Int {
        var res = li[0]
        for (i in 1..li.lastIndex) {
            res = res xor li[i]
        }
        return res
    }

}

class KnotHash(private var li: MutableList<Int>, private val input: List<Int>) {

    private var currPos = 0
    private var skip = 0

    fun round() {
        for (n in input) {
            val subList = getSubList(currPos, n)
            val revSubList = subList.reversed()
            writeBack(currPos, revSubList)
            currPos = moveForward(n + skip)
            ++skip
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

    private fun toHex(n: Int): String {
        var res = n.toString(16)
        if (res.length == 1) {
            res = "0" + res
        }
        return res
    }

    fun hash(): String {
        val xor = Xor(this.li)
        return xor.denseHash().map { toHex(it) }.joinToString("")
    }

}

fun main(args: Array<String>) {

    // this part is fix
    var li = (0..255).toMutableList()

    // example
//    val input = convert("AoC 2017")

    // production
    val input = convert("157,222,1,2,177,254,0,228,159,140,249,187,255,51,76,30")

    val kh = KnotHash(li, input)
    repeat(64) {
        kh.round()
    }
//    println(kh)

    println(kh.hash())

}

fun convert(s: String) : List<Int> {
    val postfix = listOf(17, 31, 73, 47, 23)
    val res = s.map { it.toInt() }.toMutableList()
    res.addAll(postfix)
    return res
}