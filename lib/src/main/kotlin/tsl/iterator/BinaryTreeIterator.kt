package tsl.iterator

import tsl.nodes.AbstractNode
import tsl.trees.AbstractBinaryTree

internal class BinaryTreeIterator<K : Comparable<K>, V, N: AbstractNode<K, V, N>>
    (tree: AbstractBinaryTree<K, V, N>): Iterator<Pair<K, V>>  {

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun next(): Pair<K, V> {
        TODO("Not yet implemented")
    }
}