Feature: Add Employee to activity
  Scenario: The projectmanager adds an employee in the project to the activity succesfully
    Given there is a project with name "Project 1" in the software sytem
    And the project has a projectmanager
    And the projectmanager is logged in
    And the project contains an activity with name "Activity 1"
    And the project has an employee with id "EMP1"
    When the projectmanager adds the employee to the activity
    And the activitycount of the employee is under 10
    Then the employee is added to the activity

   Scenario: The projectmanager adds an employee not in the project to the activity
     Given there is a project with name "Project 1" in the software sytem
     And the project has a projectmanager
     And the projectmanager is logged in
     And the project contains an activity with name "Activity 1"
     And the project does not have an employee with id "EMP1"
     When the projectmanager adds the employee to the activity
     Then an errormessage with text "The employee is not assigned to the project" is printed
     And the employee is not added to the activity

    Scenario: An employee not logged in as projectmanager adds an employee to the activity
      Given there is a project with name "Project 1" in the software sytem
      And the project has a projectmanager
      And the projectmanager is logged in
      And the project contains an activity with name "Activity 1"
      And the project has an employee with id "EMP1"
      And an employee with name "EMP2" is logged in
      When the employee adds the employee to the activity
      Then an errormessage with text "You are not authorized to perform this action" is printed
      And the employee is not added to the activity

    Scenario: The projectmanager adds an employee that is in too many activities
      Given there is a project with name "Project 1" in the software sytem
      And the project has a projectmanager
      And the projectmanager is logged in
      And the project contains 11 activities
      And the project has an employee with id "EMP1"
      And he is assigned to 10 of the eleven projects
      When the employee adds the employee to the eleventh activity
      Then an errormessage with text "Employee has reached the activity cap" is printed
      And the employee is not added to the activity

     Scenario: The projectmanager adds an employee that is already in the activity
       Given there is a project with name "Project 1" in the software sytem
       And the project has a projectmanager
       And the projectmanager is logged in
       And the project contains an activity with name "Activity 1"
       And the project has an employee with id "EMP1"
       And the employee is already in the activity
       When the projectmanager adds the employee to the activity
       Then an errormessage with text "Employee already in activity" is printed
       Then the employee is not added to the activity again







