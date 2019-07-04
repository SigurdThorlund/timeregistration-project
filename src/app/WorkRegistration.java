package app;

import java.time.LocalDate;

/**
 * Class used to store the information of the registered work on an activity
 *
 * By Sigurd Thorlund s184189
 */


public class WorkRegistration {

    private double timeSpent;
    private Employee employee;

    public WorkRegistration(double time, Employee user) throws Exception {
        if (time <= 0) {
            throw new Exception("This is not a valid time");
        }
        this.timeSpent = time;
        this.employee = user;
    }

    public void editTimeSet(double newTime) throws Exception {
        if (verifyUser()) {
            if (newTime >= 0) {
                this.timeSpent = newTime;
            } else {
                throw new Exception("This is not a valid time");
            }
        } else {
            throw new Exception("You are not allowed to change this");
        }
    }

    public boolean verifyUser() {
        return SoftwareSystem.getSessionUser().equals(employee);
    }

    public String toString() {
        return employee.toString() + " worked " + timeSpent + " hours" + " on: " + LocalDate.now();
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public Employee getEmployee() {
        return employee;
    }


}
