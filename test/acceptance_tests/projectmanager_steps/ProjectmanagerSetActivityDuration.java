package acceptance_tests.projectmanager_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Tests for feature: setWorkPeriodForActivity
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class ProjectmanagerSetActivityDuration {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;
    private Project project;
    private Employee projectManager;
    private Activity activity;
    private Employee employee;

    private int startWeek;
    private int endWeek;

    private int startWeekBefore;
    private int endWeekBefore;

    private String errorMessage="";

    public ProjectmanagerSetActivityDuration(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^there is a project with name \"([^\"]*)\" in the software$")
    public void thereIsAProjectWithNameInTheSoftware(String arg1) throws Exception {
        Project project = new Project(arg1);
        stepMaster.createProject(project);
        this.project = softwareSystem.getProjectById(project.getProjectID());
        assertTrue(softwareSystem.getProjects().contains(project));
    }

    @Given("^the project has a projectmanager assigned$")
    public void theProjectHasAProjectmanagerAssigned() throws Exception {
        softwareSystem.login("ADMI");
        stepMaster.createEmployee("PMA1");
        this.projectManager = softwareSystem.getEmployeeById("PMA1");
        stepMaster.setProjectManager(project,projectManager);
        assertEquals(projectManager, project.getProjectManager());
    }

    @And("^there is an activity with name \"([^\"]*)\" in the project$")
    public void thereIsAnActivityWithNameInTheProject(String arg0) throws Throwable {
        this.activity = new Activity(arg0, 10);
        stepMaster.addActivityToProject(project,activity);
        assertTrue(project.getActivities().contains(activity));
    }

    @Given("^the projectmanager is logged in to the system$")
    public void theProjectmanagerIsLoggedInToTheSystem() throws Exception {
        softwareSystem.login(projectManager.getId());
        assertEquals(projectManager, softwareSystem.getSessionUser());
    }

    @When("^the projectmanager sets the start week (\\d+) and end week (\\d+) for the activity$")
    public void theProjectmanagerSetsTheStartWeekAndEndWeekForTheActivity(int arg1, int arg2) throws Exception {
        this.startWeekBefore = activity.getStartWeek();
        this.endWeekBefore = activity.getEndWeek();
        this.startWeek = arg1;
        this.endWeek = arg2;
        try {
            project.setWorkPeriodForActivity(activity, startWeek, endWeek);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^the start and end week is updated\\.$")
    public void theStartAndEndWeekIsUpdated() throws Exception {
        assertEquals(activity.getStartWeek(), startWeek );
        assertEquals(activity.getEndWeek(), endWeek );
    }


    @Then("^the start and end week remain unchanged$")
    public void theStartAndEndWeekRemainUnchanged() {
        assertEquals(startWeekBefore,activity.getStartWeek());
        assertEquals(endWeekBefore,activity.getEndWeek());
    }

    @Then("^an errormessage with text \"([^\"]*)\" is printed to the console$")
    public void anErrormessageWithTextIsPrintedToTheConsole(String arg0) throws Throwable {
        assertEquals(arg0,errorMessage);
    }

    @And("^an employee in the project with id \"([^\"]*)\" is logged in$")
    public void anEmployeeInTheProjectWithIdIsLoggedIn(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        employee = softwareSystem.getEmployeeById(arg0);
        stepMaster.addEmployeeToProject(employee,project);
        stepMaster.addEmployeeToActivity(employee,activity);
        softwareSystem.login(arg0);
        assertEquals(softwareSystem.getSessionUser(),employee);
    }
}
