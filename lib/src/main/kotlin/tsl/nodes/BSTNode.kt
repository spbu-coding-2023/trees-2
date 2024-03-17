package tsl.nodes

class BSTNode<K : Comparable<K>, V>(key: K, value: V) : AbstractNode<K, V, BSTNode<K, V>>(key, value)

