package tsl.trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AVLTreeTest {
    private lateinit var avlTree: AVLTree<Int, String>

    @BeforeEach
    fun setup() {
        avlTree = AVLTree()
    }

    @Nested
    inner class InsertTests {
        // Common tests

        @Test
        fun `insert of new key should return null`() {
            val expectedValue = null
            val actualValue = avlTree.insert(1, "I am just a freak")

            assertEquals(expectedValue, actualValue)
        }

        @Test
        fun `insert of existing key should replace old value with new value and return old value`() {
            avlTree.insert(1, "But I love you so")

            val expectedReturnValue = "But I love you so"
            val actualReturnValue = avlTree.insert(1, "Please let me go")

            val expectedNewValue = "Please let me go"
            val actualNewValue = avlTree.search(1)

            assertEquals(expectedReturnValue, actualReturnValue, "Returned wrong value")
            assertEquals(expectedNewValue, actualNewValue, "Value wasn't replaced with new value")
        }

        // Small tree tests

        @Test
        fun `insertion of left child triggering right rotation should balance small tree`() {
            avlTree.insert(20, "Take")
            avlTree.insert(15, "To church")
            avlTree.insert(4, "Me")

            val expectedStructure = listOf(Pair(4, "Me"), Pair(15, "To church"), Pair(20, "Take"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 15)
        }

        @Test
        fun `insertion of right child triggering left rotation should balance small tree`() {
            avlTree.insert(4, "A")
            avlTree.insert(15, "B")
            avlTree.insert(20, "C")

            val expectedStructure = listOf(Pair(4, "A"), Pair(15, "B"), Pair(20, "C"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 15)
        }

        // Medium tree tests

        @Test
        fun `insertion of right child triggering left-right rotation should balance medium tree`() {
            avlTree.insert(20, "I")
            avlTree.insert(4, "Swear")
            avlTree.insert(26, "I'll only")
            avlTree.insert(9, "Make")
            avlTree.insert(3, "You")

            avlTree.insert(15, "Cry")

            val expectedStructure =
                listOf(
                    Pair(3, "You"),
                    Pair(4, "Swear"),
                    Pair(9, "Make"),
                    Pair(15, "Cry"),
                    Pair(20, "I"),
                    Pair(26, "I'll only")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 9)
        }

        @Test
        fun `insertion of left child triggering left-right rotation should balance medium tree`() {
            avlTree.insert(20, "E")
            avlTree.insert(4, "B")
            avlTree.insert(26, "F")
            avlTree.insert(3, "A")
            avlTree.insert(9, "D")

            avlTree.insert(8, "C")

            val expectedStructure =
                listOf(
                    Pair(3, "A"),
                    Pair(4, "B"),
                    Pair(8, "C"),
                    Pair(9, "D"),
                    Pair(20, "E"),
                    Pair(26, "F")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 9)
        }

        @Test
        fun `insertion of right child triggering right-left rotation should balance medium tree`() {
            avlTree.insert(20, "B")
            avlTree.insert(4, "A")
            avlTree.insert(26, "E")
            avlTree.insert(24, "C")
            avlTree.insert(30, "F")

            avlTree.insert(25, "D")

            val expectedStructure =
                listOf(
                    Pair(4, "A"),
                    Pair(20, "B"),
                    Pair(24, "C"),
                    Pair(25, "D"),
                    Pair(26, "E"),
                    Pair(30, "F")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 24)
        }

        @Test
        fun `insertion of left child triggering right-left rotation should balance medium tree`() {
            avlTree.insert(20, "B")
            avlTree.insert(4, "A")
            avlTree.insert(26, "E")
            avlTree.insert(24, "D")
            avlTree.insert(30, "F")

            avlTree.insert(21, "C")

            val expectedStructure =
                listOf(
                    Pair(4, "A"),
                    Pair(20, "B"),
                    Pair(21, "C"),
                    Pair(24, "D"),
                    Pair(26, "E"),
                    Pair(30, "F")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 24)
        }

        // Large tree tests

        @Test
        fun `insertion of right child triggering left-right rotation should balance large tree`() {
            avlTree.insert(20, "Master")
            avlTree.insert(4, "Of puppets")
            avlTree.insert(26, "I'm pulling")
            avlTree.insert(3, "Your")
            avlTree.insert(9, "Strings")
            avlTree.insert(21, "Twisting")
            avlTree.insert(30, "Your mind")
            avlTree.insert(2, "And")
            avlTree.insert(7, "Smashing")
            avlTree.insert(11, "Your")

            avlTree.insert(15, "Dreams")

            val expectedStructure =
                listOf(
                    Pair(2, "And"),
                    Pair(3, "Your"),
                    Pair(4, "Of puppets"),
                    Pair(7, "Smashing"),
                    Pair(9, "Strings"),
                    Pair(11, "Your"),
                    Pair(15, "Dreams"),
                    Pair(20, "Master"),
                    Pair(21, "Twisting"),
                    Pair(26, "I'm pulling"),
                    Pair(30, "Your mind")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 9)
        }

        @Test
        fun `insertion of left child triggering left-right rotation should balance large tree`() {
            avlTree.insert(20, "H")
            avlTree.insert(4, "C")
            avlTree.insert(26, "J")
            avlTree.insert(3, "B")
            avlTree.insert(9, "F")
            avlTree.insert(21, "I")
            avlTree.insert(30, "K")
            avlTree.insert(2, "A")
            avlTree.insert(7, "E")
            avlTree.insert(11, "G")

            avlTree.insert(6, "D")

            val expectedStructure =
                listOf(
                    Pair(2, "A"),
                    Pair(3, "B"),
                    Pair(4, "C"),
                    Pair(6, "D"),
                    Pair(7, "E"),
                    Pair(9, "F"),
                    Pair(11, "G"),
                    Pair(20, "H"),
                    Pair(21, "I"),
                    Pair(26, "J"),
                    Pair(30, "K")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 9)
        }

        @Test
        fun `insertion of right child triggering right-left rotation should balance large tree`() {
            avlTree.insert(20, "D")
            avlTree.insert(10, "B")
            avlTree.insert(30, "I")
            avlTree.insert(3, "A")
            avlTree.insert(15, "C")
            avlTree.insert(25, "F")
            avlTree.insert(35, "J")
            avlTree.insert(22, "E")
            avlTree.insert(27, "G")
            avlTree.insert(40, "K")

            avlTree.insert(28, "H")

            val expectedStructure =
                listOf(
                    Pair(3, "A"),
                    Pair(10, "B"),
                    Pair(15, "C"),
                    Pair(20, "D"),
                    Pair(22, "E"),
                    Pair(25, "F"),
                    Pair(27, "G"),
                    Pair(28, "H"),
                    Pair(30, "I"),
                    Pair(35, "J"),
                    Pair(40, "K")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 25)
        }

        @Test
        fun `insertion of left child triggering right-left rotation should balance large tree`() {
            avlTree.insert(20, "D")
            avlTree.insert(10, "B")
            avlTree.insert(30, "I")
            avlTree.insert(3, "A")
            avlTree.insert(15, "C")
            avlTree.insert(25, "F")
            avlTree.insert(35, "J")
            avlTree.insert(22, "E")
            avlTree.insert(27, "H")
            avlTree.insert(40, "K")

            avlTree.insert(26, "G")

            val expectedStructure =
                listOf(
                    Pair(3, "A"),
                    Pair(10, "B"),
                    Pair(15, "C"),
                    Pair(20, "D"),
                    Pair(22, "E"),
                    Pair(25, "F"),
                    Pair(26, "G"),
                    Pair(27, "H"),
                    Pair(30, "I"),
                    Pair(35, "J"),
                    Pair(40, "K")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 25)
        }
    }

    @Nested
    inner class DeleteTests {
        // Common tests

        @Test
        fun `if key doesn't exist deletion should not change tree and return null`() {
            avlTree.insert(10, "A")
            avlTree.insert(20, "B")
            avlTree.insert(30, "C")

            val expectedReturn = null
            val actualReturn = avlTree.delete(40)

            val expectedStructure = listOf(Pair(10, "A"), Pair(20, "B"), Pair(30, "C"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedReturn, actualReturn, "Returned wrong value")
            assertEquals(expectedStructure, actualStructure, "The tree changed after no deletion")
            assertEquals(avlTree.root?.key, 20)
        }

        @Test
        fun `if key exists deletion should return associated value and delete node`() {
            avlTree.insert(10, "A")
            avlTree.insert(20, "B")
            avlTree.insert(30, "C")

            val expectedReturn = "A"
            val actualReturn = avlTree.delete(10)

            val expectedStructure = listOf(Pair(20, "B"), Pair(30, "C"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedReturn, actualReturn, "Returned wrong value")
            assertEquals(
                expectedStructure,
                actualStructure,
                "The tree has incorrect structure after deletion"
            )
            assertEquals(avlTree.root?.key, 20)
        }

        @Test
        fun `deletion of last node should make tree empty`() {
            avlTree.insert(1, "A")
            avlTree.delete(1)

            val expectedStructure = listOf<Pair<Int, String>>()

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(avlTree.isEmpty(), true)
            assertEquals(expectedStructure, actualStructure)
        }

        @Test
        fun `deletion of node with one child should replace it with its child`() {
            avlTree.insert(20, "B")
            avlTree.insert(10, "A")
            avlTree.insert(30, "C")
            avlTree.insert(40, "D")

            avlTree.delete(30)

            val expectedStructure = listOf(Pair(10, "A"), Pair(20, "B"), Pair(40, "D"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 20)
        }

        // Small tree tests

        @Test
        fun `deletion of root node should replace it with its successor`() {
            avlTree.insert(10, "A")
            avlTree.insert(20, "B")
            avlTree.insert(30, "C")

            avlTree.delete(20)

            val expectedStructure = listOf(Pair(10, "A"), Pair(30, "C"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 30)
        }

        @Test
        fun `deletion of right child triggering right rotation should balance small tree`() {
            avlTree.insert(40, "D")
            avlTree.insert(30, "C")
            avlTree.insert(20, "B")
            avlTree.insert(10, "A")

            avlTree.delete(40)

            val expectedStructure = listOf(Pair(10, "A"), Pair(20, "B"), Pair(30, "C"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 20)
        }

        @Test
        fun `deletion of left child triggering left rotation should balance small tree`() {
            avlTree.insert(10, "A")
            avlTree.insert(20, "B")
            avlTree.insert(30, "C")
            avlTree.insert(40, "D")

            avlTree.delete(10)

            val expectedStructure = listOf(Pair(20, "B"), Pair(30, "C"), Pair(40, "D"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 30)
        }

        @Test
        fun `deletion of right child triggering left-right rotation should balance small tree`() {
            avlTree.insert(30, "C")
            avlTree.insert(10, "A")
            avlTree.insert(40, "D")
            avlTree.insert(20, "B")

            avlTree.delete(40)

            val expectedStructure = listOf(Pair(10, "A"), Pair(20, "B"), Pair(30, "C"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 20)
        }

        @Test
        fun `deletion of left child triggering right-left rotation should balance small tree`() {
            avlTree.insert(20, "B")
            avlTree.insert(10, "A")
            avlTree.insert(40, "D")
            avlTree.insert(30, "C")

            avlTree.delete(10)

            val expectedStructure = listOf(Pair(20, "B"), Pair(30, "C"), Pair(40, "D"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 30)
        }

        @Test
        fun `deletion of node with two children should replace it with its successor in small tree`() {
            avlTree.insert(20, "B")
            avlTree.insert(10, "A")
            avlTree.insert(30, "C")
            avlTree.insert(40, "D")

            avlTree.delete(20)

            val expectedStructure = listOf(Pair(10, "A"), Pair(30, "C"), Pair(40, "D"))

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 30)
        }

        // Medium tree tests

        @Test
        fun `deletion of right child triggering right rotation should balance medium tree`() {
            avlTree.insert(50, "E")
            avlTree.insert(30, "C")
            avlTree.insert(60, "F")
            avlTree.insert(20, "B")
            avlTree.insert(40, "D")
            avlTree.insert(70, "G")
            avlTree.insert(10, "A")

            avlTree.delete(70)

            val expectedStructure =
                listOf(
                    Pair(10, "A"),
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(50, "E"),
                    Pair(60, "F")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 30)
        }

        @Test
        fun `deletion of left child triggering left rotation should balance medium tree`() {
            avlTree.insert(30, "C")
            avlTree.insert(20, "B")
            avlTree.insert(50, "E")
            avlTree.insert(10, "A")
            avlTree.insert(40, "D")
            avlTree.insert(60, "F")
            avlTree.insert(70, "G")

            avlTree.delete(10)

            val expectedStructure =
                listOf(
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(50, "E"),
                    Pair(60, "F"),
                    Pair(70, "G")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 50)
        }

        @Test
        fun `deletion of node with two children should replace it with its successor in medium tree`() {
            avlTree.insert(30, "C")
            avlTree.insert(20, "B")
            avlTree.insert(50, "E")
            avlTree.insert(10, "A")
            avlTree.insert(40, "D")
            avlTree.insert(60, "F")
            avlTree.insert(70, "G")

            avlTree.delete(50)

            val expectedStructure =
                listOf(
                    Pair(10, "A"),
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(60, "F"),
                    Pair(70, "G")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 30)
        }

        // Large tree tests

        @Test
        fun `deletion of right child triggering right rotation should balance large tree`() {
            avlTree.insert(80, "H")
            avlTree.insert(50, "E")
            avlTree.insert(120, "L")
            avlTree.insert(30, "C")
            avlTree.insert(60, "F")
            avlTree.insert(100, "J")
            avlTree.insert(130, "M")
            avlTree.insert(20, "B")
            avlTree.insert(40, "D")
            avlTree.insert(70, "G")
            avlTree.insert(90, "I")
            avlTree.insert(110, "K")
            avlTree.insert(10, "A")

            avlTree.delete(130)

            val expectedStructure =
                listOf(
                    Pair(10, "A"),
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(50, "E"),
                    Pair(60, "F"),
                    Pair(70, "G"),
                    Pair(80, "H"),
                    Pair(90, "I"),
                    Pair(100, "J"),
                    Pair(110, "K"),
                    Pair(120, "L")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 80)
        }

        @Test
        fun `deletion of left child triggering left rotation should balance large tree`() {
            avlTree.insert(60, "F")
            avlTree.insert(20, "B")
            avlTree.insert(90, "I")
            avlTree.insert(10, "A")
            avlTree.insert(40, "D")
            avlTree.insert(80, "H")
            avlTree.insert(110, "K")
            avlTree.insert(30, "C")
            avlTree.insert(50, "E")
            avlTree.insert(70, "G")
            avlTree.insert(100, "J")
            avlTree.insert(120, "L")
            avlTree.insert(130, "M")

            avlTree.delete(10)

            val expectedStructure =
                listOf(
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(50, "E"),
                    Pair(60, "F"),
                    Pair(70, "G"),
                    Pair(80, "H"),
                    Pair(90, "I"),
                    Pair(100, "J"),
                    Pair(110, "K"),
                    Pair(120, "L"),
                    Pair(130, "M")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 60)
        }

        @Test
        fun `deletion of right child triggering double right rotation should balance large tree`() {
            avlTree.insert(80, "H")
            avlTree.insert(50, "E")
            avlTree.insert(110, "K")
            avlTree.insert(30, "C")
            avlTree.insert(60, "F")
            avlTree.insert(100, "J")
            avlTree.insert(120, "L")
            avlTree.insert(20, "B")
            avlTree.insert(40, "D")
            avlTree.insert(70, "G")
            avlTree.insert(90, "I")
            avlTree.insert(10, "A")

            avlTree.delete(120)

            val expectedStructure =
                listOf(
                    Pair(10, "A"),
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(50, "E"),
                    Pair(60, "F"),
                    Pair(70, "G"),
                    Pair(80, "H"),
                    Pair(90, "I"),
                    Pair(100, "J"),
                    Pair(110, "K")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 50)
        }

        @Test
        fun `deletion of left child triggering double left rotation should balance large tree`() {
            avlTree.insert(50, "E")
            avlTree.insert(20, "B")
            avlTree.insert(80, "H")
            avlTree.insert(10, "A")
            avlTree.insert(30, "C")
            avlTree.insert(70, "G")
            avlTree.insert(100, "J")
            avlTree.insert(40, "D")
            avlTree.insert(60, "F")
            avlTree.insert(90, "I")
            avlTree.insert(110, "K")
            avlTree.insert(120, "L")

            avlTree.delete(10)

            val expectedStructure =
                listOf(
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(50, "E"),
                    Pair(60, "F"),
                    Pair(70, "G"),
                    Pair(80, "H"),
                    Pair(90, "I"),
                    Pair(100, "J"),
                    Pair(110, "K"),
                    Pair(120, "L")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 80)
        }

        @Test
        fun `deletion of node with two children should replace it with its successor in large tree`() {
            avlTree.insert(80, "H")
            avlTree.insert(50, "E")
            avlTree.insert(120, "L")
            avlTree.insert(30, "C")
            avlTree.insert(60, "F")
            avlTree.insert(100, "J")
            avlTree.insert(130, "M")
            avlTree.insert(20, "B")
            avlTree.insert(40, "D")
            avlTree.insert(70, "G")
            avlTree.insert(90, "I")
            avlTree.insert(110, "K")
            avlTree.insert(10, "A")

            avlTree.delete(80)

            val expectedStructure =
                listOf(
                    Pair(10, "A"),
                    Pair(20, "B"),
                    Pair(30, "C"),
                    Pair(40, "D"),
                    Pair(50, "E"),
                    Pair(60, "F"),
                    Pair(70, "G"),
                    Pair(90, "I"),
                    Pair(100, "J"),
                    Pair(110, "K"),
                    Pair(120, "L"),
                    Pair(130, "M")
                )

            val actualStructure = mutableListOf<Pair<Int, String>>()

            for (pair in avlTree) actualStructure.add(pair)

            assertEquals(expectedStructure, actualStructure)
            assertEquals(avlTree.root?.key, 90)
        }
    }
}
