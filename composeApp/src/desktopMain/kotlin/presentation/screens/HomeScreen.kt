package presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import data.models.generics.EventSeverity
import data.models.generics.EventType
import data.models.generics.HomeUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource
import presentation.components.CircularLoading
import presentation.components.FiltersRow
import presentation.components.LogEventCard
import presentation.viewmodels.HomeViewModel
import sanisamoj.eventlogger.library.resources.Res
import sanisamoj.eventlogger.library.resources.alert
import sanisamoj.eventlogger.library.resources.gpp_maybe_24dp_FILL0_wght400_GRAD0_opsz24

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    unreadPage: Boolean = true,
    pageSize: Int = 30
) {
    val homeUiStateData by homeViewModel.uiState.collectAsState()

    var filterType by remember { mutableStateOf<EventType?>(null) }
    var filterSeverity by remember { mutableStateOf<EventSeverity?>(null) }
    var expandedType by remember { mutableStateOf(false) }
    var expandedSeverity by remember { mutableStateOf(false) }

    var listState = rememberLazyGridState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                val totalItems = listState.layoutInfo.totalItemsCount
                // Carregar mais itens se estiver nos últimos 20% da lista
                val threshold = (totalItems * 0.8).toInt()
                val lastVisibleItem = visibleItems.lastOrNull()?.index ?: 0
                lastVisibleItem >= threshold
            }
            .distinctUntilChanged()
            .collect { isNearEnd  ->
                if (isNearEnd ) {
                    if (unreadPage) {
                        homeViewModel.loadAllUnreadLogs(pageSize)
                    } else {
                        homeViewModel.loadAllReadEventLogs(pageSize)
                    }
                }
            }
    }

    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
    ) {
        when (homeViewModel.homeUiState) {
            is HomeUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    FiltersRow(
                        filterType,
                        { expandedType = true },
                        {
                            filterType = it
                            homeViewModel.filterType = filterType
                        },
                        expandedType,
                        { expandedType = it },
                        filterSeverity,
                        { expandedSeverity = true },
                        {
                            filterSeverity = it
                            homeViewModel.filterSeverity = filterSeverity
                        },
                        expandedSeverity,
                        { expandedSeverity = it }
                    )

                    val logs = if (unreadPage) {
                        homeUiStateData.unreadLogEvents
                    } else {
                        homeUiStateData.readLogEvents
                    }.filter { log ->
                        (filterType == null || log.eventType == filterType) &&
                                (filterSeverity == null || log.severity == filterSeverity)
                    }

                    if(logs.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painterResource(Res.drawable.alert),
                                    contentDescription = "No Logs",
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "No Logs Available",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            state = listState,
                            columns = GridCells.Adaptive(minSize = 350.dp),
                        ) {
                            if (unreadPage) {
                                items(homeUiStateData.unreadLogEvents.filter { log ->
                                    (filterType == null || log.eventType == filterType) &&
                                            (filterSeverity == null || log.severity == filterSeverity)
                                }, key = { it.id }) { log ->
                                    Box(modifier = Modifier.animateItemPlacement()) {
                                        LogEventCard(
                                            logEvent = log,
                                            onReadPress = {
                                                homeViewModel.read(log.id)
                                            }
                                        )
                                    }
                                }
                            } else {
                                items(homeUiStateData.readLogEvents.filter { log ->
                                    (filterType == null || log.eventType == filterType) &&
                                            (filterSeverity == null || log.severity == filterSeverity)
                                }, key = { it.id }) { log ->
                                    Box(modifier = Modifier.animateItemPlacement()) {
                                        LogEventCard(
                                            logEvent = log
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }

            is HomeUiState.Loading -> {
                CircularLoading()
            }

            is HomeUiState.Error -> {
                NoInternetScreen(
                    onRetryClick = {
                        homeViewModel.homeUiState = HomeUiState.Loading
                        homeViewModel.eraseAllLogEvents()
                        if (unreadPage) {
                            homeViewModel.loadAllUnreadLogs(pageSize)
                        } else {
                            homeViewModel.loadAllReadEventLogs(pageSize)
                        }
                    }
                )
            }
        }
    }
}


