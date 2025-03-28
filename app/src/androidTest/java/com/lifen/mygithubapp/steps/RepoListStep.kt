package com.lifen.mygithubapp.steps

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.lifen.mygithubapp.model.AuthState
import com.lifen.mygithubapp.ui.screen.RepoListScreen
import io.cucumber.java.en.Given

class RepoListStep {

    private val composeTestRule = createComposeRule()

    @Given("I am on the repo list screen")
    fun showRepoListScreen() {
        composeTestRule.setContent {
            RepoListScreen(
                naviController = rememberNavController(),
                authState = AuthState(isLoggedIn = false),
                onLogout = {}
            )
        }
    }
}