import models.GeneticSolutionModel
import models.InputModel
import models.ResultModel
import qap.IGeneticOperations
import qap.QAPAlgorithm
import selectors.IPopulationSelector

class GeneticLooper(private val inputModel: InputModel,
                    private val startPopulationCount: Int,
                    private val numberOfGenerations: Int,
                    private val tournamentSampleCount: Int,
                    private val crossoverProbability: Float,
                    private val mutationProbability: Float,
                    private val selector: IPopulationSelector,
                    private val geneticOperations: IGeneticOperations,
                    private val qapAlgorithm: QAPAlgorithm,
                    private val resultBuilder: StringBuilder){

    private fun geneticLoop(actualPopulation: List<GeneticSolutionModel>): List<GeneticSolutionModel> {
        // Select
        val newSample = selector.select(actualPopulation, tournamentSampleCount)
        // Now cross and mutate new sample
        val newPopulation = geneticOperations.crossoverAndMutate(newSample, crossoverProbability, mutationProbability)
        // Rate changes
        return qapAlgorithm.getWholeCostAndAdoption(newPopulation)
    }

    fun makeLoop() : List<ResultModel> {
        val results = mutableListOf<ResultModel>()

        // Find firsts solutions!
        val prototypedRandomSolutions = List(startPopulationCount, { (0 until inputModel.numberOfCities).shuffled()} )

        // Calculate cost of those solutions
        var solutionsWithCost = qapAlgorithm.getWholeCostAndAdoption(prototypedRandomSolutions)
        results.add(getResultModel(0, solutionsWithCost))
        resultBuilder.append(getResultModel(0, solutionsWithCost).convertToCsvString())

        (1 until numberOfGenerations).map { x ->
            println("$x/$startPopulationCount")
            solutionsWithCost = geneticLoop(solutionsWithCost)
            results.add(getResultModel(x, solutionsWithCost))
            resultBuilder.append(getResultModel(x, solutionsWithCost).convertToCsvString())
        }

        return results.toList() // TODO
    }


    private fun getResultModel(index: Int, solutions: List<GeneticSolutionModel>) : ResultModel {
        return ResultModel(
                index,
                solutions.minBy { x -> x.cost }!!,
                solutions.maxBy { x -> x.cost }!!,
                solutions.map { x -> x.cost }.average()
        )
    }
}





