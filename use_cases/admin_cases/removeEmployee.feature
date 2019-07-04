Feature: Remove employee from system
  Description: The admin removes an employee from the softwareSystem
  Actors: Admin

  Scenario: Admin removes employee that is part of a project succesfully
    Given The admin is logged in
    And An employee with id "EMP1" is registered in the system
    And he is in a project with name "project1"
    And he is assigned to an activity with name "Activity1"
    When the admin removes the employee
    Then the employee is no longer in the system
    And the employee is removed from belonging projects and activities

  Scenario: Admin tries to remove employee that is not in the system
    Given The admin is logged in
    And an employee with id "EMP2" is not registered in the system
    When the administrator removes the employee
    Then an errormessage "Employee not registered in system" occurs1

  Scenario: Admin tries to remove himself
    Given The admin is logged in
    When the administrator tries to remove himself
    Then an errormessage "You are not authorized to perform this action" occurs1
    And the administrator not removed


  Scenario: Employee who is not admin tries to remove employee from the system
    Given an employee with id "EMP1" is logged in
    And the employee is not the administrator
    And an employee with id "EMP2" is in the system
    When the employee removes the employee
    Then the errormessage "You are not authorized to perform this action" occurs
    And the employee is not removed from the system


