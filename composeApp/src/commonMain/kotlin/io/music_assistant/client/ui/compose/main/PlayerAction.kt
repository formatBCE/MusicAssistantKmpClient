package io.music_assistant.client.ui.compose.main

import io.music_assistant.client.data.model.server.RepeatMode

sealed class PlayerAction {
    data object TogglePlayPause : PlayerAction()
    data object Next : PlayerAction()
    data object Previous : PlayerAction()
    data object VolumeUp : PlayerAction()
    data object VolumeDown : PlayerAction()
    data class ToggleShuffle(val current: Boolean) : PlayerAction()
    data class ToggleRepeatMode(val current: RepeatMode) : PlayerAction()
    data class SeekTo(val pos: Long) : PlayerAction()
}