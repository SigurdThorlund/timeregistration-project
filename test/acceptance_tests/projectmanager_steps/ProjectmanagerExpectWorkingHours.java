package acceptance_tests.projectmanager_steps;

import acceptance_tests.StepMaster;
import app.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Tests for feature: expectedWorkHours
 *
 * Use case and feature By Sille Jessing s184219
 */


public class ProjectmanagerExpectWorkingHours {
    private SoftwareSystem softwareSystem;
    private StepMaster stepMaster;
    private Project project;

    public ProjectmanagerExpectWorkingHours(SoftwareSystem softwareSystem, StepMaster stepMaster) {
        this.softwareSystem = softwareSystem;
        this.stepMaster = stepMaster;
    }

    @When("^the project manager sets expected hours of work$")
    public void theProjectManagerSetsExpectedHoursOfWork() throws Exception {
        this.project = softwareSystem.getProjectByName(stepMaster.projectName1);
        project.setExpectedHours(100);
    }

    @Then("^the project is updated with the expected time$")
    public void theProjectIsUpdatedWithTheExpectedTime() {
        int reaminingHours = project.getRemainingHours();
        assertEquals(100,reaminingHours);
    }

    @And("^the project’s expected time has already been set$")
    public void theProjectSExpectedTimeHasBeenSet() throws Exception {
        this.project = softwareSystem.getProjectByName(stepMaster.projectName1);
        project.setExpectedHours(100);
        int expectedHours = project.getExpectedHours();
        assertEquals(100,expectedHours);
    }
}
