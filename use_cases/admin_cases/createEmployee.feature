Feature: Create Employee
  Actors: Admin

  Scenario: The administrator adds an employee to the SoftwareSystem succesfully
    Given the administrator is logged in
    And he has an employee with id "EMP1"
    And the employees id is unique
    When the administrator adds the epmloyee to the system
    Then an employee with name "EMP1" is added to the system

  Scenario: The administrator adds an employee without an unique id
    Given the administrator is logged in
    And he has an employee with id "EMP1"
    And the employees id is not unique
    When the administrator adds the epmloyee to the system
    Then an errormessage "Employee ID is not unique" occurs
    And the employee is not added to the system.

  Scenario: The administrator adds an employee with an invalid id
    Given the administrator is logged in
    And he has an employee with id "EMP10"
    When the administrator adds the epmloyee to the system
    Then an errormessage "ID must be four characters" occurs
    And the employee is not added to the system.

  Scenario: An employee who is not admin tries to add an employee to the system
    Given that an employee with id "EMP1" is logged in
    And he is not the administrator
    And he has an employee with id "EMP10"
    When the administrator adds the epmloyee to the system
    Then an errormessage "You are not authorized to perform this action" occurs
