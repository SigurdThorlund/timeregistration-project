package acceptance_tests.employee_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Tests for feature: setTimeWorked
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class EmployeeSetTimeSteps {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private Employee employee;
    private Employee employee2;
    private Activity activity;
    private Project project;
    private WorkRegistration ts;

    private String errorMessage = "";
    private String workChangeMessage = "";


    private double activityTimeSpentBefore;
    private double activityTimeLeftBefore;
    private double activityTimeSpentAfter;
    private double timeWorkedOnActivity;


    public EmployeeSetTimeSteps(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^that an employee with id \"([^\"]*)\" in the system is logged in$")
    public void thatAnEmployeeWithIdInTheSystemIsLoggedIn(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        this.employee = softwareSystem.getEmployeeById(arg0);
        softwareSystem.login(arg0);
        assertEquals(softwareSystem.getSessionUser(), employee);
    }

    @Given("^he is part of a project$")
    public void heIsPartOfAProject() throws Exception {
        this.project = new Project("Project Test1");
        stepMaster.createProject(project);
        stepMaster.addEmployeeToProject(employee,project);
        assertTrue(project.getEmployees().contains(employee));
        //Login the user again
        softwareSystem.login(employee.getId());
    }

    @Given("^the project has activities$")
    public void theProjectHasActivities() throws Exception {
        this.activity = new Activity("Activity 1", 100);
        stepMaster.addActivityToProject(project,activity);
        assertTrue(project.getActivities().contains(activity));
    }

    @Given("^he is assigned to an activity$")
    public void heIsAssignedToAnActivity() throws Exception {
        stepMaster.addEmployeeToActivity(employee,activity);
        assertTrue(activity.getEmployees().contains(employee));
    }

    @Given("^the time spent on the activity is (\\d+) hours$")
    public void theTimeSpentOnTheActivityIsHours(int arg1) throws Exception {
        activityTimeSpentBefore = activity.getTimeSpent();
        assertEquals(activityTimeSpentBefore,arg1,0.00001);
    }

    @Given("^the time left on the activity is (\\d+) hours$")
    public void theTimeLeftOnTheActivityIsHours(int arg1) throws Exception {
        activityTimeLeftBefore = arg1;
        assertEquals(activity.getTimeLeft(), activityTimeLeftBefore,0.00001);
    }

    @When("^he sets that he has worked (\\d+) hours on the activity$")
    public void heSetsThatHeHasWorkedHoursOnTheActivity(int arg1) throws Exception {
        timeWorkedOnActivity = arg1;
        try {
            activity.setTimeWorkedOnActivity(arg1);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^the time spent on the activity increases with that amount$")
    public void theTimeSpentOnTheActivityIncreasesWithThatAmount() throws Exception {
        this.activityTimeSpentAfter = activityTimeSpentBefore + timeWorkedOnActivity;
        assertEquals(activityTimeSpentAfter, activity.getTimeSpent(), 0.0001);
    }

    @Then("^the time left is decreased with that amount$")
    public void theTimeLeftIsDecreasedWithThatAmount() throws Exception {
        assertEquals(activity.getTimeLeft(), activityTimeLeftBefore-timeWorkedOnActivity,0.0001);
    }

    @Given("^he is not assigned to an activity$")
    public void heIsNotAssignedToAnActivity() throws Exception {
        assertFalse(activity.getEmployees().contains(employee));
    }

    @Then("^an errormessage with the text \"([^\"]*)\" occurs$")
    public void anErrormessageWithTheTextOccurs(String arg0) throws Throwable {
        assertEquals(arg0,errorMessage);
    }

    @Then("^the time spent on the activity is not increased$")
    public void theTimeSpentOnTheActivityIsNotIncreased() throws Exception {
        assertEquals(activityTimeSpentBefore,activityTimeSpentAfter,0.0001);
    }

    @Given("^he has set the time worked once$")
    public void heHasSetTheTimeWorkedOnce() throws Exception {
        activity.setTimeWorkedOnActivity(2);
        assertFalse(activity.getWorkRegistrations().isEmpty());
    }

    @When("^he gets a list of time work changes from the activity$")
    public void heGetsAListOfTimeWorkChangesFromTheActivity() throws Exception {
        this.workChangeMessage = activity.getWorkChanges();
    }

    @Then("^then a String with text \"([^\"]*)\" is printed$")
    public void thenAStringWithTextIsPrinted(String arg1) throws Exception {
        assertEquals(arg1 + LocalDate.now() + "\n", workChangeMessage);
    }

    @When("^he sets that he has worked \"([^\"]*)\" hours on the activity$")
    public void heSetsThatHeHasWorkedHoursOnTheActivity(String arg0) throws Throwable {
        try {
            activity.setTimeWorkedOnActivity(Integer.parseInt(arg0));
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @And("^the activity is complete$")
    public void theActivityIsComplete() throws Exception {
        activity.setTimeWorkedOnActivity(101);
        assertTrue(activity.complete);
    }

    @Given("^he has set that he has worked (\\d+) hours on the activity$")
    public void heHasSetThatHeHasWorkedHoursOnTheActivity(int arg1) throws Exception {
        activity.setTimeWorkedOnActivity(arg1);
        assertEquals(activity.getTimeSpent(), arg1,0.0001);
    }

    @When("^he edits the workTime he has made, instead working \"([^\"]*)\" hours$")
    public void heEditsTheWorkTimeHeHasMadeInsteadWorkingHours(String arg1) throws Exception {
        this.ts = activity.getWorkRegistrationsForEmployee(employee).get(0);
        activityTimeSpentBefore = activity.getTimeSpent();
        timeWorkedOnActivity = Double.parseDouble(arg1);
        try {
            activity.editTimeWorkedOnActivity(ts,Double.parseDouble(arg1));
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^then the timeSpent on the activity is decreased with \"([^\"]*)\" hours$")
    public void thenTheTimeSpentOnTheActivityIsDecreasedWithHours(String arg1) throws Exception {
        assertEquals(activity.getTimeSpent(),activityTimeSpentBefore-Double.parseDouble(arg1),0.0001);
    }

    @And("^another employee has set the time worked (\\d+) hours on the activity$")
    public void anotherEmployeeHasSetTheTimeWorkedHoursOnTheActivity(int arg0) throws Exception {
        stepMaster.createEmployee("EMP2");
        this.employee2 = softwareSystem.getEmployeeById("EMP2");
        stepMaster.addEmployeeToProject(employee2,project);
        stepMaster.addEmployeeToActivity(employee2,activity);

        softwareSystem.login(employee2.getId());
        activity.setTimeWorkedOnActivity(arg0);
        assertFalse(activity.getWorkRegistrations().isEmpty());
    }

    @When("^he edits the workChange the other employee has set, instead working \"([^\"]*)\" hours$")
    public void heEditsTheWorkChangeTheOtherEmployeeHasSetInsteadWorkingHours(String arg0) throws Throwable {
        softwareSystem.login(employee.getId());
        this.ts = activity.getWorkRegistrationsForEmployee(employee2).get(0);
        try {
            ts.editTimeSet(Double.parseDouble(arg0));
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @And("^the activity is completed$")
    public void theActivityIsCompleted() {
        assertTrue(activity.complete);
    }

    @And("^the project has an activity with name \"([^\"]*)\" and estimated time (\\d+) hours$")
    public void theProjectHasAnActivityWithNameAndEstimatedTimeHours(String arg0, int arg1) throws Throwable {
        activity = new Activity(arg0,arg1);
        stepMaster.addActivityToProject(project,activity);
        assertTrue(project.getActivities().contains(activity));
    }

    @Then("^then the timeSpent on the activity is increased with \"([^\"]*)\" hours$")
    public void thenTheTimeSpentOnTheActivityIsIncreasedWithHours(String arg0) throws Throwable {
        assertEquals(activity.getTimeSpent(),activityTimeSpentBefore+Double.parseDouble(arg0),0.0001);
    }
}


