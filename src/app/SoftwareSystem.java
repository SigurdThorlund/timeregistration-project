package app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SoftwareSystem {
    private Employee admin;

    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();

    private static Employee sessionUser;
    public boolean loggedIn = false;
    private int projectCount = 0;

    /**
     * Responsible for class: Sigurd Thorlund s184189
     */

    public SoftwareSystem() throws Exception {
        admin = new Employee("ADMI");
        employees.add(getAdmin());
        //initializeSystem();
    }

    /*public void initializeSystem() throws Exception {
        login("ADMI");
        Employee oliv = new Employee("OLIV");
        Employee hube = new Employee("HUBE");
        Employee sigu = new Employee("SIGU");
        Employee sill = new Employee("SILL");
        Employee dani = new Employee("DANI");

        addEmployeeToSystem(oliv);
        addEmployeeToSystem(hube);
        addEmployeeToSystem(sill);
        addEmployeeToSystem(dani);
        addEmployeeToSystem(sigu);

        Project makeui = new Project("Create the UI");
        Project makeapplications = new Project("Create the application");
        Project makeusecases = new Project("Make the use cases.");

        addProjectToSystem(makeui);
        addProjectToSystem(makeapplications);
        addProjectToSystem(makeusecases);

        appointProjectManagerForProject(makeui,oliv);
        appointProjectManagerForProject(makeapplications,sill);
        appointProjectManagerForProject(makeusecases,sigu);

        makeui.addEmployeeToProject(hube);

        Activity cli = new Activity("create cli",10);
        Activity userinput = new Activity("Create user input",5);
        logout();

        login(oliv.getId());
        makeui.createActivity(cli);
        makeui.createActivity(userinput);
        makeui.assignEmployeeToActivity(cli,hube);
        makeui.assignEmployeeToActivity(cli,oliv);
        logout();

        login(sill.getId());
        Activity softwaresystemm = new Activity("Create softwaresystem",50);
        Activity employeeclass = new Activity("Create employee class",2);
        makeapplications.createActivity(softwaresystemm);
        makeapplications.createActivity(employeeclass);
        makeapplications.assignEmployeeToActivity(softwaresystemm,sill);
        makeapplications.addEmployeeToProject(dani);
        makeapplications.assignEmployeeToActivity(employeeclass,dani);

        logout();
        login(sigu.getId());
        Activity usecase1 = new Activity("Use case 1",5);
        Activity usecase2 = new Activity("Use case 2",10);
        makeusecases.createActivity(usecase1);
        makeusecases.createActivity(usecase2);
        makeusecases.assignEmployeeToActivity(usecase1,sigu);
        makeusecases.assignEmployeeToActivity(usecase2,sigu);
        logout();
    }*/


    /**
     * @param employee
     *
     * @throws Exception
     *
     * @author Sigurd Thorlund
     */
    public void addEmployeeToSystem(Employee employee) throws Exception {
        if (isAdmin()) { //1
            if (employee.getId().length() != 4) { //2
                throw new Exception("ID must be four characters");
            } else {
                if (employeeIdInSystem(employee.getId())) { //3
                    throw new Exception("Employee ID is not unique");
                } else {
                    employees.add(employee);
                }
            }
        } else {
            throw new OperationNotAllowedException();
        }
    }

    public boolean isAdmin() {
        return sessionUser.equals(admin);
    }

    public boolean employeeIdInSystem(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeId)) return true;
        }
        return false;
    }

    public Employee getEmployeeById(String employeeID) throws Exception {
        for(Employee employee : employees) {
            if (employee.getId().equals(employeeID)) return employee;
        }
        throw new Exception("Employee is not in the system");
    }

    /**
     * @param project
     *
     * @throws Exception
     */

    public void addProjectToSystem(Project project) throws Exception {
        if (isAdmin()) {
            project.setId(getDate(),projectCount);
            projectCount++;
            getProjects().add(project);
        } else {
            throw new OperationNotAllowedException();
        }
    }

    public void appointProjectManagerForProject(Project project, Employee projectManager) throws Exception {
        if (isAdmin()) {
            project.setProjectManager(projectManager);
        } else {
            throw new OperationNotAllowedException();
        }
    }

    public void removeProjectManagerFromProject(Project project) throws Exception {
        if (isAdmin()) {
            project.removeProjectManager();
        } else {
            throw new OperationNotAllowedException();
        }
    }

    public void removeEmployeeFromSystem(Employee employee) throws Exception {
        if(isAdmin() && !employee.equals(getAdmin())) {         // 1
            if (getEmployees().contains(employee)) {            // 2
                getEmployees().remove(employee);
                if (employee.getProject()!=null) {
                    employee.getProject().removeEmployee(employee);
                }
            } else {
                throw new Exception("Employee not registered in system");
            }
        } else {
            throw new OperationNotAllowedException();
        }
    }

    public Project getProjectById(String projectId) throws Exception {
        //assert getProjects().size()>0;
        for(Project project : projects) {                                   //1
            if (project.getProjectID().equals(projectId)) return project;   //2
        }
        throw new Exception("Project is not in system");
    }

    public void login(String employeeID) throws Exception {
        if (employeeIdInSystem(employeeID)) {
            loggedIn = true;
            sessionUser = getEmployeeById(employeeID);
        } else {
            throw new Exception("ID is not in the system");
        }
    }

    public void logout() {
        if (loggedIn) {
            sessionUser = null;
            loggedIn = false;
        }
    }

    /**
     * @param projectName name of the project
     * @return project with that projectname
     *
     * by Sille Jessing s184219
     */

    public Project getProjectByName(String projectName) throws Exception {
        for(Project project : projects) {
            if (project.getProjectName().equals(projectName)) return project;
        }
        throw new Exception("Project not in system");
    }

    /**
     * getAvailableEmployees by Sille Jessing s184219
     */

    public List<Employee> getAvailableEmployees(int startWeek, int endWeek){
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {           //1
            boolean isAbsent = false;
            for(int i = startWeek; i<endWeek; i++){
                if(employee.isAbsentInWeek(i)){
                    isAbsent = true;
                }
            }
            if (!isAbsent) {
                result.add(employee);
            }
        }
        return result;
    }

    public static Employee getSessionUser(){
        return sessionUser;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    private DateServer dateServer = new DateServer();

    public Calendar getDate() {
        return dateServer.getDate();
    }

    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

    public Employee getAdmin() {
        return admin;
    }
}
