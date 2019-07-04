package acceptance_tests.admin_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Tests for feature: createEmployee
 *
 * Use case and feature By Sigurd Thorlund s184189
 */


public class AdminCreateEmployeeSteps {

    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private ArrayList<Employee> preEmployeeList;
    private ArrayList<Employee> postEmployeeList;

    private Employee admin;
    private Employee employee1;
    private Employee employee4;


    private String errorMessage = "";


    public AdminCreateEmployeeSteps(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^the administrator is logged in$")
    public void theAdministratorIsLoggedIn() throws Exception {
        this.admin = softwareSystem.getAdmin();
        softwareSystem.login("ADMI");
        assertThat(softwareSystem.getSessionUser(),is(equalTo(admin)));
    }

    @And("^he has an employee with id \"([^\"]*)\"$")
    public void heHasAnEmployeeWithId(String arg1) throws Exception {
        this.employee1 = new Employee(arg1);
        assertThat(employee1.getId(),is(equalTo(arg1)));
    }

    @And("^the employees id is unique$")
    public void theEmployeesIdIsUnique() throws Exception {
        assertFalse(softwareSystem.employeeIdInSystem(employee1.getId()));
    }

    @When("^the administrator adds the epmloyee to the system$")
    public void theAdministratorAddsTheEpmloyeeToTheSystem() throws Exception {
        preEmployeeList = new ArrayList<>(softwareSystem.getEmployees());

        try {
            softwareSystem.addEmployeeToSystem(employee1);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^an employee with name \"([^\"]*)\" is added to the system$")
    public void anEmployeeWithNameIsAddedToTheSystem(String arg0) throws Throwable {
        assertEquals(softwareSystem.getEmployeeById(arg0),employee1);

        postEmployeeList = new ArrayList<>(softwareSystem.getEmployees());
        for (Employee e : preEmployeeList) {
            if (postEmployeeList.contains(e)) {
                postEmployeeList.remove(e);
            }
        }
        assertTrue(postEmployeeList.contains(employee1));
    }

    @And("^the employees id is not unique$")
    public void theEmployeesIdIsNotUnique() throws Exception {
        //Create an employee with the same id as the employee
        stepMaster.createEmployee(employee1.getId());
        softwareSystem.login("ADMI");
        assertThat(softwareSystem.getEmployeeById(employee1.getId()),is(not(equalTo(null))));
    }

    @Then("^an errormessage \"([^\"]*)\" occurs$")
    public void anErrormessageOccurs(String arg0) throws Throwable {
        assertEquals(arg0,errorMessage);
    }

    @Then("^the employee is not added to the system\\.$")
    public void theEmployeeIsNotAddedToTheSystem() throws Exception {
        try {
            softwareSystem.getEmployeeById(employee1.getId());
        } catch (Exception e) {

        }

        postEmployeeList = new ArrayList<>(softwareSystem.getEmployees());

        for (Employee e : preEmployeeList) {
            postEmployeeList.remove(e);
        }

        assertFalse(postEmployeeList.contains(employee1));
    }

    @Given("^that an employee with id \"([^\"]*)\" is logged in$")
    public void thatAnEmployeeWithIdIsLoggedIn(String arg0) throws Throwable {
        stepMaster.createEmployee(arg0);
        softwareSystem.login(arg0);
        this.employee4 = softwareSystem.getSessionUser();
    }

    @And("^he is not the administrator$")
    public void heIsNotTheAdministrator() {
        assertNotEquals(softwareSystem.getAdmin(), employee4);
    }

    @Then("^an errormessage with text\"([^\"]*)\" occurs$")
    public void anErrormessageWithTextOccurs(String arg0) throws Throwable {
        assertEquals(arg0,errorMessage);
    }
}
