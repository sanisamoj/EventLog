package data.repository

import com.google.gson.Gson
import data.models.generics.UserPreferences
import data.models.interfaces.PreferencesRepository
import java.io.File


class DefaultPreferencesRepository: PreferencesRepository {
    private val preferencesFileName = "preferences.json"

    override fun savePreferences(preferences: UserPreferences) {
        val dirPath = getPreferencesDirectory()
        val dir = File(dirPath)
        if(!dir.exists()) { dir.mkdirs() }

        val filePath = "$dirPath/$preferencesFileName"
        val gson = Gson()
        val jsonString = gson.toJson(preferences)
        File(filePath).writeText(jsonString)
    }

    override fun deletePreferences() {
        val dirPath = getPreferencesDirectory()
        val filePath = "$dirPath/$preferencesFileName"
        val file = File(filePath)
        if(file.exists()) { file.delete() }
    }

    override fun saveToken(token: String) {
        val preferences = getPreferences() ?: createUserPreferences()
        val preferencesToSave = preferences.copy(token = token)
        savePreferences(preferencesToSave)
    }

    override fun updateDarkMode(value: Boolean) {
        val preferences = getPreferences() ?: createUserPreferences()
        val preferencesToSave = preferences.copy(darkMode = value)
        savePreferences(preferencesToSave)
    }

    override fun updatePageSize(value: Int) {
        val preferences = getPreferences() ?: createUserPreferences()
        val preferencesToSave = preferences.copy(pageSize = value)
        savePreferences(preferencesToSave)
    }

    override fun getPreferences(): UserPreferences? {
        val filePath = "${getPreferencesDirectory()}/$preferencesFileName"
        val file = File(filePath)
        if (!file.exists()) { return null }

        val jsonString = file.readText()
        val gson = Gson()
        return gson.fromJson(jsonString, UserPreferences::class.java)
    }

    private fun getPreferencesDirectory(): String {
        val os = System.getProperty("os.name").toLowerCase()
        val userHome = System.getProperty("user.home")
        return when {
            os.contains("win") -> "$userHome\\AppData\\Roaming\\EventLogger"
            os.contains("mac") -> "$userHome/Library/Application Support/EventLogger"
            os.contains("nux") -> "$userHome/.config/EventLogger"
            else -> userHome // fallback, shouldn't be used in practice
        }
    }

    private fun createUserPreferences(preferences: UserPreferences = UserPreferences()): UserPreferences {
        savePreferences(preferences)
        return getPreferences()!!
    }
}