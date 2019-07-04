Feature: Employee login
  Scenario: An employee logs in succesfully
    Given no one is logged in
    And the system has an employee with id "EMP1"
    When the employee logs in
    Then the employee is now the session user

  Scenario: An employee logs in with wrong ID
    Given no one is logged in
    And the system does not have an employee with id "EMP2"
    When the employee logs in with id "EMP2"
    Then an errormessage with text "ID is not in the system" occurs
    And the employee is not logged in
