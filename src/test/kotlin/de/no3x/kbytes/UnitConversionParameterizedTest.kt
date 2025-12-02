package de.no3x.kbytes

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class UnitConversionParameterizedTest {

    @ParameterizedTest
    @EnumSource(BinaryUnit::class)
    fun `binary units convert from and to bytes`(unit: BinaryUnit) {
        val size = StorageSize.of(1, unit)

        assertEquals(unit.bytes, size.inBytes())
        assertEquals(1.0, size.toBinary(unit))
    }

    @ParameterizedTest
    @EnumSource(DecimalUnit::class)
    fun `decimal units convert from and to bytes`(unit: DecimalUnit) {
        val size = StorageSize.of(1, unit)

        assertEquals(unit.bytes, size.inBytes())
        assertEquals(1.0, size.toDecimal(unit))
    }

    @ParameterizedTest
    @EnumSource(
        DecimalUnit::class,
        names = ["EXABYTE"], // exclude last unit because it has no larger next unit,
        mode = EnumSource.Mode.EXCLUDE
    )
    fun `decimal units convert from and to bytes into next larger unit as well`(unit: DecimalUnit) {
        val nextLargerUnit = unit.nextLargerUnit()
        val size = StorageSize.of(1, unit)

        assertEquals(unit.bytes, size.inBytes())
        assertEquals(0.001, size.toDecimal(nextLargerUnit))
    }

    @ParameterizedTest
    @EnumSource(
        BinaryUnit::class,
        names = ["EXBIBYTE"], // exclude last unit because it has no larger next unit,
        mode = EnumSource.Mode.EXCLUDE
    )
    fun `binary units convert from and to bytes into next larger unit as well`(unit: BinaryUnit) {
        val nextLargerUnit = unit.nextLargerUnit()
        val size = StorageSize.of(1, unit)
        val expectedRatio = unit.bytes.toDouble() / nextLargerUnit.bytes.toDouble()

        assertEquals(unit.bytes, size.inBytes())
        assertEquals(expectedRatio, size.toBinary(nextLargerUnit))
    }

    @Test
    fun formatUnitWithDefaults() {
        assertAll {
            val binary = (900.KB + 123.KB).bestBinaryUnit()
            assertThat(binary.unit).isEqualTo(BinaryUnit.KIBIBYTE)
            assertThat(binary.value).isEqualTo(999.0234375)

            val decimal = (900.KB + 123.KB).bestDecimalUnit()
            assertThat(decimal.unit).isEqualTo(DecimalUnit.MEGABYTE)
            assertThat(decimal.value).isEqualTo(1.023)
        }
    }
}
