package ua.pp.formatbce.musicassistant.ui.compose.settings

import cafe.adriel.voyager.core.model.ScreenModel
import ua.pp.formatbce.musicassistant.api.ServiceClient
import ua.pp.formatbce.musicassistant.api.ConnectionInfo
import ua.pp.formatbce.musicassistant.data.settings.SettingsRepository

class SettingsViewModel(
    private val apiClient: ServiceClient,
    settings: SettingsRepository
) : ScreenModel {

    val connectionInfo = settings.connectionInfo
    val connectionState = apiClient.connectionState
    val serverInfo = apiClient.serverInfo

    fun attemptConnection(host: String, port: String) {
        apiClient.connect(connection = ConnectionInfo(host, port.toInt()))
    }

    fun disconnect() {
        apiClient.disconnect()
    }
}