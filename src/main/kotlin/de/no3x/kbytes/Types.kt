package de.no3x.kbytes

import kotlin.math.abs
import java.math.BigDecimal

@JvmInline
value class StorageSize(val bytes: Long) : Comparable<StorageSize> {

    companion object {
        fun ofBytes(bytes: Long) = StorageSize(bytes)

        fun of(value: Long, unit: BinaryUnit): StorageSize =
            StorageSize(value * unit.bytes)

        fun of(value: Long, unit: DecimalUnit): StorageSize =
            StorageSize(value * unit.bytes)

        fun of(value: Double, unit: BinaryUnit): StorageSize =
            fromExactDouble(value, unit.bytes)

        fun of(value: Double, unit: DecimalUnit): StorageSize =
            fromExactDouble(value, unit.bytes)

        private fun fromExactDouble(value: Double, unitBytes: Long): StorageSize {
            val bytes = BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(unitBytes))
                .longValueExact() // fail fast if fractional bytes would be required
            return StorageSize(bytes)
        }
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

    override fun toString(): String {
        val best = bestDecimalUnit()
        return "%.2f %s".format(best.value, best.unit.symbol)
    }
}

fun kibibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.KIBIBYTE)

fun mebibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.MEBIBYTE)

fun gibibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.GIBIBYTE)

fun tebibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.TEBIBYTE)

fun pebibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.PEBIBYTE)

fun exbibyte(value: Long): StorageSize =
    StorageSize.of(value, BinaryUnit.EXBIBYTE)

fun kilobyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.KILOBYTE)

fun megabyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.MEGABYTE)

fun gigabyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.GIGABYTE)

fun terabyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.TERABYTE)

fun petabyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.PETABYTE)

fun exabyte(value: Long): StorageSize =
    StorageSize.of(value, DecimalUnit.EXABYTE)

// --- Nice Kotlin DSL-style extensions ---------------------------------------

val Int.KiB: StorageSize get() = kibibyte(this.toLong())
val Int.MiB: StorageSize get() = mebibyte(this.toLong())
val Int.GiB: StorageSize get() = gibibyte(this.toLong())
val Int.TiB: StorageSize get() = tebibyte(this.toLong())
val Int.PiB: StorageSize get() = pebibyte(this.toLong())
val Int.EiB: StorageSize get() = exbibyte(this.toLong())

val Int.KB: StorageSize get() = kilobyte(this.toLong())
val Int.MB: StorageSize get() = megabyte(this.toLong())
val Int.GB: StorageSize get() = gigabyte(this.toLong())
val Int.TB: StorageSize get() = terabyte(this.toLong())
val Int.PB: StorageSize get() = petabyte(this.toLong())
val Int.EB: StorageSize get() = exabyte(this.toLong())

val Long.KiB: StorageSize get() = kibibyte(this)
val Long.MiB: StorageSize get() = mebibyte(this)
val Long.GiB: StorageSize get() = gibibyte(this)
val Long.TiB: StorageSize get() = tebibyte(this)
val Long.PiB: StorageSize get() = pebibyte(this)
val Long.EiB: StorageSize get() = exbibyte(this)

val Long.KB: StorageSize get() = kilobyte(this)
val Long.MB: StorageSize get() = megabyte(this)
val Long.GB: StorageSize get() = gigabyte(this)
val Long.TB: StorageSize get() = terabyte(this.toLong())
val Long.PB: StorageSize get() = petabyte(this.toLong())
val Long.EB: StorageSize get() = exabyte(this.toLong())
