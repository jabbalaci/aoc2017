package b_slow

fun main(args: Array<String>) {

    // example
//    val input = 3
//    val reps = 2017

    // production
    val input = 301
    val reps = 50_000_000

    val li = mutableListOf(0)    // init
    var currPos = 0

    for (i in 1..reps) {
        // start: step forward i positions
        repeat(input) {
            ++currPos
            if (currPos > li.lastIndex) {
                currPos = 0
            }
        }
        // end: step forward i positions
        ++currPos
        li.add(currPos, i)

        if (i % 1_000_000 == 0) {
            println("# %d".format(i / 1_000_000))
        }
    }

    println(li)
    val pos0 = li.indexOf(0)
    println("Value after 0: %d".format(li[pos0+1]))


}