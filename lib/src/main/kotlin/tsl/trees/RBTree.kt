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

    /**
     * This function is used to balance the red-black tree node after insertion.
     * It checks the colors of the parent and grandparent nodes to determine the rotations needed for balancing.
     * The function alternates between left and right rotations to maintain the balance of the tree.
     * The root variable is updated to maintain the correct root node after balancing.
     * @param RBNode-typed node
     */

    private fun balanceNode(node: RBNode<K, V>?) {
        var newNode = node
        var uncle: RBNode<K, V>?
        var newRoot = root
        while (newNode?.parent?.color == RBNode.Color.Red && newNode.parent != newRoot) {
            var currentParent: RBNode<K, V>? = newNode.parent
            val currentGrandParent: RBNode<K, V>? = currentParent?.parent
            if (newNode.parent == newNode.parent?.parent?.leftChild) {
                uncle = currentParent?.parent?.rightChild

                if (uncle?.color == RBNode.Color.Red) {
                    currentParent?.color = RBNode.Color.Black
                    uncle.color = RBNode.Color.Black
                    currentGrandParent?.color = RBNode.Color.Red
                    newNode = currentGrandParent
                } else {
                    if (newNode == newNode.parent?.rightChild) {
                        newNode = currentParent
                        newRoot = rotateLeft(newNode, newRoot)
                        currentParent = newNode?.parent
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
                    if (newNode == currentParent?.leftChild) {
                        newNode = currentParent
                        newRoot = rotateRight(newNode, newRoot)
                        currentParent = newNode.parent
                    }
                    currentParent?.color = RBNode.Color.Black
                    currentGrandParent?.color = RBNode.Color.Red
                    newRoot = rotateLeft(currentGrandParent, newRoot)
                }
            }
        }
        root = newRoot
    }

    override fun delete(key: K): V? {
        root = deleteNode(root, key)
        root?.color = RBNode.Color.Black // ensure the root is black after deletion
        return null
    }

    /**
    * Deletes a node with the specified key from the Red\-Black Tree\.
    * If the node with the key is found, it is removed from the tree and the tree is rebalanced if necessary\.
    *
    * @param node The root node of the Red\-Black Tree\.
    * @param key The key of the node to be deleted\.
    * @return The new root node of the Red\-Black Tree after deletion\.
    */

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

    /**
     * This function is used to fix the red-black tree structure after a node deletion to ensure that the red-black
     * properties of the tree are maintained.
     * It iterates through the nodes and performs rotations and recoloring based on the cases derived from the sibling
     * and its children's colors to balance the tree.
     *
     * @param currentNode The node that needs fixing after deletion
     * @return The root of the tree after fixing the structure to maintain the red-black properties
     */

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
    private fun rotateRight(node: RBNode<K, V>?, currentRoot: RBNode<K, V>?): RBNode<K, V>? {
        val temp = node?.leftChild ?: return node // if left child is null, no rotation needed
        var newRoot = currentRoot
        temp.parent = node.parent

        if (temp.leftChild != null) {
            temp.leftChild?.parent = node
        }
        temp.rightChild = node
        node.parent = temp

        if (temp.parent != null) {
            if (node == temp.parent?.leftChild) {
                temp.parent?.leftChild = temp
            } else {
                temp.parent?.rightChild = temp
            }
        } else {
            root = temp
        }

        return newRoot
    }

    private fun rotateLeft(node: RBNode<K, V>?, currentRoot: RBNode<K, V>?): RBNode<K, V>? {
        val temp = node?.rightChild ?: return node // if right child is null, no rotation needed
        var newRoot = currentRoot
        temp.parent = node.parent

        node.rightChild = temp.leftChild;
        if (node.rightChild != null) {
            node.rightChild?.parent = node;
        }

        temp.leftChild = node;
        node.parent = temp;

        // temp took over node's place so now its parent should point to temp
        if (temp.parent != null) {
            if (node == temp.parent?.leftChild) {
                temp.parent?.leftChild = temp;
            } else {
                temp.parent?.rightChild = temp;
            }
        } else {
            root = temp;
            newRoot = temp
        }
        return newRoot
    }
}