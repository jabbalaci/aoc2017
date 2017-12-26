package b

import java.io.File

fun main(args: Array<String>) {

    var valid = 0

    File("input.txt").forEachLine { line ->
        val words = line.split(Regex("\\s+"))
        var good = true
        loop@
        for (i in 0..words.lastIndex-1) {
            for (j in i+1..words.lastIndex) {
                if (anagrams(words[i], words[j])) {
                    good = false
                    break@loop
                }
            }
        }
        if (good) {
            ++valid
        }
    }

    println(valid)

}

fun anagrams(s1: String, s2: String): Boolean {
    if (s1.length == s2.length) {
        if (s1.toList().sorted() == s2.toList().sorted()) {
            return true
        }
    }
    return false
}
