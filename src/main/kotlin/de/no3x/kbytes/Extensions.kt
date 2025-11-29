package de.no3x.kbytes

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

/**
 * Converts a raw byte count into a strongly typed [StorageSize].
 */
fun Long.toStorageSize(): StorageSize = StorageSize.ofBytes(this)

/**
 * Returns the size of a regular file as a [StorageSize] or throws an exception if the file doesn't exist.
 *
 * @throws IOException if an I/O error occurred.
 * @see Files.size
 */
@Throws(IOException::class)
public fun Path.toStorageSize(): StorageSize =
    StorageSize.ofBytes(Files.size(this))
