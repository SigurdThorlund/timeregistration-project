#Feature: Remove Project
#  Actors: Admin
#  Description: The admin is able to remove a project from the system. This also deletes all data of the project
#
#  Scenario: The admin removes a project succesfully
#    Given the administrator is logged in
#    And there is a project with name "Project Test1" and id "2019000" in the system
#    And there is a projectManager in the project
#    When the admin removes the project
#    Then the project is removed
#    And the projectManager is no longer associated with the project

#  Scenario: Admin tries to remove a project that is not in the system
#    Given the administrator is logged in
#    And there is a project with name "Project Test1" and id "2019001" is not in the system
#    When the administrator removes the Project
#    Then an errormessage "Project not registered" occurss

#  Scenario: Employee who is not admin tries to remove project from the system
#    Given an employee with id "EMP1" is logged in
#    And the employee is not the administrator
#    And there is a project with name "Project Test1" and id "2019001" in the systemm
#    When the employee removes the project
#    Then an errormessage "You are not authorized to perform this action" occursd
#    And the project is not removed from the system
