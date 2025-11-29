package de.no3x.kbytes

import java.nio.file.Path
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
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

        assertTrue(storageSize.inBytes() > 0)
        assertTrue(storageSize > 0.KiB)
        assertNotNull(storageSize.bestBinaryUnit())
        assertNotNull(storageSize.bestDecimalUnit())
    }

}
