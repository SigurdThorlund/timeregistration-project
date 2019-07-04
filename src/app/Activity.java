package app;

import java.util.ArrayList;

/**
 * Class responsible Sigurd Thorlund s184189
 *
 */

public class Activity {
    private String activityName;

    private int startWeek;
    private int endWeek;

    public boolean complete = false;

    private double estimatedTime;

    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<WorkRegistration> workRegistrations = new ArrayList<>();

    public Activity(String name, int estimatedTime) {
        this.activityName = name;
        this.estimatedTime = estimatedTime;
    }

    public void addEmployeeToActivity(Employee employee) throws Exception {
        if (employees.contains(employee)){
            throw new Exception("Employee already in activity");
        } else {
            if (employee.eligibleForActivity()) {
                employees.add(employee);
                employee.increaseActivityCount();
            } else {
                throw new Exception("Employee has reached the activity cap");
            }
        }
    }

    public boolean verifyUser() {
        return employees.contains(SoftwareSystem.getSessionUser());
    }

    public String getWorkChanges() {
        StringBuilder workchanges = new StringBuilder();
        for (WorkRegistration workRegistration : workRegistrations) {
            workchanges.append(workRegistration.toString()).append("\n");
        }
        return workchanges.toString();
    }

    /**
     * Helperfunction to get work changes for the employee
     *
     * @return List of work changes.
     */

    public ArrayList<WorkRegistration> getWorkRegistrationsForEmployee(Employee employee) {
        ArrayList<WorkRegistration> employeeWorkChanges = new ArrayList<>();
        for (WorkRegistration ts : workRegistrations) {
            if (ts.getEmployee().equals(employee)) {
                employeeWorkChanges.add(ts);
            }
        }
        return employeeWorkChanges;
    }

    public void setTimeWorkedOnActivity(double time) throws Exception {
        if (verifyUser()) {
            if (!complete) {
                WorkRegistration timeSet = new WorkRegistration(time, SoftwareSystem.getSessionUser());
                workRegistrations.add(timeSet);
                isActivityComplete();
            } else {
                throw new Exception("Activity is already complete");
            }
        } else {
            throw new Exception("ID not associated with activity");
        }
    }

    public void editTimeWorkedOnActivity(WorkRegistration ts, double newTime) throws Exception {
        ts.editTimeSet(newTime);
        isActivityComplete();
    }

    public double sumWorkRegistrations() {
        double timeSpentWorking = 0;
        for (WorkRegistration workRegistration : workRegistrations) {
            timeSpentWorking += workRegistration.getTimeSpent();
        }
        return timeSpentWorking;
    }

    public void isActivityComplete() {
        if (sumWorkRegistrations() >= estimatedTime) {
            complete = true;
        } else {
            complete = false;
        }
    }

    public boolean containsEmployee(Employee employee) {
        return employees.contains(employee);
    }

    public String toString() {
        return "Activity: " + activityName + ", Estimated Time for activity: " + estimatedTime + ", Time Spent: " + sumWorkRegistrations() + ", Time Left: " + (getTimeLeft());
    }

    public double getTimeSpent() {
        return sumWorkRegistrations();
    }

    public double getTimeLeft() {
        if (estimatedTime - sumWorkRegistrations() < 0) {
            return 0;
        }
        return estimatedTime - sumWorkRegistrations();
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
        isActivityComplete();
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<WorkRegistration> getWorkRegistrations() {
        return workRegistrations;
    }
}
