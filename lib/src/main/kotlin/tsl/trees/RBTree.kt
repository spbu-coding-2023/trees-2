package tsl.trees

import tsl.nodes.RBNode
import javax.crypto.KEM

class RBTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, RBNode<K, V>>() {

    override fun insert(
        key: K,
        value: V,
    ) {

        val newNode = RBNode(key, value)
        // check if the tree is empty
        if (root == null) {
            root = RBNode(key, value)
            return balanceNode(root)
        }

        var currentNode: RBNode<K, V>? = root

        // traverse the tree to find the insertion point
        while (currentNode != null) {
            if (currentNode.key > newNode.key) {
                if (currentNode.leftChild == null) {
                    currentNode.leftChild = newNode
                    newNode.parent = currentNode
                    return balanceNode(newNode)
                }
                currentNode = currentNode.leftChild
            }
            else {
                if (currentNode.rightChild == null) {

                    currentNode.rightChild = newNode
                    newNode.parent = currentNode
                    return balanceNode(newNode)
                }
                currentNode = currentNode.rightChild
            }
        }
        return balanceNode(newNode)
    }

    private fun balanceNode(node: RBNode<K, V>?) {
        var newNode = node
        var uncle: RBNode<K, V>?
        while (newNode?.parent?.color == RBNode.Color.Red) {
            if (newNode.parent == newNode.parent?.parent?.leftChild) {
                uncle = newNode.parent?.parent?.rightChild

                when {
                    uncle?.color == RBNode.Color.Red -> {
                        newNode.parent?.color = RBNode.Color.Black
                        uncle.color = RBNode.Color.Black
                        newNode.parent?.parent?.color = RBNode.Color.Red
                        newNode = newNode.parent?.parent
                    }

                    newNode == newNode.parent?.rightChild -> {
                        newNode = newNode.parent
                        if (newNode?.parent?.parent == null) root = newNode?.parent
                        newNode?.rotateLeft()
                    }

                    newNode == newNode.parent?.leftChild -> {
                        if (newNode.parent?.parent?.parent == null) root = newNode.parent
                        newNode.parent?.parent?.rotateRight()
                    }
                }
            }
            else {
                uncle = newNode.parent?.parent?.leftChild

                when {
                    uncle?.color == RBNode.Color.Red -> {
                        newNode.parent?.color = RBNode.Color.Black
                        uncle.color = RBNode.Color.Black
                        newNode.parent?.parent?.color = RBNode.Color.Red
                        newNode = newNode.parent?.parent
                    }

                    newNode == newNode.parent?.leftChild -> {
                        newNode = newNode.parent
                        if (newNode?.parent?.parent == null) root = newNode?.parent
                        newNode?.rotateRight()
                    }

                    newNode == newNode.parent?.rightChild -> {
                        if (newNode.parent?.parent?.parent == null) root = newNode.parent
                        newNode.parent?.parent?.rotateLeft()
                    }
                }
            }
        }
        root?.color = RBNode.Color.Black
    }

    override fun delete(key: K) {

    }

    fun printTree() {
        printTreeRecursively(root, "", true)
    }

    private fun printTreeRecursively(node: RBNode<K, V>?, prefix: String, isTail: Boolean) {
        if (node != null) {
            printTreeRecursively(node.rightChild, "$prefix${if (isTail) "│   " else "    "}", false)
            println("$prefix${if (isTail) "└── " else "┌── "}${if (node.color == RBNode.Color.Red) "\u001b[31m" else "\u001b[30;1m"}(${node.key},${node.value})\u001b[0m")
            printTreeRecursively(node.leftChild, "$prefix${if (isTail) "    " else "│   "}", true)
        }
    }
}
