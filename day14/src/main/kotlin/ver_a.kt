package a

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

class KnotHash(val text: String) {

    private val li = (0..255).toMutableList()
    private var input = this.convert(text).toMutableList()
    private var currPos = 0
    private var skip = 0

    init {
        repeat(64) {
            round()
        }
    }

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

    private fun convert(s: String) : List<Int> {
        val postfix = listOf(17, 31, 73, 47, 23)
        val res = s.map { it.toInt() }.toMutableList()
        res.addAll(postfix)
        return res
    }

}

object Binary {

    fun toBinaryString(hash: String): String {
        val sb = StringBuilder()

        for (c in hash) {
            val decimal = Integer.parseInt(c.toString(), 16)
            val binary = Integer.toBinaryString(decimal).padStart(4, '0')
//            println("%d: %s".format(decimal, binary))
            sb.append(binary)
        }

        return sb.toString()
    }

}

fun main(args: Array<String>) {

    // example
//    val input = "flqrgnkx"

    // production
    val input = "uugsqrei"

    val hashes = generateHashes(input, 0, 127)
    var total = 0
    for (hash in hashes) {
        val binString = Binary.toBinaryString(hash)
        total += binString.count { it == '1' }
    }
    println(total)

//    val hash = "a0c2017"
//    val binString = Binary.toBinaryString(hash)
//    println(hash)
//    println(binString)

}

fun generateHashes(base: String, lo: Int, hi: Int): List<String> {
    val res = mutableListOf<String>()

    for (i in lo..hi) {
        val text = "%s-%d".format(base, i)
//        println("hashing %s".format(text))
        res.add(KnotHash(text).hash())
//        println(res.last())
    }

    return res
}
