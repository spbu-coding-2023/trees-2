package tsl.trees

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tsl.nodes.RBNode
import kotlin.test.assertEquals


class RBTreeTest {
    private lateinit var rbTree: RBTree<Int, String>

    @BeforeEach
    fun setup() {
        rbTree = RBTree()
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
    inner class SearchTests {

        @Test
        fun `search node that isn't in the rbTree`() {
            rbTree.insert(15, "hihi")
            rbTree.insert(10, "haha")
            rbTree.insert(5, "bugaga")

            val returnValue = rbTree.search(7)
            assertEquals(null, returnValue)
        }

        @Test
        fun `search node that is in rbTree`() {
            rbTree.insert(15, "hihi")
            rbTree.insert(10, "haha")
            rbTree.insert(5, "bugaga")

            val returnValue = rbTree.search(5)
            assertEquals("bugaga", returnValue)
        }
    }

    @Nested
    inner class DeleteTests {

        @Test
        fun `successful delete (return value)`() {
            rbTree.insert(30, "pink")
            rbTree.insert(20, "white")
            rbTree.insert(40, "red")

            val returnValue = rbTree.delete(40)
            assertEquals("red", returnValue)
        }

        @Test
        fun `unsuccessful delete (return value)`() {
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
        fun `right-right case`() {
            // s is right child of its parent and both children of s are red
            rbTree.insert(30, "pink")
            rbTree.insert(18, "white")
            rbTree.insert(40, "red")
            rbTree.insert(45, "yellow")

            val returnValue = rbTree.delete(40)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals("red", returnValue)
            assertEquals(listOf(Pair(18, "white"), Pair(30, "pink"), Pair(45, "yellow")), inorderedTraversalTree)
        }

        @Test
        fun `left-left case`() {
            // s is left child of its parent and both children of s are red
            rbTree.insert(30, "pink")
            rbTree.insert(40, "white")
            rbTree.insert(18, "red")
            rbTree.insert(10, "yellow")

            val returnValue = rbTree.delete(18)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(10, "yellow"), Pair(30, "pink"), Pair(40, "white")), inorderedTraversalTree)
            assertEquals("red", returnValue)
        }

        @Test
        fun `right-left case`() {
            // s is right child of its parent and r is left child of s
            rbTree.insert(30, "pink")
            rbTree.insert(40, "white")
            rbTree.insert(18, "red")
            rbTree.insert(35, "yellow")

            val returnValue = rbTree.delete(40)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(18, "red"), Pair(30, "pink"), Pair(35, "yellow")), inorderedTraversalTree)
            assertEquals("white", returnValue)
        }

        @Test
        fun `left-right case`() {
            rbTree.insert(30, "pink")
            rbTree.insert(40, "white")
            rbTree.insert(18, "red")
            rbTree.insert(20, "yellow")

            val returnValue = rbTree.delete(18)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(20, "yellow"), Pair(30, "pink"), Pair(40, "white")), inorderedTraversalTree)
            assertEquals("red", returnValue)

        }

        @Test
        fun `left-red sibling`() {
            rbTree.insert(20, "pink")
            rbTree.insert(30, "white")
            rbTree.insert(10, "red")
            rbTree.insert(25, "brown")
            rbTree.insert(35, "gold")

            val returnValue = rbTree.delete(10)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(20, "pink"), Pair(25, "brown"), Pair(30, "white"), Pair(35, "gold")), inorderedTraversalTree)
            assertEquals("red", returnValue)
        }

        @Test
        fun `remove root - sole node in tree`(){
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
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf( Pair(10, "red"), Pair(20, "pink"), Pair(25, "brown"), Pair(35, "gold")), inorderedTraversalTree)
            assertEquals("white", returnValue)
        }

        @Test
        fun `fix after deletion second case`() {
            rbTree.insert(15, "c")
            rbTree.insert(10, "a")
            rbTree.insert(20, "e")
            rbTree.insert(5, "b")
            rbTree.insert(6, "d")

            val returnValue = rbTree.delete(6)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(5, "b"), Pair(10, "a"), Pair(15, "c"), Pair(20, "e")), inorderedTraversalTree)
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
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(5, "b"), Pair(10, "a"), Pair(15, "c"), Pair(20, "e")), inorderedTraversalTree)
            assertEquals("f", returnValue)
        }


        @Test
        fun `right-red sibling`() {
            rbTree.insert(20, "pink")
            rbTree.insert(30, "white")
            rbTree.insert(15, "red")
            rbTree.insert(10, "brown")
            rbTree.insert(5, "gold")

            val returnValue = rbTree.delete(30)
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(5, "gold"), Pair(10, "brown"), Pair(15, "red"), Pair(20, "pink")), inorderedTraversalTree)
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
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)
            }
            assertEquals(listOf(Pair(5, "gold"), Pair(10, "brown"), Pair(15, "red"), Pair(30, "white")), inorderedTraversalTree)
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
            val inorderedTraversalTree: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in rbTree) {
                inorderedTraversalTree += Pair(key, value)

            }
            assertEquals(listOf(Pair(5, "gold"), Pair(10, "brown"), Pair(15, "red"), Pair(20, "pink")), inorderedTraversalTree)
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