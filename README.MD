kbytes
======

Tiny, fluent Kotlin DSL for working with byte counts using binary (IEC) and decimal units. Includes JUnit 5 helpers for locale-sensitive formatting tests.

Features
--------
- Inline `StorageSize` value type with arithmetic and comparisons.
- Binary (`KiB`, `MiB`, …) and decimal (`KB`, `MB`, …) extensions on `Int`/`Long`.
- Converters for byte counts and `Path` sizes (`toStorageSize()`).

Gradle setup (JitPack)
----------------------
1) Add the JitPack repository:
```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}
```
2) Depend on your GitHub fork/tag:
```kotlin
dependencies {
    implementation("com.github.No3x:kbytes:1.0.0")
}
```
Replace `No3x` with the GitHub owner of your repository and `1.0.0` with the tag/commit you publish.

Usage
-----
```kotlin
val size = 3.GB + 512.MB          // arithmetic
println(size.bestDecimalUnit())   // Sized(value=3.51, unit=GB)

val fileSize = somePath.toStorageSize()
println(fileSize.inBytes())
```
