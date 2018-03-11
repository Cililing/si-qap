package classes

import models.GeneticSolutionModel
import models.InputModel
import org.paukov.combinatorics3.Generator

class QAPAlghorithm(private val inputModel: InputModel) {

    private fun getFlowBetweenFacilities(i: Int, j: Int) : Int {
        return inputModel.flowMatrix[i][j]
    }

    private fun getCostBetweenLocations(i: Int, j: Int) : Int {
        return inputModel.costMatrix[i][j]
    }

    private fun getCost(facilities: Pair<Int, Int>, locations: Pair<Int, Int>) : Int {
        return getFlowBetweenFacilities(facilities.first, facilities.second) *
                getCostBetweenLocations(locations.first, locations.second)
    }

    private fun getWholeCost(solution: List<Int>) : Int {
        var cost = 0
        Generator.combination(solution).simple(2).stream().forEach { facilitiesPairs ->
            cost += getCost(
                    Pair(facilitiesPairs[0], facilitiesPairs[1]),
                    Pair(solution.indexOf(facilitiesPairs[0]), solution.indexOf(facilitiesPairs[1]))
            )
        }
        return cost
    }

    fun getWholeCostAndAdoption(solutions: List<List<Int>>) : List<GeneticSolutionModel> {
        val solutionsWithCost = solutions.map { x -> Pair(x, getWholeCost(x)) }
        val costCounter = solutionsWithCost.fold(0, { acc, pair -> acc + pair.second })

        return solutionsWithCost.map { x -> GeneticSolutionModel(x.first, ((x.second.toFloat() / costCounter).toInt())) }
    }
}