package acceptance_tests;

import app.*;

/**
 * This class is used to create dummy variables for the system
 *
 * By Sigurd Thorlund
 */


public class StepMaster {
    private SoftwareSystem system;

    //For the initialize function:
    private final String adminInitials = "ADMI";
    public final String projectLeaderInitials1 = "PONE";
    public final String projectLeaderInitials2 = "PTWO";
    public final String employeeInitials1 = "EONE";
    public final String employeeInitials2 = "ETWO";
    public final String projectName1 = "Project1";
    public final String projectName2 = "Project2";


    public StepMaster(SoftwareSystem softwareSystem) {
        this.system = softwareSystem;
    }

    public void createEmployee(String id) throws Exception {
        system.login("ADMI");
        Employee employee = new Employee(id);
        system.addEmployeeToSystem(employee);
        system.logout();
    }

    public void createProject(Project project) throws Exception {
        system.login("ADMI");
        system.addProjectToSystem(project);
        system.logout();
    }

    public void addEmployeeToProject(Employee employee, Project project) {
        project.getEmployees().add(employee);
        employee.setProject(project);
    }

    public void setProjectManager(Project p, Employee e) throws Exception {
        system.login("ADMI");
        system.appointProjectManagerForProject(p,e);
        system.logout();
    }

    public void addActivityToProject(Project project, Activity activity) {
        project.getActivities().add(activity);
    }

    public void addEmployeeToActivity(Employee employee, Activity activity) {
        activity.getEmployees().add(employee);
    }

    /**
     * Initialize projects with projectLeaders and employees
     * Sille Jessing s184219
     */

    public void initialize() throws Exception {
        system.login(adminInitials);
        Employee pl1 = new Employee(projectLeaderInitials1);
        Employee pl2 = new Employee(projectLeaderInitials2);

        system.addEmployeeToSystem(pl1);
        system.addEmployeeToSystem(pl2);


        Employee emp1 = new Employee(employeeInitials1);
        Employee emp2 = new Employee(employeeInitials2);

        system.addEmployeeToSystem(emp1);
        system.addEmployeeToSystem(emp2);

        Project project1 = new Project(projectName1);
        Project project2 = new Project(projectName2);

        system.addProjectToSystem(project1);
        system.addProjectToSystem(project2);

        project1.addEmployeeToProject(pl1);
        project2.addEmployeeToProject(pl2);

        system.appointProjectManagerForProject(project1,pl1);
        system.appointProjectManagerForProject(project2,pl2);

        Activity activity1 = new Activity("ActivityOne", 2);
        Activity activity2 = new Activity("ActivityTwo", 3);

        activity1.setStartWeek(48);

        project1.getActivities().add(activity1);
        project1.getActivities().add(activity2);
    }

}
