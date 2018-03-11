package ioReader

import models.InputModel

interface IInputReader {
    fun readInputModel(fileName: String) : InputModel
}