Feature: credential login with image validation

    #Normal flow
  Scenario: successful credential login after specifying the username and password with image validation
    Given I am on the "login" page with the username and password unspecified
    And I have a screenshot of the successful login page
    When I enter the username and password
    And I press "Login"
    Then I should be redirected to a page that is identical to the screenshot I have


    #Error flow
  Scenario: unsuccessful credential login after specifying invalid username and password with image validation
    Given I am on the "login" page with the username and password unspecified
    And I have a screenshot of the successful login page
    When I enter invalid username and password
    And I press "Login"
    Then I should be redirected to a page that is not identical to the screenshot I have
