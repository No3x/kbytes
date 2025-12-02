package de.no3x.kbytes

import assertk.assertThat
import assertk.assertions.isCloseTo
import assertk.assertions.isEqualTo
import de.no3x.junit.extension.locale.WithLocale
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestTemplate
import java.util.*

/**
 * Usage-style tests that also double as executable documentation for the tiny DSL.
 */
class StorageSizeSampleTest {

    @Test
    fun `dsl extensions create expected byte counts`() {
        assertThat(1.KiB.inBytes()).isEqualTo(1_024L);
        assertThat(1L.MB.inBytes()).isEqualTo(1_000_000L);
        assertThat(3.GB.inBytes()).isEqualTo(3_000_000_000L);
    }

    @Test
    fun `arithmetic with storage sizes keeps byte precision`() {
        val total = 2.MiB + 512.KiB - 256.KiB
        val expectedBytes = (2L shl 20) + (512L shl 10) - (256L shl 10)
        assertThat(total.inBytes()).isEqualTo(expectedBytes)
    }

    @Test
    fun `test inBytes`() {
        assertThat(1.KB.inBytes()).isEqualTo(1_000)
        assertThat(1.MB.inBytes()).isEqualTo(1_000_000)
        assertThat(1.GB.inBytes()).isEqualTo(1_000_000_000)
        assertThat(1.TB.inBytes()).isEqualTo(1_000_000_000_000)
        assertThat(1.PB.inBytes()).isEqualTo(1_000_000_000_000_000)
        assertThat(1.EB.inBytes()).isEqualTo(1_000_000_000_000_000_000)

        assertThat(1.KiB.inBytes()).isEqualTo(1024)
        assertThat(1.MiB.inBytes()).isEqualTo(1048576)
        assertThat(1.GiB.inBytes()).isEqualTo(1073741824)
        assertThat(1.TiB.inBytes()).isEqualTo(1099511627776)
        assertThat(1.PiB.inBytes()).isEqualTo(1125899906842624)
        assertThat(1.EiB.inBytes()).isEqualTo(1152921504606846976)
    }

    @TestTemplate
    @WithLocale("de-DE", "en-US", "fr-FR", "ja-JP")
    fun `toString works with different locales`() {
        val total = 2.MiB + 512.KiB - 256.KiB
        val expected = when (Locale.getDefault().toLanguageTag()) {
            "de-DE" -> "2,36 MB"
            "fr-FR" -> "2,36 MB"
            "ja-JP" -> "2.36 MB"
            else -> "2.36 MB" // en-US and fallback
        }
        assertThat(total.toString()).isEqualTo(expected)
    }

    @Test
    fun `best unit picks the largest readable unit`() {
        val oneAndHalfKiB = StorageSize.ofBytes(1_536)
        val bestBinary = oneAndHalfKiB.bestBinaryUnit()
        assertThat(bestBinary.unit).isEqualTo(BinaryUnit.KIBIBYTE)
        assertThat(bestBinary.value).isCloseTo(1.50, 0.01)
        val twoPointThreeGb = 2_300_000_000L.toStorageSize()
        val bestDecimal = twoPointThreeGb.bestDecimalUnit()
        assertThat(bestDecimal.unit).isEqualTo(DecimalUnit.GIGABYTE)
        assertThat(bestDecimal.value).isCloseTo(2.30, 0.01)
    }
}
