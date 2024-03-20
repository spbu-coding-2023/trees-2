package tsl.trees

import tsl.nodes.BSTNode

class BSTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, BSTNode<K, V>>() {

	override fun insert(key: K, value: V): V? {
		val nodeToInsert = BSTNode(key, value)

		if (root == null) {
			root = nodeToInsert
			return null
		}

		var currentNode: BSTNode<K, V>? = root
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

