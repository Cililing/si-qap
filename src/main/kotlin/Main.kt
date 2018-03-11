import ResultBuilder.bigBuilder
import ResultBuilder.resultBuilder
import qap.*
import ioReader.InputReader
import models.ResultModel
import org.knowm.xchart.BitmapEncoder
import selectors.IPopulationSelector
import selectors.TournamentSelector
import java.io.File
import java.util.*
import org.knowm.xchart.SwingWrapper
import selectors.RouletteSelector


// Load input model and load proper strategy
val outputPrefix = "output/"
val inputPrefix = "input/"
val inputsFilenames = listOf(
        "had12.dat",
        "had14.dat",
        "had16.dat",
        "had18.dat",
        "had20.dat"
)
val random = Random()

val populationSelector: IPopulationSelector = TournamentSelector(random)
val geneticOperations: IGeneticOperations = StandardGeneticOperations(random)

// Const
val numberOfTries = 10
val startPopulationCount = 100
val numberOfGenerations = 100
val tournamentSampleCount = 10
val crossoverProbability = .8f
val mutationProbability = .03f


val inputModels = inputsFilenames.map { x -> Pair(x, InputReader().readInputModel(inputPrefix + x)) }

// Buffer for result
object ResultBuilder {
    var resultBuilder = StringBuilder()
    var bigBuilder = StringBuilder()
}

fun main(args: Array<String>) {

    // InputModels -> Pair of filename and model
    inputModels.forEach { input ->

        val solutions = mutableListOf<List<ResultModel>>()
        (0 until numberOfTries).forEach  { tryNumber ->

            // This loop will save result in ResultBuilder
            solutions.add(GeneticLooper(input.second,
                    startPopulationCount,
                    numberOfGenerations,
                    tournamentSampleCount,
                    crossoverProbability,
                    mutationProbability,
                    populationSelector,
                    geneticOperations,
                    QAPAlgorithm(input.second),
                    resultBuilder
            ).makeLoop())

            // Print data from single loop
            print(resultBuilder.toString())

            // Save data!
            val outputFilename = outputPrefix + input.first + "-$tryNumber"
            File(outputFilename).writeText(resultBuilder.toString())

            bigBuilder.append(resultBuilder)

            // flush buffer
            resultBuilder = StringBuilder()
        }

        // split output -> tries nr X in one array
        val splittedResult = bigBuilder.split("\n")
                .chunked(numberOfGenerations)


        val finalResult = StringBuilder()
        (0 until numberOfGenerations).forEach { k ->
            val allTries = StringBuilder()
            (0 until numberOfTries).forEach { i -> allTries.append(splittedResult[i][k]).append(", ") }
            finalResult.append(allTries.append("\n"))
        }

        // Save data for all loops
        val outputBigFile = outputPrefix + input.first + "-full"
        File(outputBigFile).writeText(finalResult.toString())
        bigBuilder = StringBuilder()


        // Indexes
        val indexes = solutions[0].map { x -> x.index }

        // Data (from all tries)
        val bestDataSummed = (0 until numberOfGenerations).map { x -> (0 until numberOfTries).sumBy { solutions[it][x].bestSolution.cost } }
        val avgDataSummed = (0 until numberOfGenerations).map { x -> (0 until numberOfTries).sumBy { solutions[it][x].avgResult.toInt() } }
        val worstDataSummed = (0 until numberOfGenerations).map { x -> (0 until numberOfTries).sumBy { solutions[it][x].worstSolution.cost } }

        val bestDataAvg = (0 until numberOfGenerations).map { x -> (0 until numberOfTries).sumBy { solutions[it][x].bestSolution.cost } }.map { x -> x / numberOfTries }
        val avgDataAvg = (0 until numberOfGenerations).map { x -> (0 until numberOfTries).sumBy { solutions[it][x].avgResult.toInt() } }.map { x -> x / numberOfTries }
        val worstDataAvg = (0 until numberOfGenerations).map { x -> (0 until numberOfTries).sumBy { solutions[it][x].worstSolution.cost } }.map { x -> x / numberOfTries }

        val bestDataErr = solutions[0].mapIndexed { index, x -> x.bestSolution.cost - bestDataAvg[index] }.map { x -> x /numberOfTries }
        val avgDataErr = solutions[0].mapIndexed { index, x -> x.avgResult.toInt() - avgDataAvg[index] }.map { x -> x /numberOfTries }
        val worstDataErr = solutions[0].mapIndexed { index, x -> x.worstSolution.cost - worstDataAvg[index] }.map { x -> x /numberOfTries }

        // Print Char
        val chart = getChartWithError("Chart: ${input.first}", "X", "Y", "best", indexes, bestDataAvg, bestDataErr).apply {
            addSeries("worst", indexes.toIntArray(), worstDataAvg.toIntArray(), worstDataErr.toIntArray())
            addSeries("avg", indexes.toIntArray(), avgDataAvg.toIntArray(), avgDataErr.toIntArray())
        }

        // Save chart
        BitmapEncoder.saveJPGWithQuality(chart, "$outputPrefix${input.first}-chart.jpg", 0.8f)


        SwingWrapper(chart).displayChart()

        // Clear solutions
        solutions.clear()
    }
}