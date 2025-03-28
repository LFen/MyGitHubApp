package com.lifen.mygithubapp.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lifen.mygithubapp.model.RepoItemState

@Composable
fun RepositoryItem(
    repo: RepoItemState,
    onItemClick: (RepoItemState) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), onClick = { onItemClick(repo) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = repo.owner.avatarUrl,
                contentDescription = null,  // 无障碍描述（必填）
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = repo.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = repo.description ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "★ ${repo.stars}", style = MaterialTheme.typography.labelSmall)
                Text(text = repo.language ?: "", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}