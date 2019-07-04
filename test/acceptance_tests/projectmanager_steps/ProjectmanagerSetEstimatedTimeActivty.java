package acceptance_tests.projectmanager_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Tests for feature: setEstimatedTimeForActivity
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class ProjectmanagerSetEstimatedTimeActivty {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;
    private Project project;
    private Employee projectManager;
    private Employee employee;
    private Activity activity;
    private String errorMessage="";

    private double activityEstimatedTimeBefore;


    public ProjectmanagerSetEstimatedTimeActivty(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^there is a project with name \"([^\"]*)\" in$")
    public void thereIsAProjectWithNameIn(String arg0) throws Throwable {
        project = new Project(arg0);
        stepMaster.createProject(project);
        assertTrue(softwareSystem.getProjects().contains(project));
    }

    @Given("^it has a projectmanager assigned to it$")
    public void itHasAProjectmanagerAssignedToIt() throws Exception {
        stepMaster.createEmployee("PMA1");
        projectManager = softwareSystem.getEmployeeById("PMA1");
        stepMaster.setProjectManager(project,projectManager);
        assertEquals(projectManager,project.getProjectManager());
    }

    @Given("^the project contains an activity with name \"([^\"]*)\" and estimated time (\\d+) hours$")
    public void theProjectContainsAnActivityWithNameAndEstimatedTimeHours(String arg1, int arg2) throws Exception {
        activity = new Activity(arg1,arg2);
        stepMaster.addActivityToProject(project,activity);
        assertTrue(project.getActivities().contains(activity));
    }

    @Given("^the activity is not complete$")
    public void theActivityIsNotComplete() throws Exception {
        assertFalse(activity.complete);
    }

    @Given("^the projectmanagager of the project is logged in$")
    public void theProjectmanagagerOfTheProjectIsLoggedIn() throws Exception {
        softwareSystem.login(projectManager.getId());
        assertEquals(projectManager,softwareSystem.getSessionUser());
    }


    @When("^the projectmanager sets the estimated time for the activity to (\\d+) hours$")
    public void theProjectmanagerSetsTheEstimatedTimeForTheActivityToHours(int arg0) {
        activityEstimatedTimeBefore = activity.getEstimatedTime();
        try {
            project.setEstimatedTimeForActivity(activity,arg0);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^the estimated time for the activity is changed to (\\d+) hours$")
    public void theEstimatedTimeForTheActivityIsChangedToHours(int arg0) {
        assertEquals(arg0,activity.getEstimatedTime(),0.0001);
    }

    @And("^the activity is no longer complete$")
    public void theActivityIsNoLongerComplete() {
        assertFalse(activity.complete);
    }

    @And("^the activity is complete due to timeSpent being higher than the the estimated time$")
    public void theActivityIsCompleteDueToTimeSpentBeingHigherThanTheTheEstimatedTime() throws Exception {
        stepMaster.createEmployee("EMP1");
        this.employee = softwareSystem.getEmployeeById("EMP1");
        stepMaster.addEmployeeToProject(employee,project);
        stepMaster.addEmployeeToActivity(employee,activity);

        softwareSystem.login(employee.getId());
        activity.setTimeWorkedOnActivity(activity.getEstimatedTime());

        assertTrue(activity.complete);
    }

    @And("^the activity has an employee with id \"([^\"]*)\"$")
    public void theActivityHasAnEmployeeWithId(String arg0) throws Throwable {
        stepMaster.createEmployee("EMP1");
        this.employee = softwareSystem.getEmployeeById("EMP1");
        stepMaster.addEmployeeToProject(employee,project);
        stepMaster.addEmployeeToActivity(employee,activity);
        assertTrue(activity.getEmployees().contains(employee));
    }

    @And("^the employee is logged in to the system$")
    public void theEmployeeIsLoggedInToTheSystem() throws Exception {
        softwareSystem.login(employee.getId());
        assertEquals(softwareSystem.getSessionUser(),employee);
    }

    @And("^the estimated time for the activity is unchanged$")
    public void theEstimatedTimeForTheActivityIsUnchanged() {
        assertEquals(activityEstimatedTimeBefore,activity.getEstimatedTime(),0.001);
    }

    @Then("^an errormessage with the text \"([^\"]*)\" occurs asd$")
    public void anErrormessageWithTheTextOccursAsd(String arg0) throws Throwable {
        assertEquals(arg0,errorMessage);
    }
}
