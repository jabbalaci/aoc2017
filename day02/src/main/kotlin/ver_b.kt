package b

import java.io.File

fun main(args: Array<String>) {

    var total = 0
    File("input.txt").forEachLine { line ->
        val nums = line.split(Regex("\\s+")).map { it.toInt() }.sortedDescending()
        loop@
        for (i in 0..(nums.lastIndex-1)) {
            for (j in i+1..nums.lastIndex) {
                val a = nums[i]
                val b = nums[j]
                if (a % b == 0) {
                    total += (a / b)
                    break@loop
                }
            }
        }
//        println(nums)
    }
    println(total)

}