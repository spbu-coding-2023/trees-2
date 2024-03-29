package tsl.trees

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tsl.nodes.RBNode
import kotlin.test.assertEquals


class RBTreeTest {

    @Nested
    inner class InsertTests() {

        @Test
        fun `insert if the node with this key doesn't exist`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(15, "i")
            rbtree.insert(10, "am")
            rbtree.insert(5, "really")
            val returnValue = rbtree.insert(7, "wordless")
            Assertions.assertEquals(null, returnValue)
        }

        @Test
        fun `insert if the node with this key exists`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(15, "i")
            rbtree.insert(10, "am")
            rbtree.insert(5, "really")
            val returnValue = rbtree.insert(5, "wordless")
            Assertions.assertEquals("really", returnValue)
        }

        @Test
        fun `insert left child`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(17, "legend")
            rbtree.insert(27, "club")
            rbtree.insert(49, "poliklinika")
        }

        @Test
        fun `insert right child`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(17, "legend")
            rbtree.insert(27, "club")
            rbtree.insert(49, "poliklinika")
        }
    }

    @Nested
    inner class SearchTests() {

        @Test
        fun `search node that isn't in the rbtree`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(15, "hihi")
            rbtree.insert(10, "haha")
            rbtree.insert(5, "bugaga")
            val returnValue = rbtree.search(7)
            Assertions.assertEquals(null, returnValue)
        }

        @Test
        fun `search node that is in rbtree`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(15, "hihi")
            rbtree.insert(10, "haha")
            rbtree.insert(5, "bugaga")
            val returnValue = rbtree.search(5)
            Assertions.assertEquals("bugaga", returnValue)
        }
    }

    @Nested
    inner class DeleteTests() {

        @Test
        fun `successful delete (return value)`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(30, "pink")
            rbtree.insert(20, "white")
            rbtree.insert(40, "red")
            val returnValue = rbtree.delete(40)
            Assertions.assertEquals("red", returnValue)
        }

        @Test
        fun `unsuccessful delete (return value)`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(30, "pink")
            rbtree.insert(20, "white")
            rbtree.insert(40, "red")
            val returnValue = rbtree.delete(50)
            Assertions.assertEquals(null, returnValue)
        }

        @Test
        fun `delete if sibling and its left-child are black`() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(30, "pink")
            rbtree.insert(20, "white")
            rbtree.insert(40, "red")
            rbtree.insert(50, "yellow")
            val returnValue = rbtree.delete(20)
            Assertions.assertEquals("white", returnValue)
        }

//        @Test
//        fun `right-right case`() {
//            // s is right child of its parent and both children of s are red
//            val rbtree = RBTree<Int, String>()
//            rbtree.insert(30, "pink")
//            rbtree.insert(18, "white")
//            rbtree.insert(40, "red")
//            rbtree.insert(45, "yellow")
//            val returnValue = rbtree.delete(40)
//            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
//            for ((key, value) in rbtree) {
//                inorderedTraversalTree += Pair(key, value)
//            }
//        }

        @Test
        fun `left-left case`() {
            // s is left child of its parent and both children of s are red
            val rbtree = RBTree<Int, String>()
            rbtree.insert(30, "pink")
            rbtree.insert(40, "white")
            rbtree.insert(18, "red")
            rbtree.insert(10, "yellow")
            val returnValue = rbtree.delete(18)
            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
            for ((key, value) in rbtree) {
                inorderedTraversalTree += Pair(key, value)
                println(key)
            }
            Assertions.assertEquals(listOf(Pair(10, "yellow"), Pair(30, "pink"), Pair(40, "white")), inorderedTraversalTree)
            Assertions.assertEquals("red", returnValue)
        }

        @Test
        fun `right-left case`() {
            // s is right child of its parent and r is left child of s
            val rbtree = RBTree<Int, String>()
            rbtree.insert(30, "pink")
            rbtree.insert(40, "white")
            rbtree.insert(18, "red")
            rbtree.insert(35, "yellow")
            val returnValue = rbtree.delete(40)
            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
            for ((key, value) in rbtree) {
                inorderedTraversalTree += Pair(key, value)
                println(key)
            }
            Assertions.assertEquals(listOf(Pair(18, "red"), Pair(30, "pink"), Pair(35, "yellow")), inorderedTraversalTree)
            Assertions.assertEquals("white", returnValue)
        }

        @Test
        fun `left-right case`() {
            // s is left child of its parent and r is right child
            val rbtree = RBTree<Int, String>()
            rbtree.insert(30, "pink")
            rbtree.insert(40, "white")
            rbtree.insert(18, "red")
            rbtree.insert(20, "yellow")
            val returnValue = rbtree.delete(18)
            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
            for ((key, value) in rbtree) {
                inorderedTraversalTree += Pair(key, value)
                println(key)
            }
            Assertions.assertEquals(listOf(Pair(20, "yellow"), Pair(30, "pink"), Pair(40, "white")), inorderedTraversalTree)
            Assertions.assertEquals("red", returnValue)

        }
        //        fun `sibling is black and both children are black`() {
//            val rbtree = RBTree<Int, String>()
//            rbtree.insert(30, "pink")
//            rbtree.insert(40, "white")
//            rbtree.insert(18, "red")
//            rbtree.delete(18)
//            // TODO
//        }
//
        @Test
        fun `left-red sibling`() {
            // s is left child of its parent
            val rbtree = RBTree<Int, String>()
            rbtree.insert(20, "pink")
            rbtree.insert(30, "white")
            rbtree.insert(10, "red")
            rbtree.insert(25, "brown")
            rbtree.insert(35, "gold")
            val returnValue = rbtree.delete(10)
            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
            for ((key, value) in rbtree) {
                inorderedTraversalTree += Pair(key, value)
                println(key)
            }
            Assertions.assertEquals(listOf(Pair(20, "pink"), Pair(25, "brown"), Pair(30, "white"), Pair(35, "gold")), inorderedTraversalTree)
            Assertions.assertEquals("red", returnValue)
        }

        @Test
        fun `remove root`(){
            val rbtree = RBTree<Int, String>()
            rbtree.insert(10, "hihi haha")
            val returnValue = rbtree.delete(10)
            assertEquals(null, returnValue)
        }

        @Test
        fun `delete node with two children`() {
            // s is left child of its parent
            val rbtree = RBTree<Int, String>()
            rbtree.insert(20, "pink")
            rbtree.insert(30, "white")
            rbtree.insert(10, "red")
            rbtree.insert(25, "brown")
            rbtree.insert(35, "gold")
            val returnValue = rbtree.delete(30)
            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
            for ((key, value) in rbtree) {
                inorderedTraversalTree += Pair(key, value)
                println(key)
            }
            Assertions.assertEquals(listOf( Pair(10, "red"), Pair(20, "pink"), Pair(25, "brown"), Pair(35, "gold")), inorderedTraversalTree)
            Assertions.assertEquals("white", returnValue)
        }

        @Test
        fun testFixAfterDeletionCase3LeftChildRed() {
            val rbTree = RBTree<Int, String>()
            rbTree.insert(15, "C")
            rbTree.insert(10, "A")
            rbTree.insert(20, "E")
            rbTree.insert(18, "D")

            // for case 3 - insert sibling and ensure its child is red
            rbTree.insert(17, "Z")

            // deletion to trigger fixAfterDeletion with Case 3 conditions
            rbTree.delete(10)

            val currentSibling = rbTree.root?.rightChild

            assertEquals(RBNode.Color.Black, currentSibling?.leftChild?.color)
            assertEquals(RBNode.Color.Black, currentSibling?.color)
        }

        @Test
        fun `fix after deletion case 3 right child red`() {
            val rbTree = RBTree<Int, String>()
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

//        @Test
//        fun testFixAfterDeletionSecondCase() {
//            val rbtree = RBTree<Int, String>()
//            rbtree.insert(15, "c")
//            rbtree.insert(10, "a")
//            rbtree.insert(20, "e")
//            rbtree.insert(5, "b")
//            rbtree.insert(6, "d")
//
//            val returnValue = rbtree.delete(6)
//            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
//            for ((key, value) in rbtree) {
//                inorderedTraversalTree += Pair(key, value)
//                println(key)
//            }
//            Assertions.assertEquals(listOf(Pair(5, "b"),  Pair(10, "a"), Pair(15, "c"), Pair(20, "e")), inorderedTraversalTree)
//            Assertions.assertEquals("d", returnValue)
//        }



//        @Test
//        fun testFixAfterDeletionFourthCase() {
//            val rbtree = RBTree<Int, String>()
//            rbtree.insert(15, "c")
//            rbtree.insert(10, "a")
//            rbtree.insert(20, "e")
//            rbtree.insert(5, "b")
//            rbtree.insert(6, "d")
//            rbtree.insert(4, "f")
//
//            val returnValue = rbtree.delete(4)
//            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
//            for ((key, value) in rbtree) {
//                inorderedTraversalTree += Pair(key, value)
//                println(key)
//            }
//            Assertions.assertEquals(listOf(Pair(5, "b"), Pair(6, "d"), Pair(10, "a"), Pair(15, "c"), Pair(20, "e")), inorderedTraversalTree)
//            Assertions.assertEquals("f", returnValue)
//        }



        // fix
//        @Test
//        fun `right-red sibling`() {
//            // s is right child of its parent
//            val rbtree = RBTree<Int, String>()
//            rbtree.insert(20, "pink")
//            rbtree.insert(30, "white")
//            rbtree.insert(15, "red")
//            rbtree.insert(10, "brown")
//            rbtree.insert(5, "gold")
//            val returnValue = rbtree.delete(30)
//            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
//            for ((key, value) in rbtree) {
//                inorderedTraversalTree += Pair(key, value)
//            }
//            Assertions.assertEquals(listOf(Pair(20, "pink"), Pair(25, "brown"), Pair(30, "white"), Pair(35, "gold")), inorderedTraversalTree)
//            Assertions.assertEquals("red", returnValue)
//        }

//        // fix
//        @Test
//        fun `root left rotate`() {
//            val rbtree = RBTree<Int, String>()
//            rbtree.insert(20, "pink")
//            rbtree.insert(30, "white")
//            rbtree.insert(15, "red")
//            rbtree.insert(10, "brown")
//            rbtree.insert(5, "gold")
//            val returnValue = rbtree.delete(20)
//            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
//            for ((key, value) in rbtree) {
//                inorderedTraversalTree += Pair(key, value)
//                println(key)
//            }
//            Assertions.assertEquals(listOf(Pair(20, "pink"), Pair(25, "brown"), Pair(30, "white"), Pair(35, "gold")), inorderedTraversalTree)
//            Assertions.assertEquals("red", returnValue)
//        }

//        // fix
//        @Test
//        fun `root right rotate`() {
//            val rbtree = RBTree<Int, String>()
//            rbtree.insert(20, "pink")
//            rbtree.insert(30, "white")
//            rbtree.insert(15, "red")
//            rbtree.insert(10, "brown")
//            rbtree.insert(5, "gold")
//            val returnValue = rbtree.delete(30)
//            var inorderedTraversalTree: List<Pair<Int, String>> = listOf()
//            for ((key, value) in rbtree) {
//                inorderedTraversalTree += Pair(key, value)
//                println(key)
//            }
//            Assertions.assertEquals(listOf(Pair(20, "pink"), Pair(25, "brown"), Pair(30, "white"), Pair(35, "gold")), inorderedTraversalTree)
//            Assertions.assertEquals("red", returnValue)
//        }
    }

    @Nested
    inner class BalancerTests() {

    }

    @Nested
    inner class AuxiliaryTests() {
        @Test
        fun testInsertAndBalance() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(10, "A")
            rbtree.insert(5, "B")
            rbtree.insert(15, "C")
            rbtree.insert(3, "D")
            rbtree.insert(7, "E")

            assertEquals(RBNode.Color.Black, rbtree.root?.color)
            assertEquals(RBNode.Color.Black, rbtree.root?.leftChild?.color)
            assertEquals(RBNode.Color.Black, rbtree.root?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.leftChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.leftChild?.rightChild?.color)
        }

        @Test
        fun testRBTreeBalancing() {
            val rbtree = RBTree<Int, String>()
            rbtree.insert(10, "A")
            rbtree.insert(5, "B")
            rbtree.insert(15, "C")
            rbtree.insert(3, "D")
            rbtree.insert(7, "E")
            rbtree.insert(13, "F")
            rbtree.insert(17, "G")
            rbtree.insert(2, "H")
            rbtree.insert(4, "I")
            rbtree.insert(6, "J")
            rbtree.insert(8, "K")
            rbtree.insert(14, "L")
            rbtree.insert(16, "M")
            rbtree.insert(18, "N")

            assertEquals(RBNode.Color.Black, rbtree.root?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.rightChild?.color)
            assertEquals(RBNode.Color.Black, rbtree.root?.leftChild?.leftChild?.color)
            assertEquals(RBNode.Color.Black, rbtree.root?.leftChild?.rightChild?.color)
            assertEquals(RBNode.Color.Black, rbtree.root?.rightChild?.leftChild?.color)
            assertEquals(RBNode.Color.Black, rbtree.root?.rightChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.leftChild?.leftChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.leftChild?.leftChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.leftChild?.rightChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.leftChild?.rightChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.rightChild?.leftChild?.rightChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.rightChild?.rightChild?.leftChild?.color)
            assertEquals(RBNode.Color.Red, rbtree.root?.rightChild?.rightChild?.rightChild?.color)
        }

    }
}