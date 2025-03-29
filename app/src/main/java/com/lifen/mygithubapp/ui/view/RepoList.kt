package com.lifen.mygithubapp.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.lifen.mygithubapp.model.RepoItemState
import kotlinx.coroutines.flow.debounce

@Composable
fun RepoList(
    repositories: List<RepoItemState>,
    modifier: Modifier = Modifier,
    onItemClick: (RepoItemState) -> Unit,
    onLoadMore: () -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
) {

    var isLoading = remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .debounce(1000)
            .collect { visibleItemsInfos ->
                if (visibleItemsInfos.isNotEmpty() && visibleItemsInfos.last().index >= listState.layoutInfo.totalItemsCount - 1 && !isLoading.value) {
                    isLoading.value = true
                    onLoadMore()
                    isLoading.value = false
                }
            }
    }

    LazyColumn(modifier = modifier, state = listState) {
        items(repositories) { it ->
            RepositoryItem(repo = it, onItemClick = { onItemClick(it) })
        }
    }
}