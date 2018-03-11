package selectors

import models.GeneticSolutionModel
import java.util.*

class RouletteSelector(private val random: Random, private val selectorWinningType: SelectorWinningType = SelectorWinningType.MAX) : IPopulationSelector {
    override fun select(population: List<GeneticSolutionModel>, tournamentSampleCount: Int): List<List<Int>> {
        val nextGeneration = mutableListOf<List<Int>>()

        val costSum = population.sumBy { x -> x.cost } // whole cost

        // Fix population adoption to fit selector
        val mapper = if (selectorWinningType == SelectorWinningType.MAX)
            fun(x: GeneticSolutionModel) : GeneticSolutionModel { return x } // Just return
        else fun(x) : GeneticSolutionModel { return GeneticSolutionModel(x.solution, costSum - x.cost) } // Return fixed cost

        val fixedPopulation = population.map { x -> mapper.invoke(x) }
        val fixedCostSum = fixedPopulation.sumBy { x -> x.cost }

        while (nextGeneration.count() != population.count()) {
            val randomPoint = random.nextInt(fixedCostSum)
            var weigthSum = 0

            for (instance in fixedPopulation) {
                weigthSum += instance.cost
                if (weigthSum >= randomPoint) {
                    nextGeneration.add(instance.solution)
                    break
                }
            }
        }

        return nextGeneration
    }
}