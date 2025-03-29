Feature: Repo List
Scenario: User can see the repo list
  Given I am on the repo list screen
  When I click the search button
  Then I should see the search screen