package acceptance_tests.projectmanager_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Tests for feature: addEmployeeToActivity
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class ProjectmanagerAddEmployeeToActivity {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private Project project;
    private Employee projectManager;
    private Employee employee;
    private Employee employee2;
    private Activity activity;

    private String errorMessage;

    public ProjectmanagerAddEmployeeToActivity(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^there is a project with name \"([^\"]*)\" in the software sytem$")
    public void thereIsAProjectWithNameInTheSoftwareSytem(String arg0) throws Throwable {
        this.project = new Project(arg0);
        stepMaster.createProject(project);
        assertTrue(softwareSystem.getProjects().contains(project));
    }

    @And("^the project has a projectmanager$")
    public void theProjectHasAProjectmanager() throws Exception {
        stepMaster.createEmployee("PMA1");
        this.projectManager = softwareSystem.getEmployeeById("PMA1");
        stepMaster.setProjectManager(project,projectManager);
    }

    @And("^the project contains an activity with name \"([^\"]*)\"$")
    public void theProjectContainsAnActivityWithName(String arg0) throws Throwable {
        this.activity = new Activity(arg0,10);
        project.createActivity(activity);
        assertTrue(project.getActivities().contains(activity));
    }

    @And("^the projectmanager is logged in$")
    public void theProjectmanagerIsLoggedIn() throws Exception {
        softwareSystem.login(projectManager.getId());
        assertEquals(softwareSystem.getSessionUser(),projectManager);
    }

    @And("^the project has an employee with id \"([^\"]*)\"$")
    public void theProjectHasAnEmployeeWithId(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        this.employee = softwareSystem.getEmployeeById(arg0);
        stepMaster.addEmployeeToProject(employee,project);
        assertTrue(project.getEmployees().contains(employee));
        //Logging the projectmanage in again
        softwareSystem.login(projectManager.getId());
    }

    @When("^the projectmanager adds the employee to the activity$")
    public void theProjectmanagerAddsTheEmployeeToTheActivity() throws Exception {
        try {
            project.assignEmployeeToActivity(activity, employee);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @And("^the activitycount of the employee is under (\\d+)$")
    public void theActivitycountOfTheEmployeeIsUnder(int arg0) {
        assertTrue(arg0 > employee.getActivityCount());
    }

    @Then("^the employee is added to the activity$")
    public void theEmployeeIsAddedToTheActivity() throws Exception {
        assertTrue(activity.containsEmployee(employee));
    }


    @And("^the project does not have an employee with id \"([^\"]*)\"$")
    public void theProjectDoesNotHaveAnEmployeeWithId(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        this.employee = softwareSystem.getEmployeeById(arg0);
        assertFalse(project.getEmployees().contains(employee));
    }

    @Then("^the employee is not added to the activity$")
    public void theEmployeeIsNotAddedToTheActivity() {
        assertFalse(activity.containsEmployee(employee));
    }

    @Then("^an errormessage with text \"([^\"]*)\" is printed$")
    public void anErrormessageWithTextIsPrinted(String arg0) throws Throwable {
        assertEquals(arg0,errorMessage);
    }

    @And("^an employee with name \"([^\"]*)\" is logged in$")
    public void anEmployeeWithNameIsLoggedIn(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        employee2 = softwareSystem.getEmployeeById(arg0);
        softwareSystem.login(arg0);
        assertEquals(softwareSystem.getSessionUser(),employee2);
    }

    @When("^the employee adds the employee to the activity$")
    public void theEmployeeAddsTheEmployeeToTheActivity() {
        try {
            project.assignEmployeeToActivity(activity, employee);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Given("^the project contains (\\d+) activities$")
    public void theProjectContainsActivities(int arg1) throws Exception {
        for (int i = 0; i < arg1; i++) {
            Activity activity = new Activity("Activity" + i,10);
            project.createActivity(activity);
        }
        assertEquals(project.getActivities().size(), arg1);
    }

    @And("^he is assigned to (\\d+) of the eleven projects$")
    public void heIsAssignedToOfTheElevenProjects(int arg0) throws Exception {
        softwareSystem.login(projectManager.getId());
        for (int i = 0; i < arg0; i++) {
            project.assignEmployeeToActivity(project.getActivityByName("Activity"+i), employee);
        }
    }

    @When("^the employee adds the employee to the eleventh activity$")
    public void theEmployeeAddsTheEmployeeToTheEleventhActivity() {
        this.activity = project.getActivityByName("Activity10");
        try {
            project.assignEmployeeToActivity(activity, employee);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @And("^the employee is already in the activity$")
    public void theEmployeeIsAlreadyInTheActivity() throws Exception {
        project.assignEmployeeToActivity(activity,employee);
        assertTrue(activity.getEmployees().contains(employee));
    }

    @Then("^the employee is not added to the activity again$")
    public void theEmployeeIsNotAddedToTheActivityAgain() throws Exception {
        //For loop checks that the employee is only added once
        int count = 0;
        for (Employee e : activity.getEmployees()) {
            if (e.equals(employee)) {
                count++;
            }
        }
        assertEquals(1,count);
    }
}
