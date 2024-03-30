package tsl.trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class BSTreeTest {
    private lateinit var tree: BSTree<Int, String>

    @BeforeEach
    fun setup() {
        tree = BSTree()
        tree.insert(30, "java")
        tree.insert(40, "gnomik")
        tree.insert(20, "kotlin")
        tree.insert(10, "kotik")
    }

    @Nested
    inner class InsertTests {

        @Test
        fun `insert of the key that isn't in the tree should return null`() {
            val expectedValue = null
            val actualValue = tree.insert(50, "pesik")

            assertEquals(expectedValue, actualValue)
        }
        @Test
        fun `insert of existing key should replace old value and return it`() {
            val expectedReturnValue = "kotik"
            val actualReturnValue = tree.insert(10, "pesik")

            val expectedNewValue = "pesik"
            val actualNewValue = tree.search(10)


            assertEquals(expectedReturnValue, actualReturnValue, "wrong value returned")
            assertEquals(expectedNewValue, actualNewValue, "value didn't change")
        }

        @Test
        fun `node with the smallest key should be inserted at the most left position`() {
            tree.insert(5, "pesik")

            val expectedStructure = listOf(Pair(5, "pesik"), Pair(10, "kotik"), Pair(20, "kotlin"),
                                           Pair(30, "java"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `node with the biggest key should be inserted at the most right position`() {
            tree.insert(50, "pesik")

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"), Pair(30, "java"),
                                           Pair(40, "gnomik"), Pair(5, "pesik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `node with random key should be inserted at correct position`() {
            tree.insert(25, "pesik")

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"), Pair(25, "pesik"),
                                           Pair(30, "java"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }
    @Nested
    inner class SearchTests {

        @Test
        fun `search of non existing key should return null`() {
            val expectedValue = null
            val actualValue = tree.search(100)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is in the root node should return the corresponding value`() {
            val expectedValue = "java"
            val actualValue = tree.search(30)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is to the left of the root node should return the corresponding value`() {
            val expectedValue = "kotlin"
            val actualValue = tree.search(20)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is to the right of the root node should return the corresponding value`() {
            val expectedValue = "gnomik"
            val actualValue = tree.search(40)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search should not change an empty tree`() {
            tree.clear()
            tree.search(42069)

            val expectedRoot = null
            val actualRoot = tree.root

            assertEquals(expectedRoot, actualRoot)
        }

        @Test
        fun `search should not change a non-empty tree`() {
            tree.search(40)

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"),
                                           Pair(30, "java"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class DeleteTests {

        @Test
        fun `delete of the non-existing key should return null`() {
            val expectedValue = null
            val actualValue = tree.delete(42069)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `delete of existing key should return the corresponding deleted value`() {
            val expectedValue = "gnomik"
            val actualValue = tree.delete(40)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `a key should be deleted after deletion`() {
            tree.delete(30)

            val expectedValue = null
            val actualValue = tree.search(30)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `delete of a node without children shouldn't change tree structure`() {
            tree. delete(10)

            val expectedStructure = listOf(Pair(20, "kotlin"), Pair(30, "java"), Pair(40, "gnomik"))
            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `delete of a node with a left child should replace it with it's child`() {
            tree. delete(20)

            val expectedStructure = listOf(Pair(10, "kotlin"), Pair(30, "java"), Pair(40, "gnomik"))
            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `delete of a node with a right child should replace it with it's child`() {
            tree.insert(50, "pesik")
            tree. delete(40)

            val expectedStructure = listOf(Pair(10, "kotlin"), Pair(20, "kotlin"),
                                           Pair(30, "java"), Pair(50, "pesik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `delete of a node with two children should replace it with smallest node in the right subtree`() {
            tree.insert(35, "pesik")
            tree. delete(30)

            val expectedStructure = listOf(Pair(10, "kotlin"), Pair(20, "kotlin"),
                                           Pair(35, "pesik"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class ClearTests {

        @Test
        fun `clear of a non-empty tree should return value of its root node`() {
            val expectedValue = "java"
            val actualValue = tree.clear()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `clear of an empty tree should return null`() {
            tree.clear()

            val expectedValue = null
            val actualValue = tree.clear()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `clear of an empty tree should remain it empty`() {
            val emptyTree = BSTree<Int, String>()
            emptyTree.clear()

            val expectedValue = null
            val actualValue = emptyTree.root

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `clear of a non-empty tree should make it empty`() {
            tree.clear()

            val expectedValue = null
            val actualValue = tree.root

            assertEquals(expectedValue, actualValue)
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

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"),
                                           Pair(30, "java"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class GetMinKeyTests {

        @Test
        fun `getMinKey should return null if the tree is empty`() {
            tree.clear()

            val expectedValue = null
            val actualValue = tree.getMinKey()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `getMinKey should return min key if the tree is not empty`() {
            val expectedValue = 10
            val actualValue = tree.getMinKey()

            assertEquals(expectedValue, actualValue)
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

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"),
                                           Pair(30, "java"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class GetMaxKeyTests {

        @Test
        fun `getMaxKey should return null if the tree is empty`() {
            tree.clear()

            val expectedValue = null
            val actualValue = tree.getMaxKey()

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `getMaxKey should return min key if the tree is not empty`() {
            val expectedValue = 10
            val actualValue = tree.getMaxKey()

            assertEquals(expectedValue, actualValue)
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

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"),
                Pair(30, "java"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for (pair in tree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class Iteratortests {

    }
}
