Feature: Set time worked
	Description: Employee has worked on an activity and whises to set the time worked
	Actors: Employee

	Scenario: Employee sets the time worked succesfully
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has activities
		And he is assigned to an activity
		And the time spent on the activity is 0 hours
		And the time left on the activity is 100 hours
		When he sets that he has worked 2 hours on the activity
		Then the time spent on the activity increases with that amount
		And the time left is decreased with that amount

	Scenario: Employee sets the time worked succesfully, completing the activity
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has an activity with name "Activity1" and estimated time 5 hours
		And he is assigned to an activity
		And the time spent on the activity is 0 hours
		And the time left on the activity is 5 hours
		When he sets that he has worked 5 hours on the activity
		Then the time spent on the activity increases with that amount
		And the time left is decreased with that amount
		And the activity is completed

	Scenario: Employee sets the time worked on activity he is not assigned to
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has activities
		And he is not assigned to an activity
		When he sets that he has worked 2 hours on the activity
		Then an errormessage with the text "ID not associated with activity" occurs
		And the time spent on the activity is not increased

    Scenario: Employee sees the last work changes made on an activity
		Given that an employee with id "EMP1" in the system is logged in
        And he is part of a project
        And the project has activities
        And he is assigned to an activity
        And he has set the time worked once
        When he gets a list of time work changes from the activity
        Then then a String with text "Employee: EMP1 worked 2.0 hours on: " is printed

	Scenario: Employee sets a not valid time on activity
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has activities
		And he is assigned to an activity
		When he sets that he has worked "-1" hours on the activity
		Then an errormessage with the text "This is not a valid time" occurs

	Scenario: Employee sets the time worked on an activity that is complete
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has activities
		And he is assigned to an activity
		And the activity is complete
		When he sets that he has worked 2 hours on the activity
		Then an errormessage with the text "Activity is already complete" occurs
		And the time spent on the activity is not increased

	Scenario: Employee edits the time worked succesfully
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has activities
		And he is assigned to an activity
		And he has set that he has worked 2 hours on the activity
		When he edits the workTime he has made, instead working "1.5" hours
		Then then the timeSpent on the activity is decreased with "0.5" hours

	Scenario: Employee edits the time worked on a time he hasn't worked himself
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has activities
		And he is assigned to an activity
		And another employee has set the time worked 2 hours on the activity
		When he edits the workChange the other employee has set, instead working "1.5" hours
		Then an errormessage with the text "You are not allowed to change this" occurs

	Scenario: Employee edits the time worked to a not valid number
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has activities
		And he is assigned to an activity
		And he has set that he has worked 2 hours on the activity
		When he edits the workTime he has made, instead working "-1.5" hours
		Then an errormessage with the text "This is not a valid time" occurs

	Scenario: Employee edits the time worked so that the activity is no longer complete
		Given that an employee with id "EMP1" in the system is logged in
		And he is part of a project
		And the project has an activity with name "Activity1" and estimated time 5 hours
		And he is assigned to an activity
		And the time spent on the activity is 0 hours
		And the time left on the activity is 5 hours
		And he has set that he has worked 2 hours on the activity
		When he edits the workTime he has made, instead working "5" hours
		Then then the timeSpent on the activity is increased with "3" hours
		And the activity is completed

