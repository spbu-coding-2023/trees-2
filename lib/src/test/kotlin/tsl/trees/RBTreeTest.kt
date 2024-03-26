package tsl.trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RBTreeTest{
    @Nested
    inner class SearchTests() {
        @Test
        fun `search node that isn't in the tree`() {
            val rbtree  = RBTree<Int, String>()
            rbtree.insert(15, "hihi")
            rbtree.insert(10, "haha")
            rbtree.insert(5, "bugaga")
            val returnValue = rbtree.search(7)
            assertEquals(null, returnValue)
        }

        @Test
        fun `search node that is in tree`() {
            val rbtree  = RBTree<Int, String>()
            rbtree.insert(15, "hihi")
            rbtree.insert(10, "haha")
            rbtree.insert(5, "bugaga")
            val returnValue = rbtree.search(5)
            assertEquals("bugaga", returnValue)
        }
    }

    @Nested
    inner class InsertTests() {
        fun `insert if the node with this key doesn't exist`() {
            val rbtree  = RBTree<Int, String>()
            rbtree.insert(15, "i")
            rbtree.insert(10, "am")
            rbtree.insert(5, "really")
            val returnValue = rbtree.insert(7, "wordless")
            assertEquals(null, returnValue)
        }

        fun `insert if the node with this key exists`() {
            val rbtree  = RBTree<Int, String>()
            rbtree.insert(15, "i")
            rbtree.insert(10, "am")
            rbtree.insert(5, "really")
            val returnValue = rbtree.insert(5, "wordless")
            assertEquals("really", returnValue)
        }

        fun `insert left child`() {
            val rbtree  = RBTree<Int, String>()
            rbtree.insert(17, "legend")
            rbtree.insert(27, "club")
            rbtree.insert(49, "poliklinika")
        }

        fun `insert right child`() {
            val rbtree  = RBTree<Int, String>()
            rbtree.insert(17, "legend")
            rbtree.insert(27, "club")
            rbtree.insert(49, "poliklinika")
        }
    }
    @Nested
    inner class DeleteTests() {

    }

    @Nested
    inner class IteratorTests() {

    }

    @Nested
    inner class AuxiliaryTests() {

    }
}