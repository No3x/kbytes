package de.no3x.kbytes

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import org.junit.jupiter.api.Test
import java.nio.file.Path
import kotlin.io.path.writeText
import org.junit.jupiter.api.io.TempDir

/**
 * Usage-style tests that also double as executable documentation for the tiny DSL.
 */
class PathExtensionSampleTest {

    @Test
    fun `path extension`(@TempDir tempDir: Path) {
        val tempFile = tempDir.resolve("kbytes-sample.txt")
        tempFile.writeText("non-empty") // ensure file is > 0 bytes on all platforms

        val storageSize = tempFile.toStorageSize()

        assertThat(storageSize.inBytes()).isGreaterThan(0)
        assertThat(storageSize).isGreaterThan(0.KiB)
        assertThat(storageSize.bestBinaryUnit()).isNotNull()
        assertThat(storageSize.bestDecimalUnit()).isNotNull()
    }

}
