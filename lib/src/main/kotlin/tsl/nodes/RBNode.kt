package tsl.nodes

class RBNode<K : Comparable<K>, V>(key: K, value: V) : AbstractNode<K, V, RBNode<K, V>>(key, value) {
    internal var parent: RBNode<K, V>? = null

    enum class Color {
        Black,
        Red,
    }

    internal var color = Color.Black

    internal fun switchColor() {
        if (this.color == Color.Black) {
            this.color = Color.Red
        } else {
            this.color = Color.Black
        }
    }

    internal fun rotateLeft() {
        val rightChild = this.rightChild ?: return
        val dad = this.parent

        this.switchColor(rightChild)
        rightChild.leftChild?.parent = this
        this.rightChild = rightChild.leftChild
        rightChild.leftChild = this

        when {
            this == dad?.leftChild -> dad.leftChild = rightChild
            this == dad?.rightChild -> dad.rightChild = rightChild
        }

        this.parent = rightChild
        rightChild.parent = dad
    }

    internal fun rotateRight() {
        val leftChild = this.leftChild ?: return
        val dad = this.parent

        this.switchColor(leftChild)
        leftChild.rightChild?.parent = this
        this.leftChild = leftChild.rightChild
        leftChild.rightChild = this

        when {
            this == dad?.leftChild -> dad.leftChild = leftChild
            this == dad?.rightChild -> dad.rightChild = leftChild
        }

        this.parent = leftChild
        leftChild.parent = dad
    }
}

