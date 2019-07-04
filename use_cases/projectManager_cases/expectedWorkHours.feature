Feature: Expected amount of working hours
  Description: The project manager sets/updates the expected
  amount of working hours for a given project
  Actors: Project manager#

  Scenario: Project manager sets a preliminary expected amount of
  working hours
    Given the software system is initialized
    When the project manager sets expected hours of work
    Then the project is updated with the expected time

  Scenario: The project manager changes the expected amount of
  working hours
    Given the software system is initialized
    And the project’s expected time has already been set
    When the project manager sets expected hours of work
    Then the project is updated with the expected time