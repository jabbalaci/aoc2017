package b

fun main(args: Array<String>) {

    // fix part
    val (factorA, factorB) = Pair(16807, 48271)
    val modulo = 2147483647
    val mask = 0xffffL

    // example
//    var (a, b) = Pair(65L, 8921L)
//    val rep = 5_000_000

    // production
    var (a, b) = Pair(512L, 191L)
    val rep = 5_000_000


    var cnt = 0
    for (i in 1..rep) {
        if (i % 100_000 == 0) {
            println("# %d".format(i))
        }
        while (true) {
            a = a * factorA % modulo
            if (a % 4L == 0L) {
                break
            }
        }
        while (true) {
            b = b * factorB % modulo
            if (b % 8L == 0L) {
                break
            }
        }
//        println("a: %d\t\t\tb: %d".format(a, b))
        if ((a and mask) == (b and mask)) {
//            println("match at %d".format(i))
            ++cnt
        }
    }
    println(cnt)

}