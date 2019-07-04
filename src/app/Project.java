package app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Responsible for class - Sille Jessing s184219
 *
 */

public class Project {
    private String projectID;
    private String projectName;
    private ArrayList<Activity> activities;
    private ArrayList<Employee> employees;
    private Employee projectManager;

    private int remainingHours;
    private int expectedHours;

    public Project(String name) {
        this.projectName = name;

        activities = new ArrayList<>();
        employees = new ArrayList<>();
    }

    public Activity getActivityByName(String activityName) {
        for (Activity activity : activities) {
            if (activity.getActivityName().equals(activityName)) {
                return activity;
            }
        }
        return null;
    }

    public boolean employeeInProject(Employee employee) {
        return employees.contains(employee);
    }

    public void addEmployeeToProject(Employee employee) throws Exception {
        if (employee.getProject() == null) {
            employee.setProject(this);
            this.employees.add(employee);
        } else {
            throw new Exception("Employee already in project");
        }
    }

    public boolean removeEmployee(Employee employee) {
        if (verifyColleague(employee)) {
            removeEmployeeFromActivity(employee);
            this.employees.remove(employee);
            employee.setProject(null);
            return true;
        }
        return false;
    }

    private void removeEmployeeFromActivity(Employee employee) {
        for (Activity activity : activities) {
            if(activity.getEmployees().contains(employee)){
                activity.getEmployees().remove(employee);
                employee.decreaseActivityCount();
            }
        }
    }

    public boolean isProjectManager() {
        return SoftwareSystem.getSessionUser().equals(projectManager);
    }

    public boolean verifyColleague(Employee colleague) {
        return employees.contains(colleague);
    }

    public void createActivity(Activity activity) throws Exception {
        if (isProjectManager()) {
            if (checkUniqueActivity(activity)) {
                activities.add(activity);
            } else {
                throw new Exception("Activity name is not unique");
            }
        } else {
            throw new OperationNotAllowedException();
        }
    }

    /**
     * assignEmployeeToActivity by Sigurd Thorlund s184189
     */
    public void assignEmployeeToActivity(Activity activity, Employee employee) throws Exception {
        if (employeeInProject(employee) && isProjectManager()) {
            activity.addEmployeeToActivity(employee);
        } else if (!employeeInProject(employee)) {
            throw new Exception("The employee is not assigned to the project");
        } else {
            throw new OperationNotAllowedException();
        }
    }

    /**
     * setWorkPriodForActivity by Sigurd Thorlund s184189
     *
     */
    public void setWorkPeriodForActivity(Activity activity, int startWeek, int endWeek) throws Exception {
        if (isProjectManager()) {
            if ((startWeek > 0 && startWeek <= 52) && (endWeek > 0 && endWeek <= 52)) {
                activity.setStartWeek(startWeek);
                activity.setEndWeek(endWeek);
            } else {
                throw new Exception("Not a valid input!");
            }
        } else {
            throw new OperationNotAllowedException();
        }
    }

    public boolean checkUniqueActivity(Activity newActivity) {
        for (Activity activity : activities) {
            if (activity.getActivityName().equals(newActivity.getActivityName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * set and remove ProjectManager by Sigurd Thorlund s184189
     */
    public void setProjectManager(Employee employee) throws Exception {
        if (hasProjectManager()) {
            throw new Exception("This project already has a projectmanager");
        } else {
            if (employee.getProject() == this) {
                projectManager = employee;
            } else if (employee.getProject() == null) {
                projectManager = employee;
                addEmployeeToProject(employee);
            } else {
                throw new Exception("Employee is already in another project");
            }
        }
    }

    public void removeProjectManager() throws Exception {
        if (hasProjectManager()) {
            employees.remove(projectManager);
            projectManager = null;
        } else {
            throw new Exception("There is no projectmanager on the project");
        }
    }

    public boolean hasProjectManager() {
        if (projectManager != null) {
            return true;
        }
        return false;
    }

    /**
     * setEstimatedTimeForActivity by Sigurd Thorlund s184189
     *
     */
    public void setEstimatedTimeForActivity(Activity activity, double estimatedTime) throws OperationNotAllowedException {
        if (isProjectManager()) {
            activity.setEstimatedTime(estimatedTime);
        } else {
            throw new OperationNotAllowedException();
        }
    }

    /**
     * Helper function for returning activities for employee
     */
    public List<Activity> getActivitiesForEmployee(Employee employee) {
        List<Activity> employeeActivies = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.containsEmployee(employee)) {
                employeeActivies.add(activity);
            }
        }
        return employeeActivies;
    }

    public String toString() {
        return "Project: " + projectName + ", ID:" + projectID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setId(Calendar date, int count) {
        this.projectID = date.getWeekYear() + "0" + count;
    }

    public String getProjectName() {
        return projectName;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    public ArrayList<Activity> getActivities() {
        return this.activities;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public int getRemainingHours() {
        return remainingHours;
    }

    public int getExpectedHours() {
        return expectedHours;
    }

    public void setExpectedHours(int expectedHours) {
        this.expectedHours = expectedHours;
        this.remainingHours = this.expectedHours;
    }
}
