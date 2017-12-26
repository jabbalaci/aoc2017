package a

object Bag {

    private val bag = mutableSetOf<String>()

    fun add(nums: List<Int>) {
        this.bag.add(this.convert(nums))
    }

    fun contains(nums: List<Int>): Boolean {
        return this.bag.contains(this.convert(nums))
    }

    private fun convert(nums: List<Int>): String {
        return nums.joinToString(",")
    }

}

fun main(args: Array<String>) {

//    val input = "0 2 7 0"
    val input = "4\t10\t4\t1\t8\t4\t9\t14\t5\t1\t14\t15\t0\t15\t3\t5"
    val nums = input.split(Regex("\\s+")).map { it.toInt() }.toMutableList()
    var steps = 0

    println(nums)

    Bag.add(nums)
    while (true) {
        var pos = nums.indexOf(nums.max())
        // start: redistribute
        val loops = nums[pos]
        nums[pos] = 0
        for (i in 1..loops) {
            ++pos
            if (pos > nums.lastIndex) pos = 0
            nums[pos] += 1
        }
        // end: redistribute
        ++steps
//        println(nums)
        if (Bag.contains(nums)) {
            break
        } else {
            Bag.add(nums)
        }
    }

    println("Steps: %d".format(steps))

}