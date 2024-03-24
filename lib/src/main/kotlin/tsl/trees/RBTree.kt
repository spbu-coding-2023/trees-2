package tsl.trees

import tsl.nodes.RBNode

class RBTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, RBNode<K, V>>() {
    override fun insert(
        key: K,
        value: V,
    ): V? { // in case key isnt in the tree/ inserts successfully-> return null -> else -> return value

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
            } else if (currentNode.key < newNode.key) {
                if (currentNode.rightChild == null) {
                    currentNode.rightChild = newNode
                    newNode.parent = currentNode
                    balanceNode(newNode)
                    return null
                }
                currentNode = currentNode.rightChild
            } else {
                balanceNode(newNode)
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
        root = deleteNode(root, key)
        root?.color = RBNode.Color.Black // Ensure the root is black after deletion
        return null
    }

    private fun deleteNode(node: RBNode<K, V>?, key: K): RBNode<K, V>? {
        var current: RBNode<K, V>? = searchNode(root, key) as RBNode<K, V>?
        if (current == null) return root
        var newRoot = root

        if (current.leftChild != null && current.rightChild != null) {
            val successor = getMaxNode(current.leftChild)
            if (successor != null) {
                current.value = successor.value
                current.key = successor.key
            }
            current = successor
        }


        val child = if (current?.leftChild != null) current.leftChild else current?.rightChild
        if (child != null) {
            child.parent = current?.parent
            if (current?.parent == null) return child
            if (current == current.parent?.leftChild) {
                current.parent?.leftChild = child
            } else {
                current.parent?.rightChild = child
            }

            if (current.color == RBNode.Color.Black) {
                newRoot = fixAfterDeletion(child)
            }
        } else if (current?.parent == null) {
            return null
        } else {
            if (current.color == RBNode.Color.Black) {
                newRoot = fixAfterDeletion(current)
            }
            if (current.parent?.leftChild == current) {
                current.parent?.leftChild = null
            } else {
                current.parent?.rightChild = null
            }
        }
        return newRoot
    }

    private fun fixAfterDeletion(currentDeletingNode: RBNode<K, V>?): RBNode<K, V>? {
        var fixedRoot = root
        var currentNode = currentDeletingNode
        // currentNode - variable that is used to perform various operations and control the loop of fixing the tree.

        while (currentNode != root && currentNode?.color == RBNode.Color.Black) {
            if (currentNode == currentNode.parent?.leftChild) {
                var currentSibling: RBNode<K, V>? = currentNode.parent?.rightChild
                if (currentSibling?.color == RBNode.Color.Red) { // 1st case "currentSibling is red"
                    currentSibling.color = RBNode.Color.Black
                    currentNode.parent?.color = RBNode.Color.Red
                    fixedRoot = rotateLeft(currentNode.parent, fixedRoot)
                    currentSibling = currentNode.parent?.rightChild  // transfer to other cases
                }
                else if (currentSibling?.leftChild?.color == RBNode.Color.Black && currentSibling.rightChild?.color == RBNode.Color.Black) {
                    // 2nd case "currentSibling and its children are black"
                    currentSibling.color = RBNode.Color.Red
                    currentNode = currentNode.parent
                } else {
                    if (currentSibling?.rightChild?.color == RBNode.Color.Black) {
                        // 3rd case "like 2nd but the left child is red"
                        (currentSibling.leftChild )?.color = RBNode.Color.Black
                        currentSibling.color = RBNode.Color.Red
                        fixedRoot = rotateRight(currentSibling, fixedRoot)
                        currentSibling = currentNode.parent?.rightChild
                    }
                    // 4th case "black currentSibling, right child is black'
                    currentSibling?.color = currentNode.parent?.color ?: RBNode.Color.Black
                    currentNode.parent?.color = RBNode.Color.Black
                    currentSibling?.rightChild?.color = RBNode.Color.Black
                    fixedRoot = rotateLeft(currentNode.parent, fixedRoot)
                    currentNode = fixedRoot
                }
            } else {
                var currentSibling: RBNode<K, V>? = currentNode.parent?.leftChild
                if (currentSibling?.color == RBNode.Color.Red) {
                    // 1st case "red currentSibling"
                    currentSibling.color = RBNode.Color.Black
                    currentNode.parent?.color = RBNode.Color.Red
                    fixedRoot = rotateRight(currentNode.parent, fixedRoot)
                    currentSibling = currentNode.parent?.leftChild  // transfer to other cases
                }
                // 2nd case "currentSibling and its children are black"
                if (currentSibling?.leftChild?.color == RBNode.Color.Black && currentSibling.rightChild?.color == RBNode.Color.Black) {
                    currentSibling.color = RBNode.Color.Red
                    currentNode = currentNode.parent
                } else {
                    // 3rd case "like 2nd but the right child is red"
                    if (currentSibling?.leftChild?.color == RBNode.Color.Black) {
                        currentSibling.rightChild?.color = RBNode.Color.Black
                        currentSibling.color = RBNode.Color.Red
                        fixedRoot = rotateLeft(currentSibling, fixedRoot)
                        currentSibling = currentNode.parent?.leftChild
                    }
                    // 4th case "black currentSibling, left child is red'
                    currentSibling?.color = currentNode.parent?.color ?: RBNode.Color.Black
                    (currentNode.parent)?.color = RBNode.Color.Black
                    (currentSibling?.leftChild)?.color = RBNode.Color.Black
                    fixedRoot = rotateRight(currentNode.parent, fixedRoot)
                    currentNode = fixedRoot
                }
            }
        }
        currentNode?.color = RBNode.Color.Black
        return fixedRoot
    }
}