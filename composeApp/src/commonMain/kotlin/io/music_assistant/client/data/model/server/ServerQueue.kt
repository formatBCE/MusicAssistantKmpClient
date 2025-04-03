package io.music_assistant.client.data.model.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerQueue(
    @SerialName("queue_id") val queueId: String,
    //@SerialName("active") val active: Boolean,
    //@SerialName("display_name") val displayName: String,
    @SerialName("available") val available: Boolean,
    //@SerialName("items") val items: Int,
    @SerialName("shuffle_enabled") val shuffleEnabled: Boolean,
    @SerialName("repeat_mode") val repeatMode: RepeatMode,
    //@SerialName("dont_stop_the_music_enabled") val dontStopTheMusicEnabled: Boolean,
    //@SerialName("current_index") val currentIndex: Int? = null,
    //@SerialName("index_in_buffer") val indexInBuffer: Int? = null,
    @SerialName("elapsed_time") val elapsedTime: Double? = null,
    //@SerialName("elapsed_time_last_updated") val elapsedTimeLastUpdated: Double,
    //@SerialName("state") val state: PlayerState,
    @SerialName("current_item") val currentItem: ServerQueueItem? = null,
    //@SerialName("next_item") val nextItem: QueueItem? = null,
    //@SerialName("radio_source") val radioSource: List<String>,
    //@SerialName("flow_mode") val flowMode: Boolean,
    //@SerialName("resume_pos") val resumePos: Double?
)