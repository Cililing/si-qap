import classes.Converter
import classes.GeneticOperations
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

class GeneticOperationsTest {

    private lateinit var randomMock : Random

    private lateinit var instance : GeneticOperations

    @Before
    fun setUp() {
        randomMock =  mock(Random::class.java)
        instance = GeneticOperations(Converter(), randomMock)
    }

    @Test
    fun crossOverTest() {
        val l1 = listOf(0, 1, 2, 3, 4)
        val l2 = listOf(5, 6, 7, 8, 9)

        val dividePoint = 3
        val expectedL1 = listOf(0, 1, 2, 8, 9)
        val expectedL2 = listOf(5, 6, 7, 3, 4)

        `when`(randomMock.nextInt(Matchers.anyInt())).thenReturn(dividePoint)

        val result = instance.crossover(l1, l2)

        Assert.assertEquals(expectedL1, result.first)
        Assert.assertEquals(expectedL2, result.second)
    }

//    @Test
//    fun mutateTest_noMutation() {
//        val allFalse = BooleanArray(4)
//        val expected = BooleanArray(4)
//        val noMutation = 0.0f
//
//        Assert.assertArrayEquals(expected, instance.mutate(allFalse, noMutation))
//    }
//
//    @Test
//    fun mutateTest_allMutatated() {
//        val allFalse = BooleanArray(4)
//        val expected = BooleanArray(4, {true})
//        val noMutation = 1.0f
//
//        Assert.assertArrayEquals(expected, instance.mutate(allFalse, noMutation))
//    }
}