package com.lifen.mygithubapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
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
fun SearchScreen(
    authState: AuthState,
    naviController: NavController
) {
    val viewModel: GitHubRepoViewModel = viewModel(
        factory = GitHubRepoViewModelFactory(
            GitHubRepoManager()
        )
    )
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Search Repos by Language") },
            navigationIcon = {
                IconButton(onClick = { naviController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            }
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Input language") },  // 提示文字
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    viewModel.searchRepositories(authState, "language:$searchQuery")
                }
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 4.dp)
        ) {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.searchRepositories(authState, "language:$searchQuery")
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                enabled = searchQuery.isNotEmpty()
            ) {
                Text("Confirm")
            }
        }

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