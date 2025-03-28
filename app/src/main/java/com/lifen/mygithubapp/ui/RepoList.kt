package com.lifen.mygithubapp.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lifen.mygithubapp.model.RepoItemState

@Composable
fun RepoList(
    repositories: List<RepoItemState>,
    modifier: Modifier = Modifier,
    onItemClick: (RepoItemState) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(repositories) { it ->
            RepositoryItem(repo = it, onItemClick = { onItemClick(it) })
        }
    }
}