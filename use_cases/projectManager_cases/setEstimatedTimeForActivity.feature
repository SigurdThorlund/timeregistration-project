Feature: Set estimated time for activitiy
  Actors: Projectmanager
  Description: The projectmanager sets the estimated time needed to complete activity

  Scenario: The projectmanager sets the estimated time needed to complete activity succesfully
    Given there is a project with name "Project" in
    And it has a projectmanager assigned to it
    And the project contains an activity with name "Activity1" and estimated time 10 hours
    And the activity is not complete
    And the projectmanagager of the project is logged in
    When the projectmanager sets the estimated time for the activity to 20 hours
    Then the estimated time for the activity is changed to 20 hours

  Scenario: The projectmanager sets the estimated time on an activity that is complete
    Given there is a project with name "Project" in
    And it has a projectmanager assigned to it
    And the project contains an activity with name "Activity1" and estimated time 10 hours
    And the activity is complete due to timeSpent being higher than the the estimated time
    And the projectmanagager of the project is logged in
    When the projectmanager sets the estimated time for the activity to 20 hours
    Then the estimated time for the activity is changed to 20 hours
    And the activity is no longer complete

  Scenario: Employee who is not the projectmanager tries to set the estimated time for the activity
    Given there is a project with name "Project" in
    And the project contains an activity with name "Activity1" and estimated time 10 hours
    And the activity is not complete
    And the activity has an employee with id "EMP1"
    And the employee is logged in to the system
    When the projectmanager sets the estimated time for the activity to 20 hours
    Then an errormessage with the text "You are not authorized to perform this action" occurs asd
    And the estimated time for the activity is unchanged



