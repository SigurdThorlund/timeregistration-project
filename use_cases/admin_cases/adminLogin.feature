Feature: Administrator logs in
  Scenario: The administrator logs in
    Given that the administrator is not logged in
    And the system has an administrator with id "ADMI"
    When the administrator logs in
    Then the administrator is the sessionUser

  Scenario: The administrator logs out
    Given that the administrator is logged in
    When the administrator logs out
    Then the administrator is no longer logged in
    And there is no sessionUser