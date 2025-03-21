package com.nahadayka.model.repositories

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun setLanguage(language: String) : Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            changeLanguageForAPI33(context, locale)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            changeLanguageForAPI24(context, locale)
        } else {
            changeLanguageForLegacy(context, locale)
        }
    }
    private fun changeLanguageForAPI33(context: Context, locale: Locale) : Context{
        val config = context.resources.configuration
        config.setLocales(android.os.LocaleList(locale))
        return context.createConfigurationContext(config)
    }
    private fun changeLanguageForAPI24(context: Context, locale: Locale) : Context{
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
    private fun changeLanguageForLegacy(context: Context, locale: Locale) : Context{
        val resources = context.resources
        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        return context
    }
    fun updateAppLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.applicationContext.resources.configuration.setLocales(android.os.LocaleList(Locale.getDefault()))
        } else {
            val config = Configuration(context.applicationContext.resources.configuration)
            config.setLocale(Locale.getDefault())
            context.applicationContext.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}
