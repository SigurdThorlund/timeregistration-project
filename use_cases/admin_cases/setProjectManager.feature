Feature: Admin sets the projectmanager
  Actors: Admin

  Scenario: The admin sets an employee not in the project as projectmanager successfully
    Given the system has a project with name "Project Test1" and id "201900"
    And the project has no projectLeader
    And and the system has an employee with id "EMP1"
    And the administrator is logged in
    When the admin sets the employee as the projectmanager
    Then the employee is now the projectmanager of the project
    And the employee is added to the project

  Scenario: The admin sets an employee not in the project as projectmanager successfully
    Given the system has a project with name "Project Test1" and id "201900"
    And the project has no projectLeader
    And and the system has an employee with id "EMP1"
    And the employee is logged   in
    When the employee sets the employee as the projectmanager
    Then an errormessage is printed with text "You are not authorized to perform this action"
    And the employee is then not added to the project

  Scenario: The admin sets an employee who is already in the project as projectManager successfully
    Given the system has a project with name "Project Test1" and id "201900"
    And the project has no projectLeader
    And and the project has an employee with id "EMP1"
    And the administrator is logged in
    When the admin sets the employee as the projectmanager
    Then the employee is now the projectmanager of the project
    And the employee is not added to the project again

  Scenario: The admin sets an employee as projectManager on a project that already has a projectManager
    Given the system has a project with name "Project Test1" and id "201900"
    And the project has a projectManager
    And and the system has an employee with id "EMP1"
    And the administrator is logged in
    When the admin tries to set the employee as the projectmanager
    Then an errormessage is printed with text "This project already has a projectmanager"
    And the employee is not added to the projectt

  Scenario: The admin sets an employee as projectManager that is part of another project
    Given the system has a project with name "Project Test1" and id "201900"
    And the project has no projectLeader
    And and the system has an employee with id "EMP1"
    And the employee is part of another project in the system
    When the admin tries to set the employee as the projectmanager
    Then an errormessage is printed with text "Employee is already in another project"
    And the employee is not added to the projectt

