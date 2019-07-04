Feature: Create project
  Scenario: Admin creates project and system gives it a unique ID
    Given that the administrator is logged in1
    And the year is "2019"
    And there is a project with name "Project Test1" and one with name "Project Test2"
    When the administrator adds the two projects
    Then a project with name "Project Test1" and id "201900", and a project with name "Project Test2" and id "201901" are created
    And the two projects are in the system

  Scenario: Admin creates project a year after and the system gives it a unique id
    Given that the administrator is logged in1
    And the year is now "2020"
    And there is a project with name "Project Test1" and one with name "Project Test2"
    When the administrator adds the two projects
    Then a project with name "Project Test1" and id "202000", and a project with name "Project Test2" and id "202001" are created
    And the two projects are in the system

  Scenario: Employee not logged in as admin creates a project
    Given the employee is logged in
    And the employee is not the admin
    And there is a project with name "Project Test1"
    When he adds the project
    Then an errorMessage with text "You are not authorized to perform this action" occurss
    And the project is not added

