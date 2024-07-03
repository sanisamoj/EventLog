package context

import data.models.interfaces.EventLoggerRepository
import data.models.interfaces.PreferencesRepository

interface AppContainer {
    val eventRepository: EventLoggerRepository
    val preferences: PreferencesRepository
}