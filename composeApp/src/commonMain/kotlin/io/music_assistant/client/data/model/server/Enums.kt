package io.music_assistant.client.data.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PlayerType {
    @SerialName("player") PLAYER,
    @SerialName("group") GROUP,
    @SerialName("stereo_pair") STEREO_PAIR
}

@Serializable
enum class PlayerState {
    @SerialName("idle") IDLE,
    @SerialName("paused") PAUSED,
    @SerialName("playing") PLAYING
}

@Serializable
enum class RepeatMode {
    @SerialName("off") OFF,
    @SerialName("one") ONE,
    @SerialName("all") ALL
}

@Serializable
enum class PlayerFeature {
    @SerialName("power") POWER,
    @SerialName("volume_set") VOLUME_SET,
    @SerialName("volume_mute") VOLUME_MUTE,
    @SerialName("pause") PAUSE,
    @SerialName("set_members") SET_MEMBERS,
    @SerialName("multi_device_dsp") MULTI_DEVICE_DSP,
    @SerialName("seek") SEEK,
    @SerialName("next_previous") NEXT_PREVIOUS,
    @SerialName("play_announcement") PLAY_ANNOUNCEMENT,
    @SerialName("enqueue") ENQUEUE,
    @SerialName("gapless_playback") GAPLESS_PLAYBACK,
    @SerialName("select_source") SELECT_SOURCE
}

@Serializable
enum class EventType {
    @SerialName("player_added") PLAYER_ADDED,
    @SerialName("player_updated") PLAYER_UPDATED,
    @SerialName("player_removed") PLAYER_REMOVED,
    @SerialName("player_settings_updated") PLAYER_SETTINGS_UPDATED,
    @SerialName("queue_added") QUEUE_ADDED,
    @SerialName("queue_updated") QUEUE_UPDATED,
    @SerialName("queue_items_updated") QUEUE_ITEMS_UPDATED,
    @SerialName("queue_time_updated") QUEUE_TIME_UPDATED,
    @SerialName("queue_settings_updated") QUEUE_SETTINGS_UPDATED,
    @SerialName("application_shutdown") SHUTDOWN,
    @SerialName("media_item_added") MEDIA_ITEM_ADDED,
    @SerialName("media_item_updated") MEDIA_ITEM_UPDATED,
    @SerialName("media_item_deleted") MEDIA_ITEM_DELETED,
    @SerialName("media_item_played") MEDIA_ITEM_PLAYED,
    @SerialName("providers_updated") PROVIDERS_UPDATED,
    @SerialName("player_config_updated") PLAYER_CONFIG_UPDATED,
    @SerialName("sync_tasks_updated") SYNC_TASKS_UPDATED,
    @SerialName("auth_session") AUTH_SESSION,
    @SerialName("builtin_player") BUILTIN_PLAYER,
    @SerialName("connected") CONNECTED,
    @SerialName("disconnected") DISCONNECTED,
    @SerialName("*") ALL
}

enum class BuiltinPlayerEventType {
    @SerialName("play") PLAY,
    @SerialName("pause") PAUSE,
    @SerialName("resume") RESUME,
    @SerialName("stop") STOP,
    @SerialName("mute") MUTE,
    @SerialName("unmute") UNMUTE,
    @SerialName("set_volume") SET_VOLUME,
    @SerialName("play_media") PLAY_MEDIA,
    @SerialName("timeout") TIMEOUT,
    @SerialName("power_off") POWER_OFF,
    @SerialName("power_on") POWER_ON,
}

//enum class AlbumType {
//    @SerialName("album") ALBUM,
//    @SerialName("single") SINGLE,
//    @SerialName("compilation") COMPILATION,
//    @SerialName("ep") EP,
//    @SerialName("unknown") UNKNOWN,
//}

enum class QueueOption {
    @SerialName("play") PLAY,
    @SerialName("replace") REPLACE,
    @SerialName("next") NEXT,
    //@SerialName("replace_next") REPLACE_NEXT,
    @SerialName("add") ADD,
}