package com.lifen.mygithubapp.steps

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.lifen.mygithubapp.model.AuthState
import com.lifen.mygithubapp.ui.screen.RepoListScreen
import com.lifen.mygithubapp.ui.screen.SearchScreen
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

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

    @When("I click the search button")
    fun clickSearchButton() {
        composeTestRule.onNodeWithText("SearchButton").performClick()
    }

    @Then("I should see the search screen")
    fun seeSearchScreen() {
        composeTestRule.setContent {
            SearchScreen(
                naviController = rememberNavController(),
                authState = AuthState(isLoggedIn = false)
            )
        }
    }
}