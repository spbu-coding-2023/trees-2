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
    }

    fun defaultFill(tree: BSTree<Int, String>) {
        tree.insert(30, "java")
        tree.insert(40, "gnomik")
        tree.insert(20, "kotlin")
        tree.insert(10, "kotik")
    }

    @Nested
    inner class InsertTests {

        @Test
        fun `insert of the key that isn't in the tree should return null`() {
            defaultFill(tree)

            val expectedValue = null
            val actualValue = tree.insert(50, "pesik")

            assertEquals(expectedValue, actualValue)
        }
        @Test
        fun `insert of existing key should replace old value and return it`() {
            defaultFill(tree)

            val expectedReturnValue = "kotik"
            val actualReturnValue = tree.insert(10, "pesik")

            val expectedNewValue = "pesik"
            val actualNewValue = tree.search(10)


            assertEquals(expectedReturnValue, actualReturnValue, "wrong value returned")
            assertEquals(expectedNewValue, actualNewValue, "value didn't change")
        }

        @Test
        fun `node with the smallest key should be inserted at the most left position`() {
            defaultFill(tree)

            tree.insert(5, "pesik")

            val expectedStructure = listOf(Pair(5, "pesik"), Pair(10, "kotik"), Pair(20, "kotlin"),
                                           Pair(30, "java"), Pair(40, "gnomik")) // in the sequence of inorder traversal

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in tree) actualStructure.add(Pair(key, value))

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `node with the biggest key should be inserted at the most right position`() {
            defaultFill(tree)

            tree.insert(50, "pesik")

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"), Pair(30, "java"),
                                           Pair(40, "gnomik"), Pair(5, "pesik")) // in the sequence of inorder traversal

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in tree) actualStructure.add(Pair(key, value))

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `node with random key should be inserted at correct position`() {
            defaultFill(tree)

            tree.insert(25, "pesik")

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"), Pair(25, "pesik"),
                                           Pair(30, "java"), Pair(40, "gnomik")) // in the sequence of inorder traversal

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in tree) actualStructure.add(Pair(key, value))

            assertEquals(expectedStructure, actualStructure)
        }
    }
    @Nested
    inner class SearchTests {

        @Test
        fun `search of non existing key should return null`() {
            defaultFill(tree)

            val expectedValue = null
            val actualValue = tree.search(100)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is in the root node should return the corresponding value`() {
            defaultFill(tree)

            val expectedValue = "java"
            val actualValue = tree.search(30)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is to the left of the root node should return the corresponding value`() {
            defaultFill(tree)

            val expectedValue = "kotlin"
            val actualValue = tree.search(20)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search of existing key that is to the right of the root node should return the corresponding value`() {
            defaultFill(tree)

            val expectedValue = "gnomik"
            val actualValue = tree.search(40)

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `search should not change an empty tree`() {
            tree.search(42069)

            val expectedStructure: List<Pair<Int, String>> = listOf()
            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in tree) actualStructure.add(Pair(key, value))

            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `search should not change a non-empty tree`() {
            defaultFill(tree)

            tree.search(40)

            val expectedStructure = listOf(Pair(10, "kotik"), Pair(20, "kotlin"),
                                           Pair(30, "java"), Pair(40, "gnomik"))

            val actualStructure: MutableList<Pair<Int, String>> = mutableListOf()
            for ((key, value) in tree) actualStructure.add(Pair(key, value))

            assertEquals(expectedStructure, actualStructure)
        }
    }

    @Nested
    inner class DeleteTests {

    }

    @Nested
    inner class Iteratortests {

    }
}
