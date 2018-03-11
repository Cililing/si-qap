package classes

import models.InputModel
import java.io.File
import java.io.InputStream

interface IInputReader {
    fun readInputModel(fileName: String) : InputModel
}

class InputReader : IInputReader {
    override fun readInputModel(fileName: String) : InputModel {
        val inputStream = File(fileName).inputStream()
        val text = inputStream.readTextAndClose()

        val input = text.split("\n").map {
            line -> line.split(" ").filter { item -> item != "" }
        }.filter {
            line -> line.isNotEmpty()
        }

        val numberOfLines = input[0][0].toInt()

        val mInput = input.drop(1).take(numberOfLines).map { x -> x.map { x -> x.toInt() } }
        val nInput = input.drop(1 + numberOfLines).map { x -> x.map { x -> x.toInt() } }

        return InputModel(numberOfLines, mInput, nInput)
    }
}

fun InputStream.readTextAndClose() : String {
    return this.bufferedReader(charset = Charsets.UTF_8).use { it.readText()}
}