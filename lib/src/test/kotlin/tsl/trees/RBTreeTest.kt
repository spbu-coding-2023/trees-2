package tsl.trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RBTreeTest{
    private lateinit var tree: RBTree<Int, String>

    @BeforeEach
    fun setup() {
        tree  = RBTree()
    }

    @Test
    fun test() {}

}