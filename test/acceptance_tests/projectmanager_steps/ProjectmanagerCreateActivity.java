package acceptance_tests.projectmanager_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Tests for feature: createActivity
 *
 * Use case and feature By Sigurd Thorlund s184189
 */

public class ProjectmanagerCreateActivity {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;

    private Project project;
    private Employee projectManager;
    private Activity activity1;
    private Activity activity2;

    private String errorMessage;

    public ProjectmanagerCreateActivity(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @Given("^there is a project with name \"([^\"]*)\" in the system$")
    public void thereIsAProjectWithNameInTheSystem(String arg1) throws Exception {
        stepMaster.createProject(new Project(arg1));
        project = softwareSystem.getProjectByName(arg1);
        assertTrue(softwareSystem.getProjects().contains(project));
    }

    @Given("^that the projectmanager is logged in$")
    public void thatTheProjectmanagerIsLoggedIn() throws Exception {
        softwareSystem.login("ADMI");
        stepMaster.createEmployee("EMP1");
        softwareSystem.login("ADMI");
        softwareSystem.appointProjectManagerForProject(project, softwareSystem.getEmployeeById("EMP1"));
        this.projectManager = project.getProjectManager();
        softwareSystem.login(projectManager.getId());
        assertEquals(SoftwareSystem.getSessionUser(),projectManager);
    }

    @Given("^there is an activity with name \"([^\"]*)\"$")
    public void thereIsAnActivityWithName(String arg1) throws Exception {
        this.activity1 = new Activity(arg1,10);
        assertEquals(arg1, activity1.getActivityName());
    }

    @When("^the projectmanager adds the activity to the project$")
    public void theProjectmanagerAddsTheActivityToTheProject() throws Exception {
        try {
            project.createActivity(activity1);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("^the activity is added to the project$")
    public void theActivityIsAddedToTheProject() throws Exception {
        assertTrue(project.getActivities().contains(activity1));
    }

    @Given("^the project has an activity with name \"([^\"]*)\"$")
    public void theProjectHasAnActivityWithName(String arg1) throws Exception {
        activity2 = new Activity(arg1,10);
        project.createActivity(activity2);
        assertTrue(project.getActivities().contains(activity2));
    }

    @Then("^an errormessage occurs with the text \"([^\"]*)\"$")
    public void anErrormessageOccursWithTheText(String arg1) throws Exception {
        assertEquals(arg1,errorMessage);
    }

    @Then("^the activity is not added to the project$")
    public void theActivityIsNotAddedToTheProject() throws Exception {
        assertFalse(project.getActivities().contains(activity1));
    }

    @When("^the employee adds the activity to the project$")
    public void theEmployeeAddsTheActivityToTheProject() {
        try {
            project.createActivity(activity1);
        } catch (Exception e) {
            this.errorMessage = e.getMessage();
        }
    }
}
