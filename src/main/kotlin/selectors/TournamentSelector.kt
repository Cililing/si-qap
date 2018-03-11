package selectors

import models.GeneticSolutionModel
import java.util.*

class TournamentSelector(private val random: Random, private val selectorWinningType: SelectorWinningType = SelectorWinningType.MIN) : IPopulationSelector {

    override fun select(population: List<GeneticSolutionModel>, tournamentSampleCount: Int): List<List<Int>> {
        // Select by tournament.
        val nextGeneration = mutableListOf<List<Int>>()

        while (nextGeneration.count() != population.count()) {
            val representation = List(tournamentSampleCount, {
                population[random.nextInt(population.size)]
            })

            when (selectorWinningType) {
                SelectorWinningType.MAX -> nextGeneration.add(representation.maxBy { x -> x.cost }!!.solution)
                SelectorWinningType.MIN -> nextGeneration.add(representation.minBy { x -> x.cost }!!.solution)
            }

        }

        return nextGeneration.toList()
    }
}