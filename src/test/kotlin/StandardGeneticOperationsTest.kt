import qap.StandardGeneticOperations
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

class StandardGeneticOperationsTest {

    private lateinit var randomMock : Random
    private lateinit var instance : StandardGeneticOperations

    @Before
    fun setUp() {
        randomMock =  mock(Random::class.java)
        instance = StandardGeneticOperations(randomMock)
    }

    @Test
    fun crossoverTest_noDuplicate() {
        val l1 = listOf(0, 1, 2, 3, 4)
        val l2 = listOf(5, 6, 7, 8, 9)

        val dividePoint = 3
        val expectedL1 = listOf(0, 1, 2, 8, 9)
        val expectedL2 = listOf(5, 6, 7, 3, 4)

        `when`(randomMock.nextInt(Matchers.anyInt())).thenReturn(dividePoint)

        val result = instance.crossover(l1, l2, 1f)

        println("crossoverTest_noDuplicate results: ")
        println(result.first)
        println(result.second)

        Assert.assertEquals(expectedL1, result.first)
        Assert.assertEquals(expectedL2, result.second)
    }

    @Test
    fun crossoverTest_withDuplicates() {
        // With divide point = 3 number two will be duplicate
        val l1 = listOf(0, 1, 2, 3, 4, 5)
        val l2 = listOf(0, 1, 5, 4, 3, 2)

        val dividePoint = 3
        val expectedL1 = listOf(0, 1, 2, 4, 3, 5)
        val expectedL2 = listOf(0, 1, 5, 3, 4, 2)

        `when`(randomMock.nextInt(Matchers.anyInt())).thenReturn(dividePoint)

        val result = instance.crossover(l1, l2, 1.0f)

        println("crossoverTest_noDuplicate results: ")
        println(result.first)
        println(result.second)

        Assert.assertEquals(expectedL1, result.first)
        Assert.assertEquals(expectedL2, result.second)
    }

    @Test
    fun crossover_multimpleRandomData_allUnique() {
        // Unmock random and create instance one again!
        this.randomMock = Random()
        this.instance = StandardGeneticOperations(randomMock)

        val numberOfSamples = 20

        // get random lists with unique elements in random order
        val randomSolutions = (0 until numberOfSamples).map {
            (0 until 10).shuffled()
        }

        println("crossoverTest_allUnique results: ")
        println()
        println("Random solutions")
        randomSolutions.forEach {
            println(it)
        }

        // for each pair cross them
        val crossed = (0 until numberOfSamples step 2).map { x ->
            instance.crossover(randomSolutions[x], randomSolutions[x+1], 1.0f).toList()
        }.flatten()


        println()
        println("Crossed results")
        crossed.forEach {
            println(it)
        }

        // for each solutions make sure elements are unique
        crossed.forEach { x ->
            run {
                Assert.assertEquals(x.size, x.toSet().size)
            }
        }
    }
}