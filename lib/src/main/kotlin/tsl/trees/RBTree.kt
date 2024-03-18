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
        val deleteNode: RBNode<K, V>? = searchNodeF(key) // track node that will replace other one
        var colorOfTransferingNode = deleteNode?.color
        val childNode: RBNode<K, V>?
        if (getChildrenCount(deleteNode) < 2) {
            childNode = if (deleteNode?.leftChild != null) deleteNode.leftChild else deleteNode?.rightChild
            transplantTwoNodes(deleteNode, childNode)
        }
        else {
            val minNode = getMin(deleteNode?.rightChild)
            if (minNode != null) {
                deleteNode?.key = minNode.key
                deleteNode?.value = minNode.value
                colorOfTransferingNode = minNode.color
            }
            childNode = if (minNode?.leftChild != null) minNode.leftChild else minNode?.rightChild
            transplantTwoNodes(minNode, childNode)
        }
        if (colorOfTransferingNode == RBNode.Color.Black) fixAfterDelete(childNode)
        // TODO: return values
        return
    }

    private fun fixAfterDelete(node: RBNode<K, V>?) {
        var fixNode: RBNode<K, V>? = node
        var fixNodeBrother: RBNode<K, V>?
        while (fixNode != root && fixNode?.color == RBNode.Color.Black) {
            if (fixNode == fixNode.parent?.leftChild) {
                val parent = fixNode.parent
                fixNodeBrother = parent?.rightChild
                if (fixNodeBrother?.leftChild?.color == RBNode.Color.Black && fixNodeBrother.rightChild?.color == RBNode.Color.Black) {
                    fixNodeBrother.color = RBNode.Color.Red
                    fixNode = fixNode.parent
                }
                else {
                    if (fixNodeBrother?.rightChild?.color == RBNode.Color.Black) {
                        fixNodeBrother.leftChild?.color = RBNode.Color.Black
                        fixNodeBrother.color = RBNode.Color.Red
                        fixNodeBrother.rotateRight()
                        fixNodeBrother = fixNode.parent?.rightChild
                    }
                    if (fixNodeBrother != null && fixNode.parent != null) {
                        fixNode.parent?.color.also { if (it != null) fixNodeBrother?.color = it }
                        // omg do not touch TODO: good coomment
                    }
                    fixNode.parent?.color = RBNode.Color.Black
                    fixNodeBrother?.rightChild?.color = RBNode.Color.Black
                    fixNode.parent?.rotateLeft()
                    fixNode = root
                }
            }
            else {
                fixNodeBrother = fixNode.parent?.leftChild
                if(fixNodeBrother?.color == RBNode.Color.Red) {
                    fixNodeBrother.color = RBNode.Color.Black
                    fixNode.parent?.color = RBNode.Color.Red
                    fixNode.parent?.rotateRight()
                    fixNodeBrother = fixNode.parent?.leftChild
                }
                if(fixNodeBrother?.leftChild?.color == RBNode.Color.Black && fixNodeBrother.rightChild?.color == RBNode.Color.Black) {
                    fixNode.parent?.rotateRight()
                    fixNodeBrother = fixNode.parent?.leftChild
                }
                else {
                    if(fixNodeBrother?.leftChild?.color == RBNode.Color.Black) {
                        fixNodeBrother.rightChild?.color = RBNode.Color.Black
                        fixNodeBrother.color = RBNode.Color.Red
                        fixNodeBrother.rotateLeft()
                        fixNodeBrother = fixNode.parent?.leftChild
                    }
                    fixNode.parent?.color.also { if (it != null) fixNodeBrother?.color = it }
                    // TODO: good coomment
                    fixNode.parent?.color = RBNode.Color.Black
                    fixNodeBrother?. leftChild?.color = RBNode.Color.Black
                    fixNode.parent?.rotateRight()
                    fixNode = root
                }
            }
        }
        fixNode?.color = RBNode.Color.Black
    }

    private fun searchNodeF(key: K): RBNode<K, V>? {
        var currentNode = root
        while (currentNode != null) {
            if (key == currentNode.key) {
                return currentNode
            }
            currentNode = if (key < currentNode.key) currentNode.leftChild else currentNode.rightChild
        }
        return null
    }

    private fun transplantTwoNodes(firstNode: RBNode<K, V>? , secondNode: RBNode<K, V>?) {
        if (firstNode == root) root = secondNode
        else if (firstNode?.parent?.leftChild == firstNode) firstNode?.parent?.leftChild = secondNode
        else firstNode?.parent?.rightChild = secondNode
        secondNode?.parent = firstNode?.parent
    }

    private fun getChildrenCount(node: RBNode<K, V>?): Int {
        if (node == null) return 0
        var count = 0

        if (node.leftChild != null) count++
        if (node.rightChild != null) count++
        return count
    }

    private fun getMin(node: RBNode<K, V>?): RBNode<K, V>? {
        if (node == null) return null
        var current: RBNode<K, V> = node

        while (current.leftChild != null) {
            current.leftChild.also { if (it != null) current = it }
        }
        return current
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
