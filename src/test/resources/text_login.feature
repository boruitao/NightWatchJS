Feature: credential login with text validation

    #Normal flow
  Scenario: successful credential login after specifying the username and password with text validation
    Given I am on the "login" page with the username and password unspecified
    When I enter the username and password
    And I press "Login"
    Then I should be redirected to a page with the "WELCOME" message displayed


    #Error flow
  Scenario: unsuccessful credential login after specifying invalid username and password with text validation
    Given I am on the "login" page with the username and password unspecified
    When I enter invalid username and password
    And I press "Login"
    Then I should be redirected to a page with the "ACCESS DENIED!" message displayed
