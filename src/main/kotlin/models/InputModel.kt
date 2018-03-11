package models

data class InputModel(val numberOfCities : Int,
                      val flowMatrix: List<List<Int>>,
                      val costMatrix: List<List<Int>>)