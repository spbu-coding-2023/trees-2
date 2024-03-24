package tsl.nodes

class RBNode<K : Comparable<K>, V>(key: K, value: V) : AbstractNode<K, V, RBNode<K, V>>(key, value) {
    internal var parent: RBNode<K, V>? = null

    enum class Color {
        Black,
        Red,
    }

    internal var color = Color.Red

    internal fun switchColor(node2: RBNode<K, V>?) {
        val node1color = this.color

        if (node2 != null) {
            this.color = node2.color
            node2.color = node1color
        }
    }
}

