kbytes
======

Tiny, fluent Kotlin DSL for working with byte counts using binary (IEC) and decimal units.

Features
--------
- Inline `StorageSize` value type with arithmetic and comparisons.
- Binary (`KiB`, `MiB`, …) and decimal (`KB`, `MB`, …) extensions on `Int`/`Long`.
- Converters for byte counts and `Path` sizes (`toStorageSize()`).

Gradle setup (JitPack)
----------------------
1) Add it in your settings.gradle.kts at the end of repositories:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```
2) Depend on your GitHub fork/tag:
```kotlin
dependencies {
    implementation("com.github.No3x:kbytes:1.0.0")
}
```
Replace `1.0.0` with the tag you want.

Usage
-----
```kotlin
val size = 3.GB + 512.MB          // arithmetic
println(size.bestDecimalUnit())   // Sized(value=3.512, unit=GB)

val fileSize = somePath.toStorageSize()
println(fileSize.inBytes())
```
