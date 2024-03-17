package tsl.nodes

class RBNode<K : Comparable<K>, V>(key: K, value: V) : AbstractNode<K, V, RBNode<K, V>>(key, value) {
    internal var parent: RBNode<K, V>? = null

    enum class Color {
        Black,
        Red,
    }

    internal var color = Color.Black

    internal fun switchColor() {
        if (this.color == Color.Black) {
            this.color = Color.Red
        } else {
            this.color = Color.Black
        }
    }
}

