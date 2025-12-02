package de.no3x.kbytes


enum class BinaryUnit(val symbol: String, val bytes: Long) {
    BYTE("B", 1L),
    KIBIBYTE("KiB", 1L shl 10),
    MEBIBYTE("MiB", 1L shl 20),
    GIBIBYTE("GiB", 1L shl 30),
    TEBIBYTE("TiB", 1L shl 40),
    PEBIBYTE("PiB", 1L shl 50),
    EXBIBYTE("EiB", 1L shl 60); // past this, Long overflows anyway
}

enum class DecimalUnit(val symbol: String, val bytes: Long) {
    BYTE("B", 1L),
    KILOBYTE("kB", 1_000L),
    MEGABYTE("MB", 1_000_000L),
    GIGABYTE("GB", 1_000_000_000L),
    TERABYTE("TB", 1_000_000_000_000L),
    PETABYTE("PB", 1_000_000_000_000_000L),
    EXABYTE("EB", 1_000_000_000_000_000_000L), ;
}

public fun BinaryUnit.nextLargerUnit(): BinaryUnit {
    val values = enumValues<BinaryUnit>()
    val nextOrdinal = (ordinal + 1)
    return values[nextOrdinal]
}

public fun DecimalUnit.nextLargerUnit(): DecimalUnit {
    val values = enumValues<DecimalUnit>()
    val nextOrdinal = (ordinal + 1)
    return values[nextOrdinal]
}

// convenience wrapper for "value + unit"
data class Sized<U>(val value: Double, val unit: U)