import java.io.File

object Registers {

    private val map = mutableMapOf<String, Int>()

    private var maxValue = 0

    fun get(name: String): Int {
        if (map.containsKey(name) == false) {
            map[name] = 0
        }
        return map[name]!!
    }

    fun inc(name: String, num: Int) {
        if (map.containsKey(name) == false) {
            map[name] = 0
        }
        val newValue = map[name]!! + num
        map[name] = newValue
        //
        if (newValue > this.maxValue) {
            this.maxValue = newValue
        }
    }

    fun dec(name: String, num: Int) {
        inc(name, -1 * num)
    }

    fun getMap(): Map<String, Int> {
        return this.map
    }

    fun getMaxValue() =
        this.maxValue

}

fun main(args: Array<String>) {

//    val fname = "example.txt"
    val fname = "input.txt"

    File(fname).forEachLine { line ->
        val parts = line.split(Regex("\\s+"))
//        println(parts)
        if (isTrue(parts[4], parts[5], parts[6])) {
            val register = parts[0]
            val num = parts[2].toInt()
            when(parts[1]) {
                "inc" -> Registers.inc(register, num)
                "dec" -> Registers.dec(register, num)
            }
        }
    }

//    for ((k ,v) in Registers.getMap().entries) {
//        println("%s: %d".format(k, v))
//    }

    println("Highest value (Part A): %d".format(Registers.getMap().values.max()))
    println("Highest value ever (Part B): %d".format(Registers.getMaxValue()))
}

fun isTrue(register: String, operator: String, value: String): Boolean {
    val registerValue = Registers.get(register)
    val num = value.toInt()
    return when(operator) {
        "==" -> registerValue == num
        "!=" -> registerValue != num
        "<"  -> registerValue < num
        "<="  -> registerValue <= num
        ">"  -> registerValue > num
        ">="  -> registerValue >= num
        else -> false    // shouldn't ever arrive here
    }
}
