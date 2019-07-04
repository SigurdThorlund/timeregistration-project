package acceptance_tests.projectmanager_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Tests for feature: addEmployee
 *
 * Use case and feature By Sille Jessing s184219
 */

public class ProjectmanagerAddEmployee {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;
    private Project project;

    public ProjectmanagerAddEmployee(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^the software system is initialized$")
    public void theSoftwareSystemIsInitialized() throws Exception {
        stepMaster.initialize();
        String projectName = stepMaster.projectName1;
        this.project = softwareSystem.getProjectByName(projectName);
    }

    @Given("^the employee is not part of the project$")
    public void theEmployeeIsNotPartOfTheProject() throws Exception {
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        assertFalse(project.verifyColleague(employee));
    }

    @And("^the projectManager is logged in$")
    public void theProjectManagerIsLoggedIn() throws Exception {
        softwareSystem.login(stepMaster.projectLeaderInitials1);
    }

    @When("^the project leader adds the employee to the project$")
    public void theProjectLeaderAddsTheEmployeeToTheProject() throws Exception {
        boolean isOK = true;
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        try{
            project.addEmployeeToProject(employee);
        } catch (Exception e){
            isOK = false;
        }
        //assertTrue(isOK);
        employee.currentProjectName = project.getProjectName();
    }

    @Then("^the employee is succesfully added to the project$")
    public void theEmployeeIsSuccesfullyAddedToTheProject() throws Exception {
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        employee.currentProjectName = project.getProjectName();
        assertTrue(project.verifyColleague(employee));

    }

    @Given("^the employee is part of another project$")
    public void theEmployeeIsPartOfAnotherProject() throws Exception {
        String projectName = stepMaster.projectName2;
        Project project2 = softwareSystem.getProjectByName(projectName);
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        project2.addEmployeeToProject(employee);
        employee.currentProjectName = project2.getProjectName();
        assertTrue(project2.verifyColleague(employee));
    }

    @Then("^the employee is not added to the project$")
    public void theEmployeeIsNotAddedToTheProject() throws Exception {
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        boolean isOK = project.getEmployees().contains(employee);
        assertFalse(isOK);
        isOK = project.verifyColleague(employee);
        assertFalse(isOK);
    }

    @Given("^an Employee with ID = 'EONE' is in the project$")
    public void anEmployeeWithIDEONEIsInTheProject() throws Exception {
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        project.addEmployeeToProject(employee);
        boolean isOK = project.getEmployees().contains(employee);
        assertTrue(isOK);
    }

    @When("^the project leader removes him from the project$")
    public void theProjectLeaderRemovesHimFromTheProject() throws Exception {
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        boolean isOK = project.removeEmployee(employee);
        assertTrue(isOK);
    }

    @Then("^the employee is no longer part of the project$")
    public void theEmployeeIsNoLongerPartOfTheProject() throws Exception {
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        boolean isOK = project.verifyColleague(employee);
        assertFalse(isOK);
    }

    private String errorMessage;

    @When("^the project leader adds the employee to the project again$")
    public void theProjectLeaderAddsTheEmployeeToTheProjectAgain() throws Exception {
        boolean isOK = true;
        Employee employee = softwareSystem.getEmployeeById(stepMaster.employeeInitials1);
        try{
            project.addEmployeeToProject(employee);
        } catch (Exception e){
            this.errorMessage = e.getMessage();
            isOK = false;
        }
        assertFalse(isOK);
    }

    @Then("^an errormessage with \"([^\"]*)\" occurs$")
    public void anErrormessageWithOccurs(String arg1) throws Exception {
        assertEquals(arg1,errorMessage);
    }
}
