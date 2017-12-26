package b

import java.io.File

object Groups {

    /*
    A feltárt csoportokat egy listában tároljuk.
    A lista elemei halmazok, ezek a halmazok az egyes csoportok.
     */
    private val groups = mutableListOf<Set<Int>>()

    /*
    Ha a start pl. 0, akkor elindul a 0-tól, s feltárja, hogy
    a linkeket követve mely csúcsokhoz lehet eljutni.
    Az eredményt egy halmazban adja vissza.
     */
    fun findGroupOf(start: Int, map: Map<Int, List<Int>>): Set<Int> {
        val bag = mutableSetOf(start)    // init
        val li = mutableListOf(start)    // init

        var i = 0
        while (i < li.size) {
            val currValue = li[i]
            for (n in map[currValue]!!) {
                if (n !in bag) {
                    li.add(n)
                    bag.add(n)
                }
            }
            ++i
        }

        return bag.toSet()    // immutable
    }

    /*
    Hány db csoportot találtunk.
     */
    fun numberOfGroups() =
        this.groups.size

    /*
    Kapunk egy kulcsot, ami egy kezdőpozíció. Ha ez a kulcs
    már szerepel vmelyik feltárt coportban, akkor nem
    foglalkozunk vele.
    Ha még nem szerepel, akkor feltárjuk a hozzá tartozó csoportot.
     */
    fun exploreGroupOf(key: Int, map: Map<Int, List<Int>>) {
        if (this.isKeyInAnyGroup(key) == false) {
            val group = this.findGroupOf(key, map)
            this.groups.add(group)
        }
    }

    /*
    Az adott kulcs szerepel-e már vmelyik feltárt csoportban?
     */
    private fun isKeyInAnyGroup(key: Int): Boolean {
        for (group in this.groups) {
            if (key in group) {
                return true
            }
        }
        return false
    }

}

fun main(args: Array<String>) {

//    val fname = "example.txt"
    val fname = "input.txt"

    val map = readFile(fname)

    /*
    Megyünk végig a kulcsokon, s feltárjuk (és letároljuk)
    a csoportokat.
     */
    for (key in map.keys) {
        Groups.exploreGroupOf(key, map)
    }

    println(Groups.numberOfGroups())

}

fun readFile(fname: String): Map<Int, List<Int>> {

    val map = mutableMapOf<Int, List<Int>>()

    File(fname).forEachLine { line ->
        val parts = line.split("<->")
        val key = parts[0].trim().toInt()
        val value = parts[1].split(",").map { it.trim().toInt() }
        map[key] = value
    }

    return map

}
