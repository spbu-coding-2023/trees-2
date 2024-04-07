package tsl.nodes

abstract class AbstractNode<K : Comparable<K>, V, N : AbstractNode<K, V, N>>(
    var key: K,
    var value: V
) {
    internal var leftChild: N? = null
    internal var rightChild: N? = null

    internal fun hasTwoChildren(): Boolean =
        (this.leftChild != null && this.rightChild != null)
}
