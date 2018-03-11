package qap

import models.GeneticSolutionModel
import models.InputModel
import org.paukov.combinatorics3.Generator
interface IQAPCostCalculator {
    fun getWholeCost(solution: List<Int>): Int
    fun getWholeCostAndAdoption(solution: List<List<Int>>): List<GeneticSolutionModel>
}

class QAPCostCalculator(private val inputModel: InputModel) : IQAPCostCalculator {
    /*
        inputModel:  Indexes of facilities in order [vector]
            For example: solution=[0, 3, 1, 2] means,
            to location 0 we assign factory 0,
            to location 1 we assign factory 3 etc.
     */

    override fun getWholeCost(solution: List<Int>) : Int {
        // contains pairs with locations, like: (location_3, location_8)
        val allConnections = Generator.permutation(solution).withRepetitions(2).toList()
        //val allConnections = Generator.combination(solution).multi(2).toList()


        var cost = 0
        allConnections.forEachIndexed { index, locationsPair ->
            val x = locationsPair[0]
            val y = locationsPair[1]

            cost += (inputModel.flowMatrix[x][y]
                    * inputModel.costMatrix[solution.indexOf(x)][solution.indexOf(y)])
        }

        return cost
    }

    private fun costs(arr: List<Int>): Int {
        var result = 0

        (0 until inputModel.numberOfCities).forEach { i -> (0 until inputModel.numberOfCities).forEach { j -> result += inputModel.costMatrix[i][j] * inputModel.flowMatrix[arr[i]][arr[j]] } }

        return result
    }

    override fun getWholeCostAndAdoption(solutions: List<List<Int>>) : List<GeneticSolutionModel> {
        val solutionsWithCost = solutions.map { x -> Pair(x, getWholeCost(x)) }
        return solutionsWithCost.map { x -> GeneticSolutionModel(x.first, x.second) }
    }
}