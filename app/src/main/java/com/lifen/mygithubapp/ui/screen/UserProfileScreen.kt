package com.lifen.mygithubapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lifen.mygithubapp.data.GitHubRepoManager
import com.lifen.mygithubapp.model.AuthState
import com.lifen.mygithubapp.ui.ErrorState
import com.lifen.mygithubapp.ui.RepoList
import com.lifen.mygithubapp.viewmodel.GitHubRepoViewModel
import com.lifen.mygithubapp.viewmodel.GitHubRepoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    naviController: NavController,
    authState: AuthState
) {

    val viewModel: GitHubRepoViewModel = viewModel(
        factory = GitHubRepoViewModelFactory(
            GitHubRepoManager()
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(authState) {
        viewModel.getUserRepositories(authState)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("User Repos") },
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
                RepoList(
                    repositories = uiState.repos,
                    modifier = Modifier.fillMaxSize(),
                    onItemClick = { item ->
                        naviController.navigate("repoDetail/${item.owner.login}/${item.name}")
                    }
                )
            }
        }
    }
}