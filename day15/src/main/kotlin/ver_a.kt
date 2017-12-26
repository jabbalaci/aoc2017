package a

fun main(args: Array<String>) {

    // fix part
    val (factorA, factorB) = Pair(16807, 48271)
    val modulo = 2147483647
    val mask = 0xffffL

    // example
//    var (a, b) = Pair(65L, 8921L)
//    val rep = 5

    // production
    var (a, b) = Pair(512L, 191L)
    val rep = 40_000_000


    var cnt = 0
    for (i in 1..rep) {
        if (i % 1_000_000 == 0) {
            println("# %d".format(i))
        }
        a = a * factorA % modulo
        b = b * factorB % modulo
//        println("a: %d\t\t\tb: %d".format(a, b))
        if ((a and mask) == (b and mask)) {
//            println("match at %d".format(i))
            ++cnt
        }
    }
    println(cnt)

}