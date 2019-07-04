package acceptance_tests.admin_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Tests for feature: removeEmployee
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class AdminRemoveEmployeeSteps {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private Employee admin;
    private Employee employee;
    private Employee employee2;

    private Project project;
    private Activity activity;

    private String errormessage;

    public AdminRemoveEmployeeSteps(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^The admin is logged in$")
    public void theAdminIsLoggedIn() throws Exception {
        this.admin = softwareSystem.getAdmin();
        softwareSystem.login("ADMI");
        assertEquals(softwareSystem.getSessionUser(),admin);
    }

    @Given("^An employee with id \"([^\"]*)\" is registered in the system$")
    public void anEmployeeWithIdIsRegisteredInTheSystem(String arg1) throws Exception {
        stepMaster.createEmployee(arg1);
        this.employee = softwareSystem.getEmployeeById(arg1);
        assertNotNull(employee);

    }

    @And("^he is in a project with name \"([^\"]*)\"$")
    public void heIsInAProjectWithName(String arg0) throws Throwable {
        project = new Project(arg0);
        stepMaster.createProject(project);
        stepMaster.addEmployeeToProject(employee,project);
        assertTrue(project.getEmployees().contains(employee));
    }

    @Given("^he is assigned to an activity with name \"([^\"]*)\"$")
    public void heIsAssignedToAnActivityWithName(String arg1) throws Exception {
        activity = new Activity(arg1,10);
        stepMaster.addActivityToProject(project,activity);
        stepMaster.addEmployeeToActivity(employee,activity);
        assertTrue(activity.getEmployees().contains(employee));
        //Admin logs in again due to the stepmaster function overwriting the sessionUser
        softwareSystem.login("ADMI");
    }

    @When("^the admin removes the employee$")
    public void theAdminRemovesTheEmployee() throws Exception {
        softwareSystem.removeEmployeeFromSystem(employee);
    }

    @Then("^the employee is no longer in the system$")
    public void theEmployeeIsNoLongerInTheSystem() throws Exception {
        assertFalse(softwareSystem.employeeIdInSystem(employee.getId()));
    }

    @Then("^the employee is removed from belonging projects and activities$")
    public void theEmployeeIsRemovedFromBelongingProjectsAndActivities() throws Exception {
        assertFalse(project.getEmployees().contains(employee));
        assertFalse(project.getActivities().contains(employee));
    }

    @Given("^an employee with id \"([^\"]*)\" is not registered in the system$")
    public void anEmployeeWithIdIsNotRegisteredInTheSystem(String arg1) throws Exception {
        this.employee = new Employee(arg1);
        assertFalse(softwareSystem.employeeIdInSystem(employee.getId()));
    }

    @When("^the administrator removes the employee$")
    public void theAdministratorRemovesTheEmployee() throws Exception {
        try {
            softwareSystem.removeEmployeeFromSystem(employee);
        } catch (Exception e) {
            this.errormessage = e.getMessage();
        }
    }

    @Then("^an errormessage \"([^\"]*)\" occurs(\\d+)$")
    public void anErrormessageOccurs(String arg1, int arg2) throws Exception {
        assertEquals(arg1,errormessage);
    }

    @Given("^an employee with id \"([^\"]*)\" is logged in$")
    public void anEmployeeWithIdIsLoggedIn(String arg1) throws Exception {
        stepMaster.createEmployee(arg1);
        this.employee = softwareSystem.getEmployeeById(arg1);
        softwareSystem.login(employee.getId());
        assertEquals(softwareSystem.getSessionUser(),employee);
    }

    @Given("^the employee is not the administrator$")
    public void theEmployeeIsNotTheAdministrator() throws Exception {
        this.admin = softwareSystem.getAdmin();
        assertNotEquals(admin,employee.getId());
    }

    @Given("^an employee with id \"([^\"]*)\" is in the system$")
    public void anEmployeeWithIdIsInTheSystem(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        this.employee2 = softwareSystem.getEmployeeById(arg0);

        assertTrue(softwareSystem.getEmployees().contains(employee2));
        //log the employee in again
        softwareSystem.login(employee.getId());
    }

    @When("^the employee removes the employee$")
    public void theEmployeeRemovesTheEmployee() throws Exception {
        try {
            softwareSystem.removeEmployeeFromSystem(employee2);
        } catch (OperationNotAllowedException e) {
            this.errormessage = e.getMessage();
        }
    }

    @Then("^the errormessage \"([^\"]*)\" occurs$")
    public void theErrormessageOccurs(String arg1) throws Exception {
        assertEquals(arg1,errormessage);
    }

    @Then("^the employee is not removed from the system$")
    public void theEmployeeIsNotRemovedFromTheSystem() throws Exception {
        assertTrue(softwareSystem.getEmployees().contains(employee2));
    }


    @When("^the administrator tries to remove himself$")
    public void theAdministratorTriesToRemoveHimself() {
        try {
            softwareSystem.removeEmployeeFromSystem(admin);
        } catch (Exception e) {
            this.errormessage = e.getMessage();
        }
    }

    @And("^the administrator not removed$")
    public void theAdministratorNotRemoved() {
        assertEquals(softwareSystem.getAdmin(),admin);
    }
}
