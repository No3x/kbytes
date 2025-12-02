package de.no3x.kbytes

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class StorageSizeIdentityTest {

    @Test
    fun `value class equality works`() {
        val a = StorageSize(42)
        val b = StorageSize(42)
        assertThat(a).isEqualTo(b)
        assertThat(a.bytes).isEqualTo(b.bytes)
    }
}
