package b

import java.io.File

object Tree {

    private val nodes = mutableMapOf<String, Node>()

    fun add(node: Node) {
        this.nodes[node.name] = node
    }

    fun getNodeByName(name: String): Node? {
        return this.nodes.getOrDefault(name, null)
    }

    fun getAllNodes(): List<Node> {
        return this.nodes.values.toList()
    }

    fun setParents() {
        for (parent in getAllNodes()) {
            for (child in parent.getChildrenAsNodes()) {
                child.parent = parent
            }
        }
    }

}

data class Node(val name: String, val weight: Int) {

    var parent : Node? = null

    val children = mutableListOf<String>()

    var totalWeight = weight

    fun addChild(child: String) {
        this.children.add(child)
    }

    fun hasChildren(): Boolean {
        return this.children.size > 0
    }

    fun getChildrenAsNodes(): List<Node> {
        val li = mutableListOf<Node>()
        for (name in children) {
            li.add(Tree.getNodeByName(name)!!)
        }
        return li
    }

    override fun toString() : String {
        val sb = StringBuilder()
        sb.append("%s (%d) -> ".format(this.name, this.totalWeight))
        var cnt = 0
        for (ch in this.children) {
            val node = Tree.getNodeByName(ch)!!
            if (cnt > 0) {
                sb.append(", ")
            }
            sb.append("%s (%d)".format(node.name, node.totalWeight))
            ++cnt
        }
        return sb.toString()
    }

}

fun main(args: Array<String>) {

//    val fname = "example.txt"
//    val fname = "input.txt"

    File(fname).forEachLine { line ->
        val parts = line.split(Regex(" -> "))

        if (parts.size == 1) {
            val (name, weight) = extractNameAndWeight(parts[0])
            Tree.add(Node(name, weight))
        }
        else {
            val left = parts[0]
            val (name, weight) = extractNameAndWeight(left)
            val parent = Node(name, weight)
            Tree.add(parent)

            val right = parts[1]
            val pieces = right.split(", ")
            pieces.forEach { prgName ->
                parent.addChild(prgName)
            }
        }
    }
    Tree.setParents()

    for (node in Tree.getAllNodes()) {
        val weight = node.weight
        var curr = node.parent
        while (curr != null) {
            curr.totalWeight += weight
            curr = curr.parent
        }
    }

    for (node in Tree.getAllNodes()) {
        if (node.hasChildren()) {
            val bag = node.getChildrenAsNodes().map { it.totalWeight }.toSet()
            if (bag.size > 1) {
                println(node)
            }
        }
    }

    println(Tree.getNodeByName("gozhrsf")?.weight)

    /*
    output:
    exrud (7086) -> hpziqqg (1381), abxglwt (1381), gozhrsf (1386), oqjqu (1381), vhkodl (1381)
    rbzmniw (74207) -> tespoy (7081), tirifqs (7081), exrud (7086)
    eqgvf (445226) -> wdbmakv (74202), zqamk (74202), hvecp (74202), rbzmniw (74207), jfofam (74202), tigvdj (74202)
    762

    gozhrsf 's total weight is 1386. If its total weight would be 1381, then the tree would be balanced.
    That is, it should be lighter with 5 units.
    gozhrsf 's own weight is 762, thus the answer is: 762 - 5 = 757
     */

}

fun extractNameAndWeight(s: String): Pair<String, Int> {
    val pieces = s.split(Regex("\\s+"))
    val name = pieces[0]
    val weight = pieces[1].substring(1, pieces[1].lastIndex).toInt()

    return Pair(name, weight)
}
