package a

enum class States {
    A, B, C, D, E, F
}

data class Node(var data: Int = 0, var prev: Node? = null, var next: Node? = null) {

    fun isFirst() =
        prev == null

    fun isLast() =
        next == null

}

class MyList {

    var first: Node? = null
    var last: Node? = null
    var size : Int    // for curiosity
    var curr: Node? = null

    init {
        val node = Node()
        first = node
        last = node
        size = 1
        curr = node
    }

    fun moveLeft() {
        if (curr!!.isFirst()) {
            val newNode = Node()
            ++size
            newNode.next = curr
            curr!!.prev = newNode
            first = newNode
        }
        curr = curr!!.prev
    }

    fun moveRight() {
        if (curr!!.isLast()) {
            val newNode = Node()
            ++size
            newNode.prev = curr
            curr!!.next = newNode
            last = newNode
        }
        curr = curr!!.next
    }

}

class Turing(var state: States, val steps: Int) {

    private val li = MyList()

    override fun toString(): String {
        val sb = StringBuilder()

        var node = li.first
        while (node != null) {
            sb.append(node.data)
            node = node.next
        }

        return sb.toString()
    }

    fun example() {
        repeat(steps) {
            when (state) {
                States.A -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 1
                            li.moveRight()
                            state = States.B
                        }
                        1 -> {
                            li.curr!!.data = 0
                            li.moveLeft()
                            state = States.B
                        }
                    }
                }
                States.B -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 1
                            li.moveLeft()
                            state = States.A
                        }
                        1 -> {
                            li.curr!!.data = 1
                            li.moveRight()
                            state = States.A
                        }
                    }
                }
            }
        }
    }

    fun production() {
        var cnt = 0
        repeat(steps) {
            ++cnt
            if (cnt % 1_000_000 == 0) {
                println("# %d".format(cnt / 1_000_000))
            }
            //
            when (state) {
                States.A -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 1
                            li.moveRight()
                            state = States.B
                        }
                        1 -> {
                            li.curr!!.data = 0
                            li.moveLeft()
                            state = States.D
                        }
                    }
                }
                States.B -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 1
                            li.moveRight()
                            state = States.C
                        }
                        1 -> {
                            li.curr!!.data = 0
                            li.moveRight()
                            state = States.F
                        }
                    }
                }
                States.C -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 1
                            li.moveLeft()
                            state = States.C
                        }
                        1 -> {
                            li.curr!!.data = 1
                            li.moveLeft()
                            state = States.A
                        }
                    }
                }
                States.D -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 0
                            li.moveLeft()
                            state = States.E
                        }
                        1 -> {
                            li.curr!!.data = 1
                            li.moveRight()
                            state = States.A
                        }
                    }
                }
                States.E -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 1
                            li.moveLeft()
                            state = States.A
                        }
                        1 -> {
                            li.curr!!.data = 0
                            li.moveRight()
                            state = States.B
                        }
                    }
                }
                States.F -> {
                    when (li.curr!!.data) {
                        0 -> {
                            li.curr!!.data = 0
                            li.moveRight()
                            state = States.C
                        }
                        1 -> {
                            li.curr!!.data = 0
                            li.moveRight()
                            state = States.E
                        }
                    }
                }
            }
        }
    }

    fun checksum(): Int {
        var total = 0

        var node = li.first
        while (node != null) {
            if (node.data == 1) {
                ++total
            }
            node = node.next
        }

        return total
    }

    fun size() =
        li.size

}

fun main(args: Array<String>) {

    // example
//    val turing = Turing(States.A, 6)
//    turing.example()

    // production
    val turing = Turing(States.A, 12_317_297)
    turing.production()


//    println(turing)
    println("Size of the tape: ${turing.size()}")
    println("Checksum (Part One): %d".format(turing.checksum()))

}