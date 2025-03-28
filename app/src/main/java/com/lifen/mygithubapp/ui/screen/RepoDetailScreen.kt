package com.lifen.mygithubapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.lifen.mygithubapp.data.GitHubRepoManager
import com.lifen.mygithubapp.model.AuthState
import com.lifen.mygithubapp.model.RepoItemState
import com.lifen.mygithubapp.ui.view.ErrorState
import com.lifen.mygithubapp.viewmodel.GitHubRepoViewModel
import com.lifen.mygithubapp.viewmodel.GitHubRepoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailScreen(
    naviController: NavController,
    authState: AuthState,
    owner: String?,
    repo: String?
) {
    val viewModel: GitHubRepoViewModel = viewModel(
        factory = GitHubRepoViewModelFactory(
            GitHubRepoManager()
        )
    )
    val uiState by viewModel.ownerRepoUiState.collectAsState()
    var hasRequested by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(authState) {
        if (!hasRequested) {
            hasRequested = true
            viewModel.getRepoDetails(authState, owner ?: "", repo ?: "")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Repo Detail") },
            navigationIcon = {
                IconButton(onClick = { naviController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            }
        )

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                ErrorState(message = uiState.error ?: "Unknown Error!")
            }

            else -> {
                uiState.repo?.let { repo ->
                    RepositoryItem(repo = repo)
                }
            }
        }
    }
}

@Composable
private fun RepositoryItem(
    repo: RepoItemState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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