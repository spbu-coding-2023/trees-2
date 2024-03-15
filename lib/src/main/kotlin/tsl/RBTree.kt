import nodes.RBNode

class ExitLoopException : Exception()

class RBTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, RBNode<K, V>>() {

    override fun insert(
        key: K,
        value: V,
    ) { // идем по дереву и вставляем
        insertNode(root, key, value)
    }

    private fun insertNode(
        node: RBNode<K, V>?,
        key: K,
        value: V,
    ): RBNode<K, V>? {
        if (root == null) {
            val newRoot = RBNode(key, value)
            root = newRoot
            return newRoot
        }
        if (node == null) {
            return RBNode(key, value).apply { color = RBNode.Color.Red }
        }

        var insertedNode: RBNode<K, V>? = null
        if (key < node.key) {
            insertedNode = insertNode(node.leftChild, key, value)
            node.leftChild = insertedNode
            insertedNode?.parent = node
            balanceNode(insertedNode)
            throw ExitLoopException()

        } else if (key > node.key) {
            insertedNode = insertNode(node.rightChild, key, value)
            node.rightChild = insertedNode
            insertedNode?.parent = node
            balanceNode(insertedNode)
            throw ExitLoopException()

        } else {
            println("Duplicate keys are not allowed") // throw expression
        }
        return null
    }
    private fun balanceNode(node: RBNode<K, V>?) {
        var newNode = node
        var uncle: RBNode<K, V>?
        while (newNode?.parent?.color == RBNode.Color.Red) {
            if (newNode.parent == newNode.parent?.parent?.leftChild) {
                uncle = newNode.parent?.parent?.rightChild
                if (uncle?.color == RBNode.Color.Red) {
                    newNode.parent?.color = RBNode.Color.Black
                    uncle.color = RBNode.Color.Black
                    newNode.parent?.parent?.color = RBNode.Color.Red
                    newNode = newNode.parent?.parent
                } else {
                    if (newNode == newNode.parent?.rightChild) {
                        newNode = newNode.parent
                        newNode?.let { rotateLeft(it) }
                    }
                    newNode?.parent?.color = RBNode.Color.Black
                    newNode?.parent?.parent?.color = RBNode.Color.Red
                    newNode?.parent?.parent?.let { rotateRight(it) }
                }
            } else {
                uncle = newNode.parent?.parent?.leftChild
                if (uncle?.color == RBNode.Color.Red) {
                    newNode.parent?.color = RBNode.Color.Black
                    uncle.color = RBNode.Color.Black
                    newNode.parent?.parent?.color = RBNode.Color.Red
                    newNode = newNode.parent?.parent
                } else {
                    if (newNode == newNode.parent?.leftChild) {
                        newNode = newNode.parent
                        newNode?.let { rotateRight(it) }
                    }
                    newNode?.parent?.color = RBNode.Color.Black
                    newNode?.parent?.parent?.color = RBNode.Color.Red
                    newNode?.parent?.parent?.let { rotateLeft(it) }
                }
            }
        }
        root?.color = RBNode.Color.Black
    }

    private fun rotateLeft(node: RBNode<K, V>): RBNode<K, V>? {
        val temp = node.rightChild
        node.rightChild = temp?.leftChild
        temp?.rightChild = node
        temp?.color = node.color
        node.color = RBNode.Color.Red
        return temp
    }

    private fun rotateRight(node: RBNode<K, V>): RBNode<K, V>? {
        val temp = node.leftChild
        node.leftChild = temp?.rightChild
        temp?.rightChild = node
        temp?.color = node.color
        node.color = RBNode.Color.Red
        return temp
    }

    override fun delete(key: K) {
        root = deleteNodeRecursive(root, key)
    }

    private fun deleteNodeRecursive(
        node: RBNode<K, V>?,
        key: K,
    ): RBNode<K, V>? {
        val currentNode = node ?: return null

        when {
            key < currentNode.key -> currentNode.leftChild = deleteNodeRecursive(currentNode.leftChild, key)
            key > currentNode.key -> currentNode.rightChild = deleteNodeRecursive(currentNode.rightChild, key)
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
                        currentNode.rightChild = deleteNodeRecursive(currentNode.rightChild, successor.key)
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
            if (this == node.parent?.leftChild) node.parent?.let { rotateRight(it) } else node.parent?.let { rotateLeft(it) }
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
                        rotateLeft(it)
                    }
                } else {
                    it.leftChild?.let { left ->
                        left.color = RBNode.Color.Black
                        it.color = RBNode.Color.Red
                        rotateRight(it)
                    }
                }
                return null
            }
        }

        node.parent?.let {
            sibling?.color = it.color
            it.color = RBNode.Color.Black
        }
        return if (node == node.parent?.leftChild) node.parent?.let { rotateRight(it) } else node.parent?.let { rotateLeft(it) }
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

    fun rightSubbfsPrint(node: RBNode<K, V>?) {
        if (node != null) {
            println("${node.key}")
            rightSubbfsPrint(node.rightChild)
        }
    }

}
