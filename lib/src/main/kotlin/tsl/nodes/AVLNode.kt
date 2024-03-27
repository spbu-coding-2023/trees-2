package tsl.nodes

class AVLNode<K : Comparable<K>, V>(key: K, value: V) :
    AbstractNode<K, V, AVLNode<K, V>>(key, value) {
    internal var height = 0
}
