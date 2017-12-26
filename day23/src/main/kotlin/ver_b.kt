package b

/*
This exercise was not clear so I used some help here:

+ https://www.reddit.com/r/adventofcode/comments/7lo2ed/can_someone_explain_day_23_part_2/
+ https://github.com/dp1/AoC17/blob/master/day23.5.txt

Thus, the pseudo-code is the following:

    b = 105_700
    c = 122_700
    h = 0

    for n in range(b, c+1, 17):
        if not is_prime(n):
            h += 1

    print(h)

 */

fun isPrime(n: Int): Boolean {
    if (n < 2) {
        return false
    }
    if (n == 2) {
        return true
    }
    if (n % 2 == 0) {
        return false
    }

    var i = 3
    val maxi = Math.sqrt(n.toDouble()) + 1
    while (i <= maxi) {
        if (n % i == 0) {
            return false
        }
        i += 2
    }

    return true
}

fun main(args: Array<String>) {

    val b = 105_700
    val c = 122_700
    var h = 0

    for (n in b..c step 17) {
        if (isPrime(n) == false) {
            ++h
        }
    }

    println(h)

}