package tsl.trees

import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tsl.nodes.RBNode

/*
 * Some standarts and "variables" that were used for decreasing the amount of words)
 * S - sibling of deleting node
 * V - deleting node
 * U - node that will take place of deleting one
 */

class RBTreeTest {
    private lateinit var rbTree: RBTree<Int, String>
    private lateinit var inorderedTraversalTree: MutableList<Pair<Int, String>>

    @BeforeEach
    fun setup() {
        rbTree = RBTree()
        inorderedTraversalTree = mutableListOf()
    }

    @Nested
    inner class InsertTests {

        @Test
        fun `insert if the node with this key doesn't exist`() {
            rbTree.insert(15, "i")
            rbTree.insert(10, "am")
            rbTree.insert(5, "really")
            val returnValue = rbTree.insert(7, "wordless")
            assertEquals(null, returnValue)
        }

        @Test
        fun `insert if the node with this key exists`() {
            rbTree.insert(15, "i")
            rbTree.insert(10, "am")
            rbTree.insert(5, "really")
            val returnValue = rbTree.insert(5, "wordless")
            assertEquals("really", returnValue)
        }

        @Test
        fun `insert left child`() {
            rbTree.insert(17, "legend")
            rbTree.insert(27, "club")
            rbTree.insert(49, "poliklinika")
            val returnValue = rbTree.insert(16, "18+")
            assertEquals(null, returnValue)
            assertEquals(16, rbTree.root?.leftChild?.leftChild?.key)
        }

        @Test
        fun `insert right child`() {
            rbTree.insert(17, "legend")
            rbTree.insert(27, "club")
            rbTree.insert(49, "poliklinika")
            val returnValue = rbTree.insert(20, "wordless")
            assertEquals(null, returnValue)
            assertEquals(20, rbTree.root?.leftChild?.rightChild?.key)
        }
    }

    @Nested
    inner class DeleteTests {

        @Test
        fun `successful delete (return value check)`() {
            rbTree.insert(30, "pink")
            rbTree.insert(20, "white")
            rbTree.insert(40, "red")

            val returnValue = rbTree.delete(40)
            assertEquals("red", returnValue)
        }

        @Test
        fun `unsuccessful delete (return value check)`() {
            rbTree.insert(30, "pink")
            rbTree.insert(20, "white")
            rbTree.insert(40, "red")

            val returnValue = rbTree.delete(50)
            assertEquals(null, returnValue)
        }

        @Test
        fun `delete if sibling and its left-child are black`() {
            rbTree.insert(30, "pink")
            rbTree.insert(20, "white")
            rbTree.insert(40, "red")
            rbTree.insert(50, "yellow")

            val returnValue = rbTree.delete(20)
            assertEquals("white", returnValue)
        }

        @Test
        fun `delete right-right case`() {
            // s is right child of its parent and both children of s are red
            rbTree.insert(30, "pink")
            rbTree.insert(18, "white")
            rbTree.insert(40, "red")
            rbTree.insert(45, "yellow")

            val returnValue = rbTree.delete(40)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals("red", returnValue)
            assertEquals(
                listOf(Pair(18, "white"), Pair(30, "pink"), Pair(45, "yellow")),
                inorderedTraversalTree
            )
        }

        @Test
        fun `delete left-left case`() {
            // s is left child of its parent and both children of s are red
            rbTree.insert(30, "pink")
            rbTree.insert(40, "white")
            rbTree.insert(18, "red")
            rbTree.insert(10, "yellow")

            val returnValue = rbTree.delete(18)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(10, "yellow"), Pair(30, "pink"), Pair(40, "white")),
                inorderedTraversalTree
            )
            assertEquals("red", returnValue)
        }

        @Test
        fun `delete right-left case`() {
            // s is right child of its parent and r is left child of s
            rbTree.insert(30, "pink")
            rbTree.insert(40, "white")
            rbTree.insert(18, "red")
            rbTree.insert(35, "yellow")

            val returnValue = rbTree.delete(40)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(18, "red"), Pair(30, "pink"), Pair(35, "yellow")),
                inorderedTraversalTree
            )
            assertEquals("white", returnValue)
        }

        @Test
        fun `delete left-right case`() {
            // s is left child of its parent and r is right child
            rbTree.insert(30, "pink")
            rbTree.insert(40, "white")
            rbTree.insert(18, "red")
            rbTree.insert(20, "yellow")

            val returnValue = rbTree.delete(18)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(20, "yellow"), Pair(30, "pink"), Pair(40, "white")),
                inorderedTraversalTree
            )
            assertEquals("red", returnValue)
        }

        @Test
        fun `delete left-red sibling`() {
            rbTree.insert(20, "pink")
            rbTree.insert(30, "white")
            rbTree.insert(10, "red")
            rbTree.insert(25, "brown")
            rbTree.insert(35, "gold")

            val returnValue = rbTree.delete(10)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(20, "pink"), Pair(25, "brown"), Pair(30, "white"), Pair(35, "gold")),
                inorderedTraversalTree
            )
            assertEquals("red", returnValue)
        }

        @Test
        fun `delete root - sole node in tree`() {
            rbTree.insert(10, "hihi haha")
            val returnValue = rbTree.delete(10)
            assertEquals(null, returnValue)
        }

        @Test
        fun `delete node with two children`() {
            rbTree.insert(20, "pink")
            rbTree.insert(30, "white")
            rbTree.insert(10, "red")
            rbTree.insert(25, "brown")
            rbTree.insert(35, "gold")

            val returnValue = rbTree.delete(30)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(10, "red"), Pair(20, "pink"), Pair(25, "brown"), Pair(35, "gold")),
                inorderedTraversalTree
            )
            assertEquals("white", returnValue)
        }

        @Test
        fun `delete lonely black node with red sibling`() {
            rbTree.insert(20, "hi")
            rbTree.insert(10, "hihi")
            rbTree.insert(40, "what")
            rbTree.insert(30, "omg")
            rbTree.insert(45, "damn")
            rbTree.insert(33, "!")

            val returnValue = rbTree.delete(10)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(
                    Pair(20, "hi"),
                    Pair(30, "omg"),
                    Pair(33, "!"),
                    Pair(40, "what"),
                    Pair(45, "damn")
                ),
                inorderedTraversalTree
            )
            assertEquals("hihi", returnValue)
        }

        @Test
        fun `delete left-black node with red-right child`() {
            rbTree.insert(100, "huh")
            rbTree.insert(80, "?")
            rbTree.insert(200, "you")
            rbTree.insert(60, "meow")
            rbTree.insert(90, ".")
            rbTree.insert(88, "cat")

            val returnValue = rbTree.delete(60)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(
                    Pair(80, "?"),
                    Pair(88, "cat"),
                    Pair(90, "."),
                    Pair(100, "huh"),
                    Pair(200, "you")
                ),
                inorderedTraversalTree
            )
            assertEquals("meow", returnValue)
        }

        @Test
        fun `delete black node with zero children and black sibling`() {
            rbTree.insert(110, "walking")
            rbTree.insert(238, "in")
            rbTree.insert(88, "of")
            rbTree.insert(233, "phone")

            val returnValue = rbTree.delete(233)
            rbTree.delete(88)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(110, "walking"), Pair(238, "in")), inorderedTraversalTree)
            assertEquals("phone", returnValue)
        }

        @Test
        fun `delete left-red node with no children`() {
            val rbTree = RBTree<Int, String>()
            rbTree.insert(6, "monkey")
            rbTree.insert(4, "you")
            rbTree.insert(7, "dog")
            rbTree.insert(2, "cat")
            rbTree.insert(5, "dog")
            rbTree.insert(1, "cat")

            val returnValue = rbTree.delete(7)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(
                    Pair(1, "cat"),
                    Pair(2, "cat"),
                    Pair(4, "you"),
                    Pair(5, "dog"),
                    Pair(6, "monkey")
                ),
                inorderedTraversalTree
            )
            assertEquals("dog", returnValue)
        }

        @Test
        fun `delete black node with two children (left subtree & right leaf)`() {
            val rbTree = RBTree<Int, String>()
            rbTree.insert(200, "Im")
            rbTree.insert(160, "gonna make")
            rbTree.insert(400, "him an")
            rbTree.insert(120, "offer")
            rbTree.insert(180, "he")
            rbTree.insert(130, "cant refuse")

            val returnValue = rbTree.delete(200)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(
                    Pair(120, "offer"),
                    Pair(130, "cant refuse"),
                    Pair(160, "gonna make"),
                    Pair(180, "he"),
                    Pair(400, "him an")
                ),
                inorderedTraversalTree
            )
            assertEquals("Im", returnValue)
        }

        @Test
        fun `fix after deletion second case`() {
            rbTree.insert(15, "c")
            rbTree.insert(10, "a")
            rbTree.insert(20, "e")
            rbTree.insert(5, "b")
            rbTree.insert(6, "d")

            val returnValue = rbTree.delete(6)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(5, "b"), Pair(10, "a"), Pair(15, "c"), Pair(20, "e")),
                inorderedTraversalTree
            )
            assertEquals("d", returnValue)
        }

        @Test
        fun `fix after deletion case 3 right child red`() {
            rbTree.insert(15, "C")
            rbTree.insert(10, "A")
            rbTree.insert(20, "E")
            rbTree.insert(22, "F")

            // for case 3 - insert sibling and ensure its child is red
            rbTree.insert(23, "K")

            // deletion to trigger fixAfterDeletion with Case 3 conditions
            rbTree.delete(10)

            val currentSibling = rbTree.root?.rightChild
            assertEquals(null, currentSibling?.rightChild?.color)
            assertEquals(RBNode.Color.Black, currentSibling?.color)
        }

        @Test
        fun `fix after deletion case 3 left child red`() {
            rbTree.insert(15, "C")
            rbTree.insert(10, "A")
            rbTree.insert(20, "E")
            rbTree.insert(18, "D")

            // for case 3 - insert sibling and ensure its child is red
            rbTree.insert(17, "Z")
            // deletion to trigger fixAfterDeletion with Case 3 conditions
            rbTree.delete(10)

            val currentSibling = rbTree.root?.rightChild
            assertEquals(RBNode.Color.Black, currentSibling?.color)
        }

        @Test
        fun `fix after deletion fourth case`() {
            rbTree.insert(15, "c")
            rbTree.insert(10, "a")
            rbTree.insert(20, "e")
            rbTree.insert(5, "b")
            rbTree.insert(4, "f")

            val returnValue = rbTree.delete(4)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(5, "b"), Pair(10, "a"), Pair(15, "c"), Pair(20, "e")),
                inorderedTraversalTree
            )
            assertEquals("f", returnValue)
        }

        @Test
        fun `delete right-red sibling`() {
            rbTree.insert(20, "pink")
            rbTree.insert(30, "white")
            rbTree.insert(15, "red")
            rbTree.insert(10, "brown")
            rbTree.insert(5, "gold")

            val returnValue = rbTree.delete(30)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(5, "gold"), Pair(10, "brown"), Pair(15, "red"), Pair(20, "pink")),
                inorderedTraversalTree
            )
            assertEquals("white", returnValue)
        }

        @Test
        fun `root left rotate`() {
            rbTree.insert(20, "pink")
            rbTree.insert(30, "white")
            rbTree.insert(15, "red")
            rbTree.insert(10, "brown")
            rbTree.insert(5, "gold")

            val returnValue = rbTree.delete(20)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(5, "gold"), Pair(10, "brown"), Pair(15, "red"), Pair(30, "white")),
                inorderedTraversalTree
            )
            assertEquals("pink", returnValue)
        }

        @Test
        fun `root right rotate`() {
            rbTree.insert(20, "pink")
            rbTree.insert(30, "white")
            rbTree.insert(15, "red")
            rbTree.insert(10, "brown")
            rbTree.insert(5, "gold")

            val returnValue = rbTree.delete(30)
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(
                listOf(Pair(5, "gold"), Pair(10, "brown"), Pair(15, "red"), Pair(20, "pink")),
                inorderedTraversalTree
            )
            assertEquals("white", returnValue)
        }
    }

    @Nested
    inner class AuxiliaryTests {
        @Test
        fun `insert and balance, color check 1`() {
            rbTree.insert(10, "A")
            rbTree.insert(5, "B")
            rbTree.insert(15, "C")
            rbTree.insert(3, "D")
            rbTree.insert(7, "E")

            assertEquals(RBNode.Color.Black, rbTree.root?.color)
            assertEquals(RBNode.Color.Black, rbTree.root?.leftChild?.color)
            assertEquals(RBNode.Color.Black, rbTree.root?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.leftChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.leftChild?.rightChild?.color)
        }

        @Test
        fun `insert and balance, color check 2`() {
            rbTree.insert(10, "A")
            rbTree.insert(5, "B")
            rbTree.insert(15, "C")
            rbTree.insert(3, "D")
            rbTree.insert(7, "E")
            rbTree.insert(13, "F")
            rbTree.insert(17, "G")
            rbTree.insert(2, "H")
            rbTree.insert(4, "I")
            rbTree.insert(6, "J")
            rbTree.insert(8, "K")
            rbTree.insert(14, "L")
            rbTree.insert(16, "M")
            rbTree.insert(18, "N")

            assertEquals(RBNode.Color.Black, rbTree.root?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.rightChild?.color)
            assertEquals(RBNode.Color.Black, rbTree.root?.leftChild?.leftChild?.color)
            assertEquals(RBNode.Color.Black, rbTree.root?.leftChild?.rightChild?.color)
            assertEquals(RBNode.Color.Black, rbTree.root?.rightChild?.leftChild?.color)
            assertEquals(RBNode.Color.Black, rbTree.root?.rightChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.leftChild?.leftChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.leftChild?.leftChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.leftChild?.rightChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.leftChild?.rightChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.rightChild?.leftChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.rightChild?.rightChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbTree.root?.rightChild?.rightChild?.rightChild?.color)
        }
    }
}
