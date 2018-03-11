package qap

import java.util.*

interface IGeneticOperations {

    fun crossoverAndMutate(population: List<List<Int>>, crossoverProbability: Float = 0.8f, mutationProbability: Float = 0.1f) : List<List<Int>> {
        // cross population

        //val zipped = population.zipWithNext()
        val pivot = population.size / 2
        val zipped = population.subList(0, pivot).zip(population.subList(pivot, population.size))

        val crossedPopulation = zipped.map { x -> crossover(x.first, x.second, crossoverProbability).toList() }.flatten()

        // mutate crossed population
        return crossedPopulation.map { x -> mutate(x, mutationProbability) }
    }

    fun crossover(parentOne: List<Int>, parentTwo: List<Int>, crossoverProbability: Float) : Pair<List<Int>, List<Int>>
    fun mutate(list: List<Int>, mutationProbability: Float) : List<Int>
}

class StandardGeneticOperations(private val random: Random = Random()) : IGeneticOperations {

    override fun crossover(parentOne: List<Int>, parentTwo: List<Int>, crossoverProbability: Float) : Pair<List<Int>, List<Int>> {
        if (crossoverProbability < random.nextFloat()) {
            return Pair(parentOne, parentTwo)
        }

        val crossoverPoint = 1 + random.nextInt(parentOne.size - 1) - 1

        val child1 = parentOne.subList(0, crossoverPoint).toMutableList()
        parentTwo.forEach { i ->
            if (!child1.contains(i)) child1.add(i)
        }


        val child2 = parentTwo.subList(0, crossoverPoint).toMutableList()
        parentOne.forEach { i ->
            if (!child2.contains(i)) child2.add(i)
        }


        return Pair(child1.toList(), child2.toList())
    }

    override fun mutate(list: List<Int>, mutationProbability: Float) : List<Int> {
        val mutableInput = list.toMutableList()
        for (i in 0 until list.size) {
            if (random.nextFloat() <= mutationProbability) {
                // Get random index and swap with mutated gen
                val index = random.nextInt(list.size)

                val temp = mutableInput[i]
                mutableInput[i] = mutableInput[index]
                mutableInput[index] = temp
            }
        }
        return mutableInput.toList()
    }
}