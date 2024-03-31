package tsl.trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * This class tests methods implemented in the AbstractTreeClass
 * that are common for all its inheritors (BSTree, AVLTree, RBTree).
 * BSTree is used in tests as an instance of the AbstractTreeClass.
 */

class AbstractBinaryTreeTest {
    private lateinit var tree: BSTree<Int, String>

    @BeforeEach
    fun setup() {
        tree = BSTree()
        tree.insert(30, "Danya")
        tree.insert(10, "Misha")
        tree.insert(50, "Karim")
        tree.insert(20, "Moldova")
        tree.insert(40, "Tatarstan")
    }

    @Nested
    inner class SearchTests {

        @Test
        fun `search of non-existing key should return null`() {
            val expectedValue = null
            val actualValue = tree.search(100)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key should return associated value`() {
            val expectedValue = "Danya"
            val actualValue = tree.search(30)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is to the left of the root node should return the corresponding value`() {
            val expectedValue = "Moldova"
            val actualValue = tree.search(20)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is to the right of the root node should return the corresponding value`() {
            val expectedValue = "Tatarstan"
            val actualValue = tree.search(40)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `empty tree should not change after search`() {
            tree.clear()
            tree.search(1)

            val expectedRoot = null
            val actualRoot = tree.root

            assertEquals(expectedRoot, actualRoot)
        }

        @Test
        fun `non-empty tree should not change after search`() {
            tree.search(40)

            val expectedStructure =
                listOf(
                    Pair(10, "Misha"), Pair(20, "Moldova"), Pair(30, "Danya"),
                    Pair(40, "Tatarstan"), Pair(50, "Karim")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class ClearTests {

        @Test
        fun `clear of an empty tree should return null`() {
            val emptyTree = BSTree<Int, String>()

            val expectedValue = null
            val actualValue = emptyTree.clear()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `clear of a non-empty tree should return value of its root node`() {
            val expectedValue = "Danya"
            val actualValue = tree.clear()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `empty tree should be empty after clear`() {
            val emptyTree = BSTree<Int, String>()
            emptyTree.clear()

            val expectedRoot = null
            val actualRoot = emptyTree.root

            assertEquals(expectedRoot, actualRoot)
        }

        @Test
        fun `not empty tree should be empty after clear`() {
            tree.clear()

            val expectedRoot = null
            val actualRoot = tree.root

            assertEquals(expectedRoot, actualRoot)
        }
    }


    @Nested
    inner class IsEmptyTests {

        @Test
        fun `isEmpty should return true if the tree is empty`() {
            tree.clear()

            val expectedValue = true
            val actualValue = tree.isEmpty()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `isEmpty should return false if the tree is not empty`() {
            val expectedValue = false
            val actualValue = tree.isEmpty()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `isEmpty should not change an empty tree`() {
            tree.clear()
            tree.isEmpty()

            val expectedRoot = null
            val actualRoot = tree.root

            assertEquals(expectedRoot, actualRoot)
        }

        @Test
        fun `isEmpty should not change a non-empty tree`() {
            tree.isEmpty()

            val expectedStructure =
                listOf(
                    Pair(10, "Misha"), Pair(20, "Moldova"), Pair(30, "Danya"),
                    Pair(40, "Tatarstan"), Pair(50, "Karim")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class GetMinKeyTests {

        @Test
        fun `getMinKey should return null if the tree is empty`() {
            tree.clear()

            val expectedKey = null
            val actualKey = tree.getMinKey()

            assertEquals(expectedKey, actualKey)
        }

        @Test
        fun `getMinKey should return min key if the tree is not empty`() {
            val expectedKey = 10
            val actualKey = tree.getMinKey()

            assertEquals(expectedKey, actualKey)
        }

        @Test
        fun `getMinKey should not change an empty tree`() {
            tree.clear()
            tree.getMinKey()

            val expectedRoot = null
            val actualRoot = tree.root

            assertEquals(expectedRoot, actualRoot)
        }

        @Test
        fun `getMinKey should not change a non-empty tree`() {
            tree.getMinKey()

            val expectedStructure =
                listOf(
                    Pair(10, "Misha"), Pair(20, "Moldova"), Pair(30, "Danya"),
                    Pair(40, "Tatarstan"), Pair(50, "Karim")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class GetMaxKeyTests {

        @Test
        fun `getMaxKey should return null if the tree is empty`() {
            tree.clear()

            val expectedKey = null
            val actualKey = tree.getMaxKey()

            assertEquals(expectedKey, actualKey)
        }

        @Test
        fun `getMaxKey should return max key if the tree is not empty`() {
            val expectedKey = 50
            val actualKey = tree.getMaxKey()

            assertEquals(expectedKey, actualKey)
        }

        @Test
        fun `getMaxKey should not change an empty tree`() {
            tree.clear()
            tree.getMaxKey()

            val expectedRoot = null
            val actualRoot = tree.root

            assertEquals(expectedRoot, actualRoot)
        }

        @Test
        fun `getMaxKey should not change a non-empty tree`() {
            tree.getMaxKey()

            val expectedStructure =
                listOf(
                    Pair(10, "Misha"), Pair(20, "Moldova"), Pair(30, "Danya"),
                    Pair(40, "Tatarstan"), Pair(50, "Karim")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class IteratorTests {

        @Test
        fun `hasNext should return false right after it traversed all nodes in a non-empty tree`() {
            val treeIterator = tree.iterator()

            var count = 0
            while (treeIterator.hasNext()) {
                treeIterator.next()
                count++
            }

            val expectedQuantity = 5
            val actualQuantity = count

            assertEquals(expectedQuantity, actualQuantity)
        }

        @Test
        fun `hasNext should always return false in an empty tree`() {
            tree.clear()
            val treeIterator = tree.iterator()

            var count = 0
            while (treeIterator.hasNext()) {
                treeIterator.next()
                count++
            }

            val expectedQuantity = 0
            val actualQuantity = count

            assertEquals(expectedQuantity, actualQuantity)
        }

        @Test
        fun `next method should return pairs with bigger keys than it has returned before`() {
            val treeIterator = tree.iterator()

            var everyNextKeyIsBigger = true

            var currentKey = treeIterator.next().first
            var previousKey = currentKey
            var iteration = 0
            while (treeIterator.hasNext()) {
                iteration++
                currentKey = treeIterator.next().first
                if (currentKey <= previousKey) {
                    everyNextKeyIsBigger = false
                    break
                }
                previousKey = currentKey
            }

            assertEquals(true, everyNextKeyIsBigger)
        }

        @Test
        fun `iterator should not change an empty tree`() {
            tree.clear()
            val treeIterator = tree.iterator()

            while (treeIterator.hasNext()) treeIterator.next()

            val expectedRoot = null
            val actualRoot = tree.root

            assertEquals(expectedRoot, actualRoot)
        }

        @Test
        fun `iterator should not change a not empty tree`() {
            val treeIterator = tree.iterator()

            while (treeIterator.hasNext()) treeIterator.next()

            val expectedStructure =
                listOf(
                    Pair(10, "Misha"), Pair(20, "Moldova"), Pair(30, "Danya"),
                    Pair(40, "Tatarstan"), Pair(50, "Karim")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }
}
