package b

import java.io.File

fun main(args: Array<String>) {

//    val input = "example.txt"
    val input = "input.txt"
    val li = File(input).readLines().map { it.toInt() }.toMutableList()

    var steps = 0
    var pos = 0
    var currValue : Int

    while (pos >= 0 && pos < li.size) {
        currValue = li[pos]
        if (currValue >= 3) {
            --li[pos]
        } else {
            ++li[pos]
        }
        pos += currValue
        ++steps
    }

//    println(li)
    println(steps)

}