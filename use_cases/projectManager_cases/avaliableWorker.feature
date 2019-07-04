Feature: See available workers
  Scenario: Projectmanager sees the available workers and the employee is absent
    Given the software system is initialized
    And the ProjectManager is logged in
    And an Employee with ID 'EONE' is in the project
    And the employee is absent in week 49
    And there is an activity with name 'ActivityOne' in projectOne
    And the activity starts in week 48 and ends in week 50
    When the projectmanager gets the available workers
    Then the employee is not printed

  Scenario: projectmanager sees the available workers and the employee is not absent
    Given the software system is initialized
    And the ProjectManager is logged in
    And an Employee with ID 'EONE' is in the project
    And there is an activity with name 'ActivityOne' in projectOne
    And the activity starts in week 48 and ends in week 50
    When the projectmanager gets the available workers
    Then the employee is printed