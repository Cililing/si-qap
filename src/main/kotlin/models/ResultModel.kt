package models

data class ResultModel(val index : Int,
                       val bestSolution: GeneticSolutionModel,
                       val worstSolution: GeneticSolutionModel,
                       val avgResult: Double) {

    fun convertToCsvString() : String {
        return "$index, ${bestSolution.cost}, $avgResult, ${worstSolution.cost}\n"
    }
}