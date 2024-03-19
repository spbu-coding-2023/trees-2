package tsl.trees

import tsl.nodes.RBNode

class RBTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, RBNode<K, V>>() {
    override fun insert(
        key: K,
        value: V,
    ): V? { // in case we inserted successfully - > return null -> else -> return value, so user could have another try to insert it

        val newNode = RBNode(key, value)
        if (root == null) {
            root = RBNode(key, value)
            balanceNode(newNode)
            return null
        }

        var currentNode: RBNode<K, V>? = root

        // traverse the tree to find the insertion point(node)
        while (currentNode != null) {
            if (currentNode.key > newNode.key) {
                if (currentNode.leftChild == null) {
                    currentNode.leftChild = newNode
                    newNode.parent = currentNode
                    balanceNode(newNode)
                    return null
                }
                currentNode = currentNode.leftChild
            } else if (currentNode.key < newNode.key)
            {
                if (currentNode.rightChild == null) {
                    currentNode.rightChild = newNode
                    newNode.parent = currentNode
                    balanceNode(newNode)
                    return null
                }
                currentNode = currentNode.rightChild
            } else {
                return value
            }
        }
        balanceNode(newNode)
        return null
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
            } else {
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

    override fun delete(key: K): V? {
        val deleteNode: RBNode<K, V> = searchNode(key) ?: return null // track node that will replace other one
        val deleteNodeValue = deleteNode.value
        var colorOfTransferringNode = deleteNode.color
        val childNode: RBNode<K, V>? // node that will be on the place of deleteNode

        if (getChildrenCount(deleteNode) < 2) {
            childNode = if (deleteNode.leftChild != null) deleteNode.leftChild else deleteNode.rightChild
            transplantTwoNodes(deleteNode, childNode)
        } else {
            val minNode = getMin(deleteNode.rightChild)
            if (minNode != null) {
                deleteNode.key = minNode.key
                deleteNode.value = minNode.value
                colorOfTransferringNode = minNode.color
            }
            childNode = if (minNode?.leftChild != null) minNode.leftChild else minNode?.rightChild
            transplantTwoNodes(minNode, childNode)
        }

        if (colorOfTransferringNode == RBNode.Color.Black) fixAfterDelete(childNode)

        return deleteNodeValue // in case the deleting process was successful - we return value of deleted node. in other case - null
    }

    // TODO: fix case of deleting the root

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
                } else {
                    if (fixNodeBrother?.rightChild?.color == RBNode.Color.Black) {
                        fixNodeBrother.leftChild?.color = RBNode.Color.Black
                        fixNodeBrother.color = RBNode.Color.Red
                        fixNodeBrother.rotateRight()
                        fixNodeBrother = fixNode.parent?.rightChild
                    }
                    if (fixNodeBrother != null && fixNode.parent != null) {
                        fixNode.parent?.color.also { if (it != null) fixNodeBrother?.color = it }
                        /*this line of code checks if the color property of the parent of fixNode is not null,
                         * and if it isn't, it assigns that color to the color property of fixNodeBrother.
                         * safe call operator ensures that the code doesn't throw a NullPointerException
                         * if any of the properties are null. */
                    }
                    fixNode.parent?.color = RBNode.Color.Black
                    fixNodeBrother?.rightChild?.color = RBNode.Color.Black
                    fixNode.parent?.rotateLeft()
                    fixNode = root
                }
            } else {
                fixNodeBrother = fixNode.parent?.leftChild
                if (fixNodeBrother?.color == RBNode.Color.Red) {
                    fixNodeBrother.color = RBNode.Color.Black
                    fixNode.parent?.color = RBNode.Color.Red
                    fixNode.parent?.rotateRight()
                    fixNodeBrother = fixNode.parent?.leftChild
                }
                if (fixNodeBrother?.leftChild?.color == RBNode.Color.Black && fixNodeBrother.rightChild?.color == RBNode.Color.Black) {
                    fixNode.parent?.rotateRight()
                    fixNodeBrother = fixNode.parent?.leftChild
                } else {
                    if (fixNodeBrother?.leftChild?.color == RBNode.Color.Black) {
                        fixNodeBrother.rightChild?.color = RBNode.Color.Black
                        fixNodeBrother.color = RBNode.Color.Red
                        fixNodeBrother.rotateLeft()
                        fixNodeBrother = fixNode.parent?.leftChild
                    }
                    fixNode.parent?.color.also { if (it != null) fixNodeBrother?.color = it }
                    fixNode.parent?.color = RBNode.Color.Black
                    fixNodeBrother?. leftChild?.color = RBNode.Color.Black
                    fixNode.parent?.rotateRight()
                    fixNode = root
                }
            }
        }
        fixNode?.color = RBNode.Color.Black
    }

    private fun searchNode(key: K): RBNode<K, V>? {
        var currentNode = root
        while (currentNode != null) {
            if (key == currentNode.key) {
                return currentNode
            }
            currentNode = if (key < currentNode.key) currentNode.leftChild else currentNode.rightChild
        }
        return null
    }

    // TODO: move this type of methods to abstract mb


    // this method movest the parent of  2nd node -> to 1st node
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
}
