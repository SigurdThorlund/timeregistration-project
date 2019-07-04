package acceptance_tests.projectmanager_steps;

import acceptance_tests.StepMaster;
import app.Activity;
import app.Employee;
import app.Project;
import app.SoftwareSystem;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for feature: availableWorkers
 *
 * Use case and feature By Sille Jessing s184219
 */

public class ProjectmanagerAvailableWorkers {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;
    private Project project;
    private Employee projectManager;
    private Employee employee;
    private Activity activity;
    private List<Employee> availableEmployees;

    public ProjectmanagerAvailableWorkers(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

   @Given("^the ProjectManager is logged in$")
    public void theProjectManagerIsLoggedIn() throws Exception {
        projectManager = softwareSystem.getEmployeeById(stepMaster.projectLeaderInitials1);
        softwareSystem.login(projectManager.getId());
        assertEquals(softwareSystem.getSessionUser(),projectManager);
    }

    @Given("^an Employee with ID 'EONE' is in the project$")
    public void anEmployeeWithIDEONEIsInTheProject() throws Exception {
        employee = softwareSystem.getEmployeeById("EONE");
        assertTrue(softwareSystem.getEmployees().contains(employee));
    }

    @Given("^the employee is absent in week (\\d+)$")
    public void theEmployeeIsAbsentInWeek(int arg1) throws Exception {
        employee.getCalendar().addAbsent(arg1);
        assertTrue(employee.getCalendar().containWeek(arg1));
    }

    @And("^there is an activity with name 'ActivityOne' in projectOne$")
    public void thereIsAnActivityWithNameActivityOneInTheProject() throws Exception {
        project = softwareSystem.getProjectByName("Project1");
        activity = project.getActivityByName("ActivityOne");
        assertNotNull(activity);
    }

    @Given("^the activity starts in week (\\d+) and ends in week (\\d+)$")
    public void theActivityStartsInWeekAndEndsInWeek(int arg1, int arg2) throws Exception {
        activity.setStartWeek(arg1);
        activity.setEndWeek(arg2);
        assertEquals(48,activity.getStartWeek());
        assertEquals(50,activity.getEndWeek());
    }

    @When("^the projectmanager gets the available workers$")
    public void theProjectmanagerGetsTheAvailableWorkers() throws Exception {
        Employee emp1 = softwareSystem.getEmployeeById("EONE");
        emp1.addAbsentWeek(48);
        availableEmployees = softwareSystem.getAvailableEmployees(48, 50);
        assertNotNull(availableEmployees);
    }

    @Then("^the employee is not printed$")
    public void theEmployeeIsNotPrinted() throws Exception {
        Employee employee1 = softwareSystem.getEmployeeById("EONE");
        assertFalse(availableEmployees.contains(employee1));
    }

    @Then("^the employee is printed$")
    public void theEmployeeIsPrinted() throws Exception {
        Employee employee1 = softwareSystem.getEmployeeById("EONE");
        employee1.removeAbsentWeek(48);
        availableEmployees = softwareSystem.getAvailableEmployees(48, 50);
        assertTrue(availableEmployees.contains(employee1));
    }







}
