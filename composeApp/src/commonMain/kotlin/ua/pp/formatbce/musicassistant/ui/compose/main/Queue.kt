package ua.pp.formatbce.musicassistant.ui.compose.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Play
import compose.icons.tablericons.ClipboardX
import compose.icons.tablericons.GripVertical
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import ua.pp.formatbce.musicassistant.data.model.server.events.QueueItem
import ua.pp.formatbce.musicassistant.data.source.PlayerData
import ua.pp.formatbce.musicassistant.utils.toMinSec
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Queue(
    nestedScrollConnection: NestedScrollConnection,
    playerData: PlayerData,
    items: List<QueueItem>,
    chosenItemsIds: Set<String>?,
    enabled: Boolean,
    queueAction: (QueueAction) -> Unit,
    onItemChosenChanged: (String) -> Unit,
    onChosenItemsClear: () -> Unit
) {

    var internalItems by remember(items) { mutableStateOf(items) }
    var dragEndIndex by remember { mutableStateOf<Int?>(null) }
    val listState = rememberLazyListState()
    val currentIndex = internalItems.indexOfFirst {
        it.queueItemId == playerData.queue?.currentItem?.queueItemId
    }
    val reorderableLazyListState = rememberReorderableLazyListState(listState) { from, to ->
        if (to.index <= currentIndex) {
            return@rememberReorderableLazyListState
        }
        internalItems = internalItems.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        dragEndIndex = to.index
    }
    val coroutineScope = rememberCoroutineScope()
    val queueInfo = "${currentIndex + 1}/${internalItems.size}"
    val isInChooseMode = (chosenItemsIds?.size ?: 0) > 0
    LaunchedEffect(playerData.queue?.currentItem?.queueItemId) {
        if (currentIndex != -1) {
            val targetIndex = maxOf(0, currentIndex - 2) // Ensure it doesn't go negative
            listState.animateScrollToItem(targetIndex)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(4.dp)
            .alpha(if (enabled) 1f else 0.5f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
    if (chosenItemsIds?.isNotEmpty() == true) {
        val chosenItems = items.filter { chosenItemsIds.contains(it.queueItemId) }
        QueueTrackControls(
            chosenItems = chosenItems,
            enabled = enabled,
            queueAction = { queueAction(it) },
            onChosenItemsClear = onChosenItemsClear
        )
    } else {
            Text(
                text = "Queue ($queueInfo)",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold
            )
            playerData.queue?.let {
                Icon(
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .clickable(enabled = enabled) { queueAction(QueueAction.ClearQueue(it.queueId)) }
                        .size(24.dp)
                        .padding(all = 2.dp)
                        .align(alignment = Alignment.CenterVertically),
                    imageVector = TablerIcons.ClipboardX,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.onSecondary)
            .nestedScroll(nestedScrollConnection)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        listState.scrollBy(-delta)
                    }
                },
            )
            .alpha(if (enabled) 1f else 0.5f),
        state = listState,
    ) {
        itemsIndexed(items = internalItems, key = { _, item -> item.queueItemId }) { index, item ->
            val isCurrent = item.queueItemId == playerData.queue?.currentItem?.queueItemId
            val isChosen = chosenItemsIds?.contains(item.queueItemId) == true
            val isPlayed = index < currentIndex
            ReorderableItem(
                state = reorderableLazyListState,
                key = item.queueItemId,
                enabled = enabled,
            ) {
                Row(
                    modifier = Modifier
                        .alpha(if (isPlayed && !isChosen) 0.5f else 1f)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(
                            when {
                                isChosen -> MaterialTheme.colors.primary
                                else -> Color.Transparent
                            }
                        )
                        .combinedClickable(
                            enabled = enabled,
                            onClick = {
                                if (isInChooseMode) {
                                    onItemChosenChanged(item.queueItemId)
                                } else if (!isCurrent) {
                                    queueAction(
                                        QueueAction.PlayQueueItem(
                                            item.queueId, item.queueItemId
                                        )
                                    )
                                }
                            },
                            onLongClick = {
                                onItemChosenChanged(item.queueItemId)
                            },
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (isCurrent) {
                        Icon(
                            modifier = Modifier.padding(end = 38.dp).size(18.dp),
                            imageVector = FontAwesomeIcons.Solid.Play,
                            contentDescription = null,
                            tint = when {
                                isChosen -> MaterialTheme.colors.onPrimary
                                else -> MaterialTheme.colors.secondary
                            },
                        )
                    } else {
                        Text(
                            modifier = Modifier.padding(end = 8.dp).width(48.dp),
                            text = "${index + 1}:",
                            color = when {
                                isChosen -> MaterialTheme.colors.onPrimary
                                else -> MaterialTheme.colors.secondary
                            },
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${item.mediaItem.trackDescription} " +
                                "(${item.duration?.toInt()?.seconds.toMinSec()})",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = when {
                            isChosen -> MaterialTheme.colors.onPrimary
                            else -> MaterialTheme.colors.secondary
                        },
                        style = MaterialTheme.typography.body2,
                        fontWeight = when {
                            isCurrent -> FontWeight.Bold
                            else -> FontWeight.Normal
                        }
                    )
                    if (chosenItemsIds?.isNotEmpty() == false && !isCurrent && !isPlayed) {
                        Icon(
                            modifier = Modifier
                                .draggableHandle(
                                    onDragStopped = {
                                        dragEndIndex?.let { to ->
                                            queueAction(
                                                QueueAction.MoveItem(
                                                    item.queueId,
                                                    item.queueItemId,
                                                    from = index,
                                                    to = to
                                                )
                                            )
                                        }
                                    }
                                )
                                .size(16.dp),
                            imageVector = TablerIcons.GripVertical,
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary,
                        )
                    }
                }
            }
        }
    }
}