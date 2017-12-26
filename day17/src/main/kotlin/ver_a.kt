package a

fun main(args: Array<String>) {

    // example
//    val input = 3
//    val reps = 2017

    // production
    val input = 301
    val reps = 2017

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
    }

    println(li)
    println("Answer: %d".format(li[currPos+1]))


}