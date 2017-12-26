package b_slow

import kotlin.coroutines.experimental.buildIterator

fun main(args: Array<String>) {

    // fix part
    val (factorA, factorB) = Pair(16807, 48271)
    val modulo = 2147483647
    val mask = 0xffffL

    // example
    var (a, b) = Pair(65L, 8921L)
    val rep = 5_000_000

    // production
//    var (a, b) = Pair(512L, 191L)
//    val rep = 5_000_000

    fun generateA() = buildIterator {
        while (true) {
            val res = a * factorA % modulo
            if (res % 4L == 0L) {
                yield(res)
            }
        }
    }

    fun generateB() = buildIterator {
        while (true) {
            val res = b * factorB % modulo
            if (res % 8L == 0L) {
                yield(res)
            }
        }
    }

    val genA = generateA()
    val genB = generateB()

    var cnt = 0
    for (i in 1..rep) {
        if (i % 1_000 == 0) {
            println("# %d".format(i))
        }
        a = genA.next()
        b = genB.next()
//        println("a: %d\t\t\tb: %d".format(a, b))
        if ((a and mask) == (b and mask)) {
//            println("match at %d".format(i))
            ++cnt
        }
    }
    println(cnt)

}