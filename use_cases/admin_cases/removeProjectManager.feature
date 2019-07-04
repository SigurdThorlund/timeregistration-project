Feature: Remove ProjectManager from Project
  Actors: Admin
  Description: Admin removes the projectmanager from a project

  Scenario: Admin removes projectmanager from project succesfully
    Given There is a project with name "Project1" and id "201900" in the system
    And the project has a projectmanager assigned to it
    And the administrator is logged in
    When the admin removes the projectmanager
    Then the projectmanager is removed from the project
    And the project no longer has a projectmanager

  Scenario: Admin tries to remove projectmanager from a project that does not have a projectmanager
    Given There is a project with name "Project1" and id "201900" in the system
    And the project does not have a projectmanager assigned to it
    And the administrator is logged in
    When the admin removes the projectmanager
    Then an errormessage is printed with the text "There is no projectmanager on the project"

   Scenario: Employee not logged in as admin tries to remove the projectmanager
     Given There is a project with name "Project1" and id "201900" in the system
     And the project has a projectmanager assigned to it
     And an employee with the id "EMP2" is logged in
     When the employee removes the projectmanager
     Then an errormessage is printed with the text "You are not authorized to perform this action"
     And the projectmanager is not removed