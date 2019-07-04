Feature: Set Work Period
  Actors: ProjectManager
  Description: The projectmanager sets the start and end week for an activity in his project

  Scenario: The Projectmanager sets the start and end week for an activity succesfully
    Given there is a project with name "Project1" in the software
    And the project has a projectmanager assigned
    And there is an activity with name "Activity1" in the project
    And the projectmanager is logged in to the system
    When the projectmanager sets the start week 4 and end week 10 for the activity
    Then the start and end week is updated.

  Scenario:  The Projectmanager tries to set an invalid start and end week for an activity
    Given there is a project with name "Project1" in the software
    And the project has a projectmanager assigned
    And there is an activity with name "Activity1" in the project
    And the projectmanager is logged in to the system
    When the projectmanager sets the start week 0 and end week 53 for the activity
    Then an errormessage with text "Not a valid input!" is printed to the console
    Then the start and end week remain unchanged

  Scenario: Employee who is not the projectmanager tries to set the start and endweek for activity
    Given there is a project with name "Project1" in the software
    And the project has a projectmanager assigned
    And there is an activity with name "Activity1" in the project
    And an employee in the project with id "EMP1" is logged in
    When the projectmanager sets the start week 0 and end week 53 for the activity
    Then an errormessage with text "You are not authorized to perform this action" is printed to the console
    Then the start and end week remain unchanged