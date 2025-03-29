package com.lifen.mygithubapp.ui.screen

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lifen.mygithubapp.data.GitHubRepoManager
import com.lifen.mygithubapp.model.AuthState
import com.lifen.mygithubapp.ui.view.RepoList
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
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = searchQuery,
                selection = TextRange(searchQuery.length)
            )
        )
    }

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
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                searchQuery = textFieldValue.text
            },
            placeholder = { Text("Input language") },  // 提示文字
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    viewModel.resetPage()
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
                    viewModel.resetPage()
                    viewModel.searchRepositories(authState, "language:$searchQuery")
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                enabled = searchQuery.isNotEmpty()
            ) {
                Text("Confirm")
            }
        }

        if (uiState.isLoading) {
            Toast.makeText(LocalContext.current, "Loading...", Toast.LENGTH_SHORT).show()
        } else if (uiState.error != null) {
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
        }

        RepoList(
            repositories = uiState.repos,
            modifier = Modifier.fillMaxSize(),
            onItemClick = { item ->
                naviController.navigate("repoDetail/${item.owner.login}/${item.name}")
            },
            onLoadMore = {
                viewModel.searchRepositories(authState, "language:$searchQuery")
            }
        )
    }
}