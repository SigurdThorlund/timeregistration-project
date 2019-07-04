Feature: Create activity
  Scenario: The projectmanager creates a new activity successfully
    Given there is a project with name "Project Test1" in the system
    And that the projectmanager is logged in
    And there is an activity with name "Activity1"
    When the projectmanager adds the activity to the project
    Then the activity is added to the project

  Scenario: The projectmanager tries to create an Activity with a non unique name
    Given there is a project with name "Project Test1" in the system
    And that the projectmanager is logged in
    And the project has an activity with name "Activity1"
    And there is an activity with name "Activity1"
    When the projectmanager adds the activity to the project
    Then an errormessage occurs with the text "Activity name is not unique"
    And the activity is not added to the project

  Scenario: Employee not logged in as projectmanager tries to create an activity
    Given there is a project with name "Project Test1" in the system
    And an employee with id "EMP1" is logged in
    And there is an activity with name "Activity1"
    When the employee adds the activity to the project
    Then an errormessage occurs with the text "You are not authorized to perform this action"
    Then the activity is not added to the project

