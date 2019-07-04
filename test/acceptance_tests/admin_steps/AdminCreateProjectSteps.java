package acceptance_tests.admin_steps;

import acceptance_tests.MockDateHolder;
import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Tests for feature: createProject
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class AdminCreateProjectSteps {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private Employee admin;
    private Employee employee;

    private Project project1;
    private Project project2;

    private String errormessage;

    private MockDateHolder mockDateHolder;

    public AdminCreateProjectSteps(SoftwareSystem softwareSystem, StepMaster stepMaster, MockDateHolder mockDateHolder) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
        this.mockDateHolder = mockDateHolder;
    }

    @Given("^that the administrator is logged in(\\d+)$")
    public void thatTheAdministratorIsLoggedIn(int arg1) throws Exception {
        this.admin = softwareSystem.getAdmin();
        softwareSystem.login("ADMI");
        assertEquals(softwareSystem.getSessionUser(),admin);
    }

    @Given("^the year is \"([^\"]*)\"$")
    public void theYearIs(String arg1) throws Exception {
        assertEquals(softwareSystem.getDate().getWeekYear(),Integer.parseInt(arg1));
    }

    @Given("^there is a project with name \"([^\"]*)\" and one with name \"([^\"]*)\"$")
    public void thereIsAProjectWithNameAndOneWithName(String arg1, String arg2) throws Exception {
        this.project1 = new Project(arg1);
        this.project2 = new Project(arg2);
    }

    @When("^the administrator adds the two projects$")
    public void theAdministratorAddsTheTwoProjects() throws Exception {
        softwareSystem.addProjectToSystem(project1);
        softwareSystem.addProjectToSystem(project2);
    }

    @Then("^a project with name \"([^\"]*)\" and id \"([^\"]*)\", and a project with name \"([^\"]*)\" and id \"([^\"]*)\" are created$")
    public void aProjectWithNameAndIdAndAProjectWithNameAndIdAreCreated(String arg1, String arg2, String arg3, String arg4) throws Exception {
        assertEquals(project1.getProjectName(),arg1);
        assertEquals(project1.getProjectID(),arg2);
        assertEquals(project2.getProjectName(),arg3);
        assertEquals(project2.getProjectID(),arg4);
    }

    @Then("^the two projects are in the system$")
    public void theTwoProjectsAreInTheSystem() throws Exception {
        assertTrue(softwareSystem.getProjects().contains(project1));
        assertTrue(softwareSystem.getProjects().contains(project2));
    }

    @Given("^the employee is logged in$")
    public void theEmployeeIsLoggedIn() throws Exception {
        stepMaster.createEmployee("EMP1");
        softwareSystem.login("EMP1");
        this.employee = softwareSystem.getSessionUser();
        assertTrue(softwareSystem.loggedIn);
    }

    @Given("^the employee is not the admin$")
    public void theEmployeeIsNotTheAdmin() throws Exception {
        assertNotEquals(admin, employee);
    }

    @Given("^there is a project with name \"([^\"]*)\"$")
    public void thereIsAProjectWithName(String arg1) throws Exception {
        this.project1 = new Project(arg1);
    }

    @When("^he adds the project$")
    public void heAddsTheProject() throws Exception {
        try {
            softwareSystem.addProjectToSystem(project1);
        } catch (OperationNotAllowedException e) {
            this.errormessage = e.getMessage();
        }
    }

    @Then("^an errorMessage with text \"([^\"]*)\" occurss$")
    public void anErrorMessageWithTextOccurss(String arg0) throws Throwable {
        assertEquals(errormessage,arg0);
    }

    @And("^the project is not added$")
    public void theProjectIsNotAdded() {
        assertFalse(softwareSystem.getProjects().contains(project1));
    }

    @And("^the year is now \"([^\"]*)\"$")
    public void theYearIsNow(String arg0) throws Throwable {
        mockDateHolder.advanceDateByDays(365);
        assertEquals(softwareSystem.getDate().getWeekYear(),Integer.parseInt(arg0));
    }
}
