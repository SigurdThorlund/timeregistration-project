package acceptance_tests.admin_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Tests for feature: removeProjectManager
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class AdminRemoveProjectManager {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private Project project;
    private Employee projectManager;
    private Employee employee;

    private String errorMessage;

    public AdminRemoveProjectManager(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^There is a project with name \"([^\"]*)\" and id \"([^\"]*)\" in the system$")
    public void thereIsAProjectWithNameAndIdInTheSystem(String arg1, String arg2) throws Exception {
        stepMaster.createProject(new Project(arg1));
        this.project = softwareSystem.getProjectById(arg2);
    }

    @And("^the project has a projectmanager assigned to it$")
    public void theProjectHasAProjectmanagerAssignedToIt() throws Exception {
        stepMaster.createEmployee("EMP1");
        this.projectManager = softwareSystem.getEmployeeById("EMP1");
        softwareSystem.login("ADMI");
        softwareSystem.appointProjectManagerForProject(project,projectManager);
        assertEquals(projectManager,project.getProjectManager());
    }

    @When("^the admin removes the projectmanager$")
    public void theAdminRemovesTheProjectmanager() throws Exception {
        try {
            softwareSystem.removeProjectManagerFromProject(project);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^the projectmanager is removed from the project$")
    public void theProjectmanagerIsRemovedFromTheProject() throws Exception {
        assertFalse(project.getEmployees().contains(projectManager));
    }

    @Then("^the project no longer has a projectmanager$")
    public void theProjectNoLongerHasAProjectmanager() throws Exception {
        assertNull(project.getProjectManager());
    }

    @And("^the project does not have a projectmanager assigned to it$")
    public void theProjectDoesNotHaveAProjectmanagerAssignedToIt() {
        assertNull(project.getProjectManager());
    }

    @Then("^an errormessage is printed with the text \"([^\"]*)\"$")
    public void anErrormessageIsPrintedWithTheText(String arg0) throws Throwable {
        assertEquals(arg0, errorMessage);
    }

    @And("^an employee with the id \"([^\"]*)\" is logged in$")
    public void anEmployeeWithTheIdIsLoggedIn(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        this.employee = softwareSystem.getEmployeeById(arg0);
        softwareSystem.login(arg0);
        assertEquals(softwareSystem.getSessionUser(), employee);
    }

    @When("^the employee removes the projectmanager$")
    public void theEmployeeRemovesTheProjectmanager() {
        try {
            softwareSystem.removeProjectManagerFromProject(project);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @And("^the projectmanager is not removed$")
    public void theProjectmanagerIsNotRemoved() {
        assertEquals(project.getProjectManager(), projectManager);
    }
}
