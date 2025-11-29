package de.no3x.kbytes

import de.no3x.junit.extension.locale.WithLocale
import org.junit.jupiter.api.TestTemplate
import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Usage-style tests that also double as executable documentation for the tiny DSL.
 */
class StorageSizeSampleTest {

    @Test
    fun `dsl extensions create expected byte counts`() {
        assertEquals(1_024L, 1.KiB.inBytes())
        assertEquals(1_000_000L, 1L.MB.inBytes())
        assertEquals(3_000_000_000L, (3.GB).inBytes())
    }

    @Test
    fun `arithmetic with storage sizes keeps byte precision`() {
        val total = 2.MiB + 512.KiB - 256.KiB
        val expectedBytes = (2L shl 20) + (512L shl 10) - (256L shl 10)
        assertEquals(expectedBytes, total.inBytes())
    }

    @TestTemplate
    @WithLocale("de-DE", "en-US", "fr-FR", "ja-JP")
    fun `toString works`() {
        val total = 2.MiB + 512.KiB - 256.KiB
        val expected = when (Locale.getDefault().toLanguageTag()) {
            "de-DE" -> "2,36 MB"
            "fr-FR" -> "2,36 MB"
            "ja-JP" -> "2.36 MB"
            else -> "2.36 MB" // en-US and fallback
        }
        assertEquals(expected, total.toString())
    }

    @Test
    fun `best unit picks the largest readable unit`() {
        val oneAndHalfKiB = StorageSize.ofBytes(1_536)
        val bestBinary = oneAndHalfKiB.bestBinaryUnit()
        assertEquals(BinaryUnit.KIBIBYTE, bestBinary.unit)
        assertTrue(bestBinary.value in 1.49..1.51, "value is roughly 1.5 KiB")
        val twoPointThreeGb = 2_300_000_000L.toStorageSize()
        val bestDecimal = twoPointThreeGb.bestDecimalUnit()
        assertEquals(DecimalUnit.GIGABYTE, bestDecimal.unit)
        assertTrue(bestDecimal.value in 2.29..2.31, "value is roughly 2.30 GB")
    }
}
