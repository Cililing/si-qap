package classes

import java.util.*

interface IGeneticOperations {
    fun crossover(parentOne: List<Int>, parentTwo: List<Int>) : Pair<List<Int>, List<Int>>
    fun mutateIntList(list: List<Int>, mutationProbability: Float = 0.01f) : List<Int>
}

class GeneticOperations(private val conventer : IConverter = Converter(), private val random: Random = Random()) {
    fun crossover(parentOne: List<Int>, parentTwo: List<Int>) : Pair<List<Int>, List<Int>> {
        val crossoverPoint = 1 + random.nextInt(parentOne.size - 1) - 1

        val child1 = parentOne.subList(0, crossoverPoint).plus(parentTwo.subList(crossoverPoint, parentTwo.size))
        val child2 = parentTwo.subList(0, crossoverPoint).plus(parentOne.subList(crossoverPoint, parentOne.size))

        return Pair(child1, child2)
    }

    private fun mutate(instanceToMutate: BooleanArray, mutationProbability: Float) : BooleanArray {
        val mutated = BooleanArray(instanceToMutate.size)

        mutated.forEachIndexed { index, _ ->
            val shouldMutate = mutationProbability > random.nextFloat()
            mutated[index] = if (shouldMutate) !instanceToMutate[index] else instanceToMutate[index]
        }

        return mutated
    }

    private fun mutate(instances : List<BooleanArray>, mutationProbability: Float) : List<BooleanArray> {
        return instances.map { x -> mutate(x, mutationProbability) }
    }

    fun mutateIntList(list: List<Int>, mutationProbability: Float = 0.1f) : List<Int> {
        val binaryList = list.map { x -> conventer.convertToBits(x) }
        val mutated = mutate(binaryList, mutationProbability)
        return mutated.map { x -> conventer.convertBitsToInt(x) }
    }

    fun mutateIntLists(lists: List<List<Int>>, mutationProbability: Float = 0.1f) : List<List<Int>> {
        return lists.map { x -> mutateIntList(x) }
    }

}