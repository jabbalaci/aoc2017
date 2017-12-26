package b


data class Node(val data: Int, var next: Node? = null)

class MyList {

    var first : Node? = null
    var last : Node? = null

    fun append(n: Int) {
        val node = Node(n)
        if (first == null) {    // this is the very first element
            first = node
            last = node
        } else {    // it has at least one element
            last!!.next = node
            last = node
        }
    }

    fun find(n: Int): Node? {
        var curr = first
        while (curr != null) {
            if (curr.data == n) {
                return curr
            }
            curr = curr.next
        }
        return null
    }

    override fun toString(): String {
        val sb = StringBuilder("[")
        var curr = first
        var first = true
        while (curr != null) {
            if (first == false) {
                sb.append(", ")
            }
            sb.append(curr.data)
            first = false
            curr = curr.next
        }
        sb.append("]")
        return sb.toString()
    }

}

fun main(args: Array<String>) {

    // example
//    val input = 3
//    val reps = 2017

    // production
    val input = 301
    val reps = 50_000_000

    val li = MyList()
    li.append(0)    // init with this value
    var curr = li.first

    for (i in 1..reps) {
        // start: step forward i positions
        repeat(input) {
            curr = curr!!.next
            if (curr == null) {
                curr = li.first
            }
        }
        // end: step forward i positions

        if (curr!!.next == null) {
            li.append(i)
        } else {
            val node = Node(i)
            node.next = curr!!.next
            curr!!.next = node
        }
        curr = curr!!.next

        if (i % 1_000_000 == 0) {
            println("# %d".format(i / 1_000_000))
        }
    }

//    println(li)

    val node0 = li.find(0)
    println("Value after 0: %d".format(node0?.next?.data))

}