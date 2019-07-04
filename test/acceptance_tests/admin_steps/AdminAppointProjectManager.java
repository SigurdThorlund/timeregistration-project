package acceptance_tests.admin_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Tests for feature: setProjectManager
 *
 * Use case and feature By Sigurd Thorlund s184189
 */


public class AdminAppointProjectManager {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private Project project;
    private Project project2;
    private Employee employee1;
    private Employee employee2;

    private String errorMessage;

    public AdminAppointProjectManager(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^the system has a project with name \"([^\"]*)\" and id \"([^\"]*)\"$")
    public void theSystemHasAProjectWithNameAndId(String arg1, String arg2) throws Exception {
        project = new Project(arg1);
        stepMaster.createProject(project);
        assertTrue(softwareSystem.getProjects().contains(project));
        assertEquals(arg2,project.getProjectID());
    }

    @Given("^the project has no projectLeader$")
    public void theProjectHasNoProjectLeader() throws Exception {
        assertNull(project.getProjectManager());
    }

    @Given("^and the system has an employee with id \"([^\"]*)\"$")
    public void andTheSystemHasAnEmployeeWithId(String arg1) throws Exception {
        stepMaster.createEmployee(arg1);
        this.employee1 = softwareSystem.getEmployeeById(arg1);
        assertTrue(softwareSystem.getEmployees().contains(employee1));
    }

    @When("^the admin sets the employee as the projectmanager$")
    public void theAdminSetsTheEmployeeAsTheProjectmanager() throws Exception {
        softwareSystem.appointProjectManagerForProject(project,employee1);
    }

    @Then("^the employee is now the projectmanager of the project$")
    public void theEmployeeIsNowTheProjectmanagerOfTheProject() throws Exception {
        assertEquals(project.getProjectManager(),employee1);
    }

    @And("^the employee is added to the project$")
    public void theEmployeeIsAddedToTheProject() throws Exception {
        assertEquals(employee1.getProject(),project);
        assertTrue(project.getEmployees().contains(employee1));
    }

    @Given("^and the project has an employee with id \"([^\"]*)\"$")
    public void andTheProjectHasAnEmployeeWithId(String arg1) throws Exception {
        stepMaster.createEmployee(arg1);
        employee1 = softwareSystem.getEmployeeById(arg1);
        project.addEmployeeToProject(employee1);
        assertTrue(project.getEmployees().contains(employee1));
    }

    @Then("^the employee is not added to the project again$")
    public void theEmployeeIsNotAddedToTheProjectAgain() throws Exception {
        int count = 0;
        for (Employee employee : project.getEmployees()) {
            if (employee.getId().equals(employee1.getId())) { count++;}
        }
        //Make sure the employee is only featured once.
        assertEquals(1,count);
    }

    @Given("^the project has a projectManager$")
    public void theProjectHasAProjectManager() throws Exception {
        stepMaster.createEmployee("EMP2");
        this.employee2 = softwareSystem.getEmployeeById("EMP2");
        softwareSystem.login("ADMI");
        softwareSystem.appointProjectManagerForProject(project,employee2);
        assertEquals(project.getProjectManager(),employee2);
    }

    @When("^the admin tries to set the employee as the projectmanager$")
    public void theAdminTriesToSetTheEmployeeAsTheProjectmanager() {
        try {
            softwareSystem.appointProjectManagerForProject(project,employee1);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^an errormessage is printed with text \"([^\"]*)\"$")
    public void anErrormessageIsPrintedWithText(String arg0) throws Throwable {
        assertEquals(arg0, errorMessage);
    }

    @And("^the employee is not added to the projectt$")
    public void theEmployeeIsNotAddedToTheProjectt() {
        assertFalse(project.getEmployees().contains(employee1));
    }

    @And("^the employee is part of another project in the system$")
    public void theEmployeeIsPartOfAnotherProjectInTheSystem() throws Exception {
        this.project2 = new Project("Project2");
        stepMaster.createProject(project2);
        softwareSystem.login("ADMI");
        stepMaster.addEmployeeToProject(employee1,project2);
        assertTrue(project2.getEmployees().contains(employee1));
    }

    @And("^the employee is logged   in$")
    public void theEmployeeIsLoggedIn() throws Exception {
        softwareSystem.login("EMP1");
        assertEquals(softwareSystem.getSessionUser(),employee1);
    }

    @When("^the employee sets the employee as the projectmanager$")
    public void theEmployeeSetsTheEmployeeAsTheProjectmanager() throws Exception {
        try {
            softwareSystem.appointProjectManagerForProject(project,employee1);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @And("^the employee is then not added to the project$")
    public void theEmployeeIsThenNotAddedToTheProject() {
        assertFalse(project.getEmployees().contains(employee1));
    }
}
