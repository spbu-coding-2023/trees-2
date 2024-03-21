package tsl.trees

import tsl.nodes.BSTNode

class BSTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, BSTNode<K, V>>() {

	override fun insert(key: K, value: V): V? {
		val nodeToInsert = BSTNode(key, value)

		if (root == null) {
			root = nodeToInsert
			return null
		}

		var currentNode: BSTNode<K, V> = root
		while (true) {
			if (nodeToInsert.key == currentNode.key) {
				val oldValue = currentNode.value
				currentNode.value = nodeToInsert.value
				return oldValue
			}
			else if (nodeToInsert.key < currentNode.key) {
				if (currentNode.leftChild == null) {
					currentNode.leftChild = nodeToInsert
					return null
				}
				currentNode = currentNode.leftChid
			}
			else if (nodeToInsert.key > currentNode.key) {
				if (currentNode.rightChild == null) {
					currentNode.rightChild = nodeToInsert
					return null
				}
				currentNode = currentNode.rightChild
			}
		}
	}

	override fun delete(key: K): V? {
		val deletedValue? = search(key) ?: return null
		deleteRecursively(root, key)
		return deletedValue
	}

	private fun deleteRecursively(node: BSTNode<K, V>, key): BSTNode {
		if (node == null) return null
		if (key < node.key) node.leftChild = deleteRecursively(node.leftChild, key)
		else if (key > node.key) node.rightChild = deleteRecursively(node.rightChild, key)
		else if (key == node.key) {
			if (node.leftChild == null || node.rightChild == null)
				return node.leftChild ?: node.rightChild
			else {
				val minRightNode = getMinNode(node.rightChild)
				node.key = minRightNode.key
				node.value = minRightNode.value
				node.rightChild = deleteRecursively(node.rightChild, minRightNode.key)
			}
		}
	}
