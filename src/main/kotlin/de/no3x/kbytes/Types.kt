package de.no3x.kbytes

import kotlin.math.abs

@JvmInline
value class StorageSize private constructor(val bytes: Long) : Comparable<StorageSize> {

    companion object {
        fun ofBytes(bytes: Long) = StorageSize(bytes)

        fun of(value: Long, unit: BinaryUnit): StorageSize =
            StorageSize(value * unit.bytes)

        fun of(value: Long, unit: DecimalUnit): StorageSize =
            StorageSize(value * unit.bytes)
    }

    // Arithmetic (mirrors add/subtract/multiply/divide)
    operator fun plus(other: StorageSize): StorageSize =
        StorageSize(bytes + other.bytes)

    operator fun minus(other: StorageSize): StorageSize =
        StorageSize(bytes - other.bytes)

    operator fun times(factor: Long): StorageSize =
        StorageSize(bytes * factor)

    operator fun div(divisor: Long): StorageSize =
        StorageSize(bytes / divisor)

    // Comparisons / equality by bytes
    override fun compareTo(other: StorageSize): Int =
        bytes.compareTo(other.bytes)

    // Conversions
    fun inBytes(): Long = bytes

    fun toBinary(unit: BinaryUnit): Double =
        bytes.toDouble() / unit.bytes.toDouble()

    fun toDecimal(unit: DecimalUnit): Double =
        bytes.toDouble() / unit.bytes.toDouble()

    // "best fitting" units – similar to StorageUnits.binaryValueOf/decimalValueOf
    fun bestBinaryUnit(): Sized<BinaryUnit> {
        val absBytes = abs(bytes)
        val unit = BinaryUnit.entries.toTypedArray()
            .lastOrNull { absBytes >= it.bytes } ?: BinaryUnit.BYTE

        val value = bytes.toDouble() / unit.bytes.toDouble()
        return Sized(value, unit)
    }

    fun bestDecimalUnit(): Sized<DecimalUnit> {
        val absBytes = abs(bytes)
        val unit = DecimalUnit.entries.toTypedArray()
            .lastOrNull { absBytes >= it.bytes } ?: DecimalUnit.BYTE

        val value = bytes.toDouble() / unit.bytes.toDouble()
        return Sized(value, unit)
    }

    // Simple human-readable default (you said you don't strictly need it,
    // but it's handy and mirrors the Java lib behaviour)
    override fun toString(): String {
        val best = bestDecimalUnit()
        return "%.2f %s".format(best.value, best.unit.symbol)
    }
}

// --- Kotlin-friendly factories (like StorageUnits.kibibyte(...)) -------------

fun kibibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.KIBIBYTE)

fun mebibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.MEBIBYTE)

fun gibibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.GIBIBYTE)

fun kilobyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.KILOBYTE)

fun megabyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.MEGABYTE)

fun gigabyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.GIGABYTE)

// --- Nice Kotlin DSL-style extensions ---------------------------------------

val Int.KiB: StorageSize get() = kibibyte(this.toLong())
val Int.MiB: StorageSize get() = mebibyte(this.toLong())
val Int.GiB: StorageSize get() = gibibyte(this.toLong())

val Int.KB: StorageSize get() = kilobyte(this.toLong())
val Int.MB: StorageSize get() = megabyte(this.toLong())
val Int.GB: StorageSize get() = gigabyte(this.toLong())

val Long.KiB: StorageSize get() = kibibyte(this)
val Long.MiB: StorageSize get() = mebibyte(this)
val Long.GiB: StorageSize get() = gibibyte(this)

val Long.KB: StorageSize get() = kilobyte(this)
val Long.MB: StorageSize get() = megabyte(this)
val Long.GB: StorageSize get() = gigabyte(this)