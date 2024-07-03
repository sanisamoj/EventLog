package data.models.interfaces

import data.models.generics.UserPreferences

interface PreferencesRepository {
    fun savePreferences(preferences: UserPreferences)
    fun deletePreferences()
    fun saveToken(token: String)
    fun updateDarkMode(value: Boolean)
    fun updatePageSize(value: Int)
    fun getPreferences(): UserPreferences?
}