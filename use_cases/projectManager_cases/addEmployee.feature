Feature: Add Employee to project
  Description: The projectmanager adds/removes an employee to the
  project

  Scenario: Project manager adds a employee to the project
  succesfully
    Given the software system is initialized
    And the projectManager is logged in
    And the employee is not part of the project
    When the project leader adds the employee to the project
    Then the employee is succesfully added to the project

  Scenario: Project manager adds an employee that is already
  active in another project
    Given the software system is initialized
    And the projectManager is logged in
    And the employee is not part of the project
    And the employee is part of another project
    When the project leader adds the employee to the project
    Then the employee is not added to the project

  Scenario: Project manager adds an employee that is already
  active in the project
    Given the software system is initialized
    And the projectManager is logged in
    And the project leader adds the employee to the project
    When the project leader adds the employee to the project again
    Then an errormessage with "Employee already in project" occurs

  Scenario: Project manager removes employee from the project
    Given the software system is initialized
    And an Employee with ID = 'EONE' is in the project
    When the project leader removes him from the project
    Then the employee is no longer part of the project

