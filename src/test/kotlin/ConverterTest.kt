import classes.Converter
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ConverterTest {

    private val size = 4
    private lateinit var converter : Converter

    @Before
    fun setUp() {
        converter = Converter(4)
    }

    @Test
    fun bitConvertWorks() {
        val number0 = 0 // 0000
        val number0Expected =  getFalseArray()
        Assert.assertArrayEquals(number0Expected, converter.convertToBits(number0))

        val number3 = 3 //0011
        val number3Expected = getFalseArray()
        number3Expected[2] = true
        number3Expected[3] = true
        Assert.assertArrayEquals(number3Expected, converter.convertToBits(number3))

        val number8 = 8 // 1000
        val number8Expected = getFalseArray()
        number8Expected[0] = true
        Assert.assertArrayEquals(number8Expected, converter.convertToBits(number8))
    }

    @Test
    fun intConvertWorks() {
        val number0 = getFalseArray()
        Assert.assertEquals(0, converter.convertBitsToInt(number0))

        val number8 = getFalseArray()
        number8[0] = true
        Assert.assertEquals(8, converter.convertBitsToInt(number8))

        val number5 = getFalseArray() //1 + 4 -> 0101
        number5[3] = true
        number5[1] = true
        Assert.assertEquals(5, converter.convertBitsToInt(number5))
    }

    private fun getFalseArray() : BooleanArray {
        return BooleanArray(size, { false } )
    }
}