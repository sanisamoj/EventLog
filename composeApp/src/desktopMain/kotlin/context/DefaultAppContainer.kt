package context

import data.models.interfaces.EventLoggerRepository
import data.models.interfaces.PreferencesRepository
import data.repository.DefaultEventRepository
import data.repository.DefaultPreferencesRepository
import network.EventLoggerApi

object DefaultAppContainer : AppContainer {
    override val eventRepository: EventLoggerRepository = DefaultEventRepository(EventLoggerApi)
    override val preferences: PreferencesRepository = DefaultPreferencesRepository()
}