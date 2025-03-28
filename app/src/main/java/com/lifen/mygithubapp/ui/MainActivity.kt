package com.lifen.mygithubapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lifen.mygithubapp.auth.AuthManager
import com.lifen.mygithubapp.model.AuthState
import com.lifen.mygithubapp.ui.screen.RepoDetailScreen
import com.lifen.mygithubapp.ui.screen.RepoListScreen
import com.lifen.mygithubapp.ui.screen.SearchScreen
import com.lifen.mygithubapp.ui.screen.UserProfileScreen
import com.lifen.mygithubapp.ui.screen.WebViewScreen
import com.lifen.mygithubapp.ui.theme.MyGithubAppTheme
import com.lifen.mygithubapp.viewmodel.GitHubAuthViewModel
import com.lifen.mygithubapp.viewmodel.GitHubAuthViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authManager = AuthManager(this)

        setContent {
            MyGithubAppTheme {
                AppNavi(authManager)
            }
        }
    }
}

@Composable
fun AppNavi(authManager: AuthManager) {
    val naviController = rememberNavController()
    val authState by authManager.authStatus.collectAsState(initial = AuthState(isLoggedIn = false))
    NavHost(navController = naviController, startDestination = "repo_list_screen") {
        composable("repo_list_screen") {
            val authViewModel: GitHubAuthViewModel = viewModel(
                factory = GitHubAuthViewModelFactory(
                    authManager
                )
            )
            RepoListScreen(
                naviController = naviController,
                authState = authState,
                onLogout = { authViewModel.logout() }
            )
        }
        composable("search_screen") {
            SearchScreen(
                authState = authState,
                naviController = naviController
            )
        }
        composable("repoDetail/{owner}/{repo}") { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("owner")
            val repo = backStackEntry.arguments?.getString("repo")
            RepoDetailScreen(
                naviController = naviController,
                authState = authState,
                owner = owner,
                repo = repo
            )
        }
        composable("webview") { backStackEntry ->
            WebViewScreen(
                authManager = authManager,
                navController = naviController
            )
        }
        composable("user_profile_screen") {
            UserProfileScreen(
                naviController = naviController,
                authState = authState
            )
        }
    }
}