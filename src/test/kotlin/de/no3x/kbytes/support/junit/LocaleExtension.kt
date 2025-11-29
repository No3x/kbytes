package de.no3x.kbytes.support.junit

import org.junit.jupiter.api.extension.*
import java.util.*
import java.util.stream.Stream

/**
 * JUnit 5 extension that temporarily switches the default locale for a test method
 * annotated with [WithLocale]. For each language tag provided, the test is re-run
 * with that locale set as the default.
 */
class LocaleExtension : TestTemplateInvocationContextProvider {
    override fun supportsTestTemplate(context: ExtensionContext): Boolean =
        context.testMethod.map { it.isAnnotationPresent(WithLocale::class.java) }.orElse(false)

    override fun provideTestTemplateInvocationContexts(context: ExtensionContext): Stream<TestTemplateInvocationContext> {
        val annotation = context.requiredTestMethod.getAnnotation(WithLocale::class.java)
        val languageTags = if (annotation.value.isNotEmpty()) annotation.value else arrayOf(Locale.getDefault().toLanguageTag())

        return languageTags.asSequence()
            .map { tag -> LocaleTemplateContext(tag) as TestTemplateInvocationContext }
            .toList()
            .stream()
    }

    private class LocaleTemplateContext(private val languageTag: String) : TestTemplateInvocationContext {
        override fun getDisplayName(invocationIndex: Int): String = "locale=$languageTag"

        override fun getAdditionalExtensions(): MutableList<Extension> =
            mutableListOf(LocaleSettingCallback(languageTag))
    }

    private class LocaleSettingCallback(private val languageTag: String) : BeforeEachCallback, AfterEachCallback {
        private var previous: Locale? = null

        override fun beforeEach(context: ExtensionContext) {
            previous = Locale.getDefault()
            Locale.setDefault(Locale.forLanguageTag(languageTag))
        }

        override fun afterEach(context: ExtensionContext) {
            previous?.let { Locale.setDefault(it) }
            previous = null
        }
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
internal annotation class WithLocale(vararg val value: String)
