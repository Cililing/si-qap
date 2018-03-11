import models.InputModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import qap.QAPCostCalculator

class QAPAlghoritmTest {

    private val inputModel = InputModel(9,
            listOf(
                    listOf(0, 32, 68, 97, 75, 70, 75, 40, 24),
                    listOf(32, 0, 42, 80, 53, 65, 82, 47, 29),
                    listOf(68, 42, 0, 45, 15, 49, 79, 55, 50),
                    listOf(97, 80, 45, 0, 30, 36, 65, 65, 73),
                    listOf(75, 53, 15, 30, 0, 38, 69, 53, 53),
                    listOf(70, 65, 49, 36, 38, 0, 31, 32, 46),
                    listOf(75, 82, 79, 65, 69, 31, 0, 36, 56),
                    listOf(40, 47, 55, 65, 53, 32, 36, 0, 19),
                    listOf(24, 29, 50, 73, 53, 46, 56, 19, 0)
            ),
            listOf(
                    listOf(0, 2, 4, 0, 0, 0, 2, 0, 0),
                    listOf(2, 0, 3, 1, 0, 6, 0, 0, 2),
                    listOf(4, 3, 0, 0, 0, 3, 0, 0, 0),
                    listOf(0, 1, 0, 0, 1, 0, 1, 2, 0),
                    listOf(0, 0, 0, 1, 0, 0, 0, 0, 0),
                    listOf(0, 6, 3, 0, 0, 0, 0, 0, 2),
                    listOf(2, 0, 0, 1, 0, 0, 0, 4, 3),
                    listOf(0, 0, 0, 2, 0, 0, 4, 0, 0),
                    listOf(0, 2, 0, 0, 0, 2, 3, 0, 0)
            ))

    private lateinit var instance : QAPCostCalculator

    @Before
    fun setUp() {
        instance = QAPCostCalculator(inputModel)
    }

    /**
     * Test data from https://neos-guide.org/content/qap9
     */
    @Test
    fun testProperValues() {
        val cost1 = instance.getWholeCost(
                listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).map { x -> x - 1 }
        )
        Assert.assertEquals(1916, cost1)

        val cost2 = instance.getWholeCost(
                listOf(2, 3, 1, 4, 5, 6, 7, 8, 9).map { x -> x - 1 }
        )
        Assert.assertEquals(1947, cost2)
    }
}