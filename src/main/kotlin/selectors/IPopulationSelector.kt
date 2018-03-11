package selectors

import models.GeneticSolutionModel

interface IPopulationSelector {
    fun select(population: List<GeneticSolutionModel>, tournamentSampleCount: Int) : List<List<Int>>
}