package com.lifen.mygithubapp.ui.screen

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lifen.mygithubapp.auth.GitHubAuthManager
import com.lifen.mygithubapp.network.OAuthConfig
import com.lifen.mygithubapp.viewmodel.GitHubAuthViewModel
import com.lifen.mygithubapp.viewmodel.GitHubAuthViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    gitHubAuthManager: GitHubAuthManager,
    navController: NavController
) {
    val url = OAuthConfig.AUTH_URL + "?client_id=" + OAuthConfig.CLIENT_ID +
            "&redirect_uri=" + OAuthConfig.REDIRECT_URI + "&scope=" + OAuthConfig.SCOPE
    var webView: WebView? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }

    val authViewModel: GitHubAuthViewModel = viewModel(
        factory = GitHubAuthViewModelFactory(
            gitHubAuthManager
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Github授权页") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            }
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                url: String
                            ): Boolean {
                                if (url.startsWith(OAuthConfig.REDIRECT_URI)) {
                                    val code = Uri.parse(url).getQueryParameter("code")
                                    code?.let {
                                        authViewModel.handleOauthCallback(code)
                                        navController.popBackStack()
                                    }
                                    return true
                                }
                                return super.shouldOverrideUrlLoading(view, url)
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                            }
                        }

                        loadUrl(url)
                        webView = this
                    }
                }
            )

            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}