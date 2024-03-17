package tsl.trees

import tsl.nodes.RBNode

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
                        if (newNode!!.parent?.parent == null) root = newNode.parent
                        newNode.rotateLeft()
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
                        if (newNode!!.parent?.parent == null) root = newNode.parent
                        newNode.rotateRight()
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
        root = deleteNodeRecursively(root, key)
    }

    private fun deleteNodeRecursively(
        node: RBNode<K, V>?,
        key: K,
    ): RBNode<K, V>? {
        val currentNode = node ?: return null

        when {
            key < currentNode.key -> currentNode.leftChild = deleteNodeRecursively(currentNode.leftChild, key)
            key > currentNode.key -> currentNode.rightChild = deleteNodeRecursively(currentNode.rightChild, key)
            else -> {
                if (currentNode.leftChild == null || currentNode.rightChild == null) {
                    val temp = if (currentNode.leftChild != null) currentNode.leftChild else currentNode.rightChild
                    if (temp == null) {
                        if (currentNode.color == RBNode.Color.Black) {
                            fixDoubleBlack(currentNode)
                        }
                        return null
                    } else {
                        return temp
                    }
                } else {
                    var successor = currentNode.rightChild
                    while (successor?.leftChild != null) {
                        successor = successor.leftChild
                    }
                    if (successor != null) {
                        currentNode.key = successor.key
                        currentNode.value = successor.value
                        currentNode.rightChild = deleteNodeRecursively(currentNode.rightChild, successor.key)
                    }
                }
            }
        }
        return currentNode
    }

    private fun fixDoubleBlack(node: RBNode<K, V>) {
        var currentNode = node
        while (currentNode != root && currentNode.color == RBNode.Color.Black) {
            currentNode = fixDoubleBlackHelper(currentNode) ?: return
        }
        currentNode.color = RBNode.Color.Black
    }

    private fun fixDoubleBlackHelper(node: RBNode<K, V>): RBNode<K, V>? {
        val sibling =
            when {
                node == node.parent?.leftChild -> node.parent?.rightChild
                else -> node.parent?.leftChild
            }

        sibling?.takeIf { it.color == RBNode.Color.Red }?.run {
            color = RBNode.Color.Black
            node.parent?.color = RBNode.Color.Red
            if (this == node.parent?.leftChild) node.parent?.rotateRight() else node.parent?.rotateLeft()
            return null
        }

        if ((sibling?.leftChild?.color ?: RBNode.Color.Black) == RBNode.Color.Black &&
            (sibling?.rightChild?.color ?: RBNode.Color.Black) == RBNode.Color.Black
        ) {
            sibling?.color = RBNode.Color.Red
            return node.parent
        }

        sibling?.let {
            if (it.color == RBNode.Color.Black) {
                if (node == node.parent?.leftChild) {
                    it.rightChild?.let { right ->
                        right.color = RBNode.Color.Black
                        it.color = RBNode.Color.Red
                        it.rotateLeft()
                    }
                } else {
                    it.leftChild?.let { left ->
                        left.color = RBNode.Color.Black
                        it.color = RBNode.Color.Red
                        it.rotateRight()
                    }
                }
                return null
            }
        }

        node.parent?.let {
            sibling?.color = it.color
            it.color = RBNode.Color.Black
        }
        return if (node == node.parent?.leftChild) node.parent?.rotateRight() else node.parent?.rotateLeft()
    }

    fun printTree() {
        printTree(root, "", true)
    }

    private fun printTree(node: RBNode<K, V>?, prefix: String, isTail: Boolean) {
        if (node != null) {
            printTree(node.rightChild, "$prefix${if (isTail) "│   " else "    "}", false)
            println("$prefix${if (isTail) "└── " else "┌── "}${if (node.color == RBNode.Color.Red) "\u001b[31m" else "\u001b[30;1m"}(${node.key},${node.value})\u001b[0m")
            printTree(node.leftChild, "$prefix${if (isTail) "    " else "│   "}", true)
        }
    }
}