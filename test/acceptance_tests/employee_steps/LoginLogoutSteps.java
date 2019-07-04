package acceptance_tests.employee_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Tests for feature: employeeLogin and adminLogin
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class LoginLogoutSteps {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;
    private Employee admin;
    private Employee employee;
    private String errorMessage;

    public LoginLogoutSteps(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^that the administrator is not logged in$")
    public void thatTheAdministratorIsNotLoggedIn() throws Exception {
        assertTrue(!softwareSystem.loggedIn);
    }

    @Given("^the system has an administrator with id \"([^\"]*)\"$")
    public void theSystemHasAnAdministratorWithId(String arg1) throws Exception {
        assertThat(softwareSystem.getAdmin().getId(), is(equalTo(arg1)));
        this.admin = softwareSystem.getAdmin();
    }

    @When("^the administrator logs in$")
    public void theAdministratorLogsIn() throws Exception {
        softwareSystem.login("ADMI");
    }

    @Then("^the administrator is the sessionUser$")
    public void theAdministratorIsTheSessionUser() throws Exception {
        assertEquals(softwareSystem.getSessionUser(), admin);
        assertTrue(softwareSystem.loggedIn);
    }

    @Given("^that the administrator is logged in$")
    public void thatTheAdministratorIsLoggedIn() throws Exception {
        softwareSystem.login("ADMI");
        assertTrue(softwareSystem.loggedIn);
    }

    @When("^the administrator logs out$")
    public void theAdministratorLogsOut() throws Exception {
        softwareSystem.logout();
    }

    @Then("^the administrator is no longer logged in$")
    public void theAdministratorIsNoLongerLoggedIn() throws Exception {
        assertFalse(softwareSystem.loggedIn);
    }

    @Then("^there is no sessionUser$")
    public void thereIsNoSessionUser() throws Exception {
        assertNull(softwareSystem.getSessionUser());
    }


    @Given("^no one is logged in$")
    public void noOneIsLoggedIn() throws Exception {
        assertFalse(softwareSystem.loggedIn);
    }

    @Given("^the system has an employee with id \"([^\"]*)\"$")
    public void theSystemHasAnEmployeeWithId(String arg1) throws Exception {
        stepMaster.createEmployee(arg1);
        this.employee = softwareSystem.getEmployeeById(arg1);
        assertTrue(softwareSystem.getEmployees().contains(employee));
    }

    @When("^the employee logs in$")
    public void theEmployeeLogsIn() throws Exception {
        softwareSystem.login(employee.getId());
    }

    @Then("^the employee is now the session user$")
    public void theEmployeeIsNowTheSessionUser() throws Exception {
        assertEquals(softwareSystem.getSessionUser(), employee);
        softwareSystem.logout();
    }


    @Given("^the system does not have an employee with id \"([^\"]*)\"$")
    public void theSystemDoesNotHaveAnEmployeeWithId(String arg1) throws Exception {
        assertFalse(softwareSystem.employeeIdInSystem(arg1));
    }

    @When("^the employee logs in with id \"([^\"]*)\"$")
    public void theEmployeeLogsInWithId(String arg1) throws Exception {
        try {
            softwareSystem.login(arg1);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^an errormessage with text \"([^\"]*)\" occurs$")
    public void anErrormessageWithTextOccurs(String arg0) throws Throwable {
        assertEquals(errorMessage, arg0);
    }

    @And("^the employee is not logged in$")
    public void theEmployeeIsNotLoggedIn() {
        assertFalse(softwareSystem.loggedIn);
        assertNull(softwareSystem.getSessionUser());
    }
}
