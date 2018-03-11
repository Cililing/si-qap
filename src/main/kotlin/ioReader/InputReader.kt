package ioReader

import models.InputModel
import java.io.File
import java.util.*

interface IInputReader {
    fun readInputModel(fileName: String) : InputModel
}

class InputReader : IInputReader {
    override fun readInputModel(fileName: String) : InputModel {
        val scanner = Scanner(File(fileName))
        val facilitiesCount = scanner.nextInt()
        val matrixSize = facilitiesCount * facilitiesCount

        val flowVector = mutableListOf<Int>()
        val costVector = mutableListOf<Int>()

        while (scanner.hasNext() && flowVector.size != matrixSize) {
            flowVector.add(scanner.nextInt())
        }

        while (scanner.hasNext() && costVector.size != matrixSize) {
            costVector.add(scanner.nextInt())
        }

        if (flowVector.size != matrixSize || costVector.size != matrixSize) {
            throw IllegalArgumentException("wrong flow or vector size")
        }

        return InputModel(
                facilitiesCount,
                flowMatrix = flowVector.chunked(facilitiesCount),
                costMatrix = costVector.chunked(facilitiesCount)
        )

    }
}