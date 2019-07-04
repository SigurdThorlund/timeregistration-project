package app;


import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for class Sille Jessing s184219
 *
 */

public class Employee {
    public String currentProjectName;
    private int activityCount = 0;
    private int activityCap = 10;

    private String id;
    private Project project = null;
    private EmployeeCalendar calendar;
    private List<Integer> absentWeeks;

    public Employee(String id) {
        this.id = id;
        this.calendar = new EmployeeCalendar();
        this.absentWeeks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Project getProject() throws Exception {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void decreaseActivityCount() {
        this.activityCount--;
    }

    public void increaseActivityCount() { this.activityCount++; }

    public String toString() {
        return "Employee: " + id;
    }

    public EmployeeCalendar getCalendar() {
        return calendar;
    }

    public void addAbsentWeek(int weekNumber) {
        if(!absentWeeks.contains(weekNumber)){
            absentWeeks.add(weekNumber);
        }
    }

    public void removeAbsentWeek(int weekNumber) {
        absentWeeks.remove(Integer.valueOf(weekNumber));
    }

    public boolean isAbsentInWeek(int weekNumber){
        return absentWeeks.contains(weekNumber);
    }

    /**
     * Checks if the employee is assigned too many activities
     *
     * @return true if the activity count is higher than the cap.
     */
    public boolean eligibleForActivity() {
        return activityCount < activityCap;
    }
}
