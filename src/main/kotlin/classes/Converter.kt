package classes

interface IConverter {
    fun convertToBits(value: Int) : BooleanArray
    fun convertBitsToInt(value: BooleanArray) : Int
}

class Converter(private val numberOfBits : Int = 4) : IConverter {
    override fun convertToBits(value: Int) : BooleanArray {
        var binaryRepresentation = Integer.toBinaryString(value)

        // fix binary representation
        while (binaryRepresentation.count() != numberOfBits) {
            binaryRepresentation = "0".plus(binaryRepresentation)
        }

        val bits = BooleanArray(numberOfBits)
        binaryRepresentation.forEachIndexed { index, c -> bits[index] = c == '1' }

        return bits
    }


    override fun convertBitsToInt(value: BooleanArray) : Int {
        var n = 0
        for (b in value)
            n = n shl 1 or if (b) 1 else 0
        return n
    }
}

