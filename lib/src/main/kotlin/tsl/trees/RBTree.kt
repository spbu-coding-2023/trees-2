package tsl.trees

import tsl.nodes.RBNode

class RBTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, RBNode<K, V>>() {

    // @return Null if 'insert' method haven't replaced any value. Return value if there was some
    // replace

    public override fun insert(key: K, value: V): V? {

        val newNode = RBNode(key, value)
        if (root == null) {
            root = RBNode(key, value)
            fixAfterInsertion(newNode)
            return null
        }

        var currentNode: RBNode<K, V>? = root
        val returnValue: V? = search(key)

        // traverse the tree to find the insertion point (node)
        while (currentNode != null) {
            if (currentNode.key > newNode.key) {
                if (currentNode.leftChild == null) {
                    currentNode.leftChild = newNode
                    newNode.parent = currentNode
                    fixAfterInsertion(newNode)
                    break
                }
                currentNode = currentNode.leftChild
            } else if (currentNode.key < newNode.key) {
                if (currentNode.rightChild == null) {
                    currentNode.rightChild = newNode
                    newNode.parent = currentNode
                    fixAfterInsertion(newNode)
                    break
                }
                currentNode = currentNode.rightChild
            } else {
                currentNode.value = value
                fixAfterInsertion(newNode)
                break
            }
        }
        return returnValue
    }

    private fun fixAfterInsertion(node: RBNode<K, V>?) {
        if (node?.parent == null) root.also { it?.color = RBNode.Color.Black }
        var newNode = node
        var uncle: RBNode<K, V>?
        var newRoot = root

        while (newNode?.parent?.color == RBNode.Color.Red) {
            val currentParent: RBNode<K, V>? = newNode.parent
            val currentGrandParent: RBNode<K, V>? = currentParent?.parent
            if (currentParent == currentGrandParent?.leftChild) {
                uncle = currentParent?.parent?.rightChild
                if (uncle?.color == RBNode.Color.Red) {
                    currentParent?.color = RBNode.Color.Black
                    uncle.color = RBNode.Color.Black
                    currentGrandParent?.color = RBNode.Color.Red
                    newNode = currentGrandParent
                } else {
                    if (
                        currentParent?.color == RBNode.Color.Red &&
                            newNode.color == RBNode.Color.Red &&
                            newNode == currentParent.rightChild
                    ) {
                        newRoot = rotateLeft(currentParent, newRoot)
                        newNode.color = RBNode.Color.Black
                        newNode.leftChild?.color = RBNode.Color.Red
                        newRoot = rotateRight(currentGrandParent, newRoot)
                        newNode.rightChild?.color = RBNode.Color.Red
                        break
                    }
                    currentParent?.color = RBNode.Color.Black
                    currentGrandParent?.color = RBNode.Color.Red
                    newRoot = rotateRight(currentGrandParent, newRoot)
                }
            } else if (currentParent == currentGrandParent?.rightChild) {
                uncle = newNode.parent?.parent?.leftChild
                if (uncle?.color == RBNode.Color.Red) {
                    currentParent?.color = RBNode.Color.Black
                    uncle.color = RBNode.Color.Black
                    currentGrandParent?.color = RBNode.Color.Red
                    newNode = currentGrandParent
                } else {
                    if (
                        currentParent?.color == RBNode.Color.Red &&
                            newNode.color == RBNode.Color.Red &&
                            newNode == currentParent.leftChild
                    ) {
                        currentParent.leftChild?.color = RBNode.Color.Black
                        newRoot = rotateRight(currentParent, newRoot)
                    }
                    currentParent?.color = RBNode.Color.Black
                    currentGrandParent?.color = RBNode.Color.Red
                    newRoot = rotateLeft(currentGrandParent, newRoot)
                }
            }
        }
        newRoot?.color = RBNode.Color.Black
        root = newRoot
    }

    // @return Value if delete was successful and null if it isn't.

    public override fun delete(key: K): V? {
        val deletingNodeValue = search(key)
        if (deletingNodeValue == null) return null // in case we do not found the deletinnode
        root = deleteNode(key)
        root?.color = RBNode.Color.Black // ensure the root is black after deletion
        return deletingNodeValue
    }

    // @return The new root node of the Red\-Black Tree after deletion\.

    private fun deleteNode(
        key: K,
    ): RBNode<K, V>? {
        var current: RBNode<K, V>? = searchNodeRec(root, key)
        if (current == null) return null
        var newRoot = root

        if (current.hasTwoChildren()) {
            val successor = getMaxNodeRec(current.leftChild)
            if (successor?.parent?.leftChild == successor) successor?.parent?.leftChild = null
            else successor?.parent?.rightChild = null
            if (successor != null) {
                current.value = successor.value
                current.key = successor.key
            }
            current = successor
            if (successor?.color == RBNode.Color.Red) {
                return newRoot
            }
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

    // @return The root of the tree after fixing the structure to maintain the red-black properties

    private fun fixAfterDeletion(currentDeletingNode: RBNode<K, V>?): RBNode<K, V>? {
        var fixedRoot = root
        var currentNode = currentDeletingNode
        // currentNode - variable that is used to perform various operations and control the loop of
        // fixing the tree.

        while (currentNode != root && currentNode?.color == RBNode.Color.Black) {
            if (currentNode == currentNode.parent?.leftChild) {
                var currentSibling: RBNode<K, V>? = currentNode.parent?.rightChild
                if (currentSibling?.color == RBNode.Color.Red) { // 1st case "currentSibling is red"
                    currentSibling.color = RBNode.Color.Black
                    currentNode.parent?.color = RBNode.Color.Red
                    fixedRoot = rotateLeft(currentNode.parent, fixedRoot)
                } else {
                    // 2nd case "black currentSibling, right child is black"
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
                } else {
                    // 2nd case "black currentSibling, left child is red"
                    currentSibling?.color = currentNode.parent?.color ?: RBNode.Color.Black
                    currentNode.parent?.color = RBNode.Color.Black
                    currentSibling?.leftChild?.color = RBNode.Color.Black
                    fixedRoot = rotateRight(currentNode.parent, fixedRoot)
                    currentNode = fixedRoot
                }
            }
        }
        fixAfterInsertion(fixedRoot)
        currentNode?.color = RBNode.Color.Black
        return fixedRoot
    }

    private fun rotateRight(
        nodeToRotate: RBNode<K, V>?,
        currentRoot: RBNode<K, V>?,
    ): RBNode<K, V>? {
        val leftChild = nodeToRotate?.leftChild
        var newRoot = currentRoot

        leftChild?.parent = nodeToRotate?.parent
        nodeToRotate?.parent = leftChild

        if (leftChild?.rightChild != null) {
            nodeToRotate.leftChild = leftChild.rightChild
            leftChild.rightChild?.parent = nodeToRotate
            leftChild.rightChild = nodeToRotate
        } else {
            leftChild?.rightChild = nodeToRotate
            nodeToRotate?.leftChild = null
        }

        if (leftChild?.parent != null) {
            if (leftChild.parent?.leftChild == nodeToRotate) {
                leftChild.parent?.leftChild = leftChild
            } else {
                leftChild.parent?.rightChild = leftChild
            }
        } else {
            root = leftChild
            newRoot = leftChild
        }
        return newRoot
    }

    private fun rotateLeft(
        nodeToRotate: RBNode<K, V>?,
        currentRoot: RBNode<K, V>?,
    ): RBNode<K, V>? {
        val rightChild = nodeToRotate?.rightChild
        var newRoot = currentRoot

        rightChild?.parent = nodeToRotate?.parent
        nodeToRotate?.parent = rightChild

        if (rightChild?.leftChild != null) {
            nodeToRotate.rightChild = rightChild.leftChild
            rightChild.leftChild?.parent = nodeToRotate.rightChild
            rightChild.leftChild = nodeToRotate
        } else {
            rightChild?.leftChild = nodeToRotate
            nodeToRotate?.rightChild = null
        }

        // rightChild took over "nodeToRotate" place so now its parent should point to rightChild
        if (rightChild?.parent != null) {
            if (rightChild.parent?.rightChild == nodeToRotate) {
                rightChild.parent?.rightChild = rightChild
            } else {
                rightChild.parent?.leftChild = rightChild
            }
        } else {
            root = rightChild
            newRoot = rightChild
        }
        return newRoot
    }
}
