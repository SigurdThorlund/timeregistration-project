package cli.gui;

import java.util.ArrayList;

import cli.MainGUI.GUI;
import app.Employee;
import app.SoftwareSystem;
import cli.menu.Event;
import cli.menu.Menu;
import cli.menu.MenuLabel;
import app.*;

/**
 * The User Interface and CLI package is primarily developed by Oliver Bak s1835
 *
 *
 */

public class MainMenu extends Menu{

	SoftwareSystem softwaresystem;
	GUI gui;
	String header, description;

	public MainMenu(SoftwareSystem softwaresystem,GUI gui) throws Exception {
		super(softwaresystem,gui,"Main Menu","Main Menu - Logged in as "+ softwaresystem.getSessionUser().toString());
		this.softwaresystem = softwaresystem;
		this.gui = gui;
		init();
	}

	public void init() {

		Menu projectmenu = new Menu(softwaresystem,gui,"Project Menu","Project Menu") {
			{
				appendMenuItem(new Event(softwaresystem,gui,"Create a Project","Insert name for project") {
					public void event(String input) throws Exception {
						gui.menuBack();
						softwaresystem.addProjectToSystem(new Project(input));
					}
				});

				appendMenuItem(new Event(softwaresystem,gui,"See projects","Press enter to continue") {
					public void event(String input) throws Exception {
						gui.menuBack();
						gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"See projects","List of projects") {
							{
								for (Project project : softwaresystem.getProjects()) {
									this.appendMenuItem(new Menu(softwaresystem,gui,project.toString(), project.toString()) {
										{
											this.appendMenuItem(new Event(softwaresystem,gui,"See project manager","Press enter to see Project manager") {
												public void event(String input) throws Exception {
													gui.menuBack();
													String pm = "No projectmanager";
													Employee manager = project.getProjectManager();
													if(manager!=null)
														pm = manager.toString();

													gui.printOutput("PM is: "+pm);

												}
											});
											this.appendMenuItem(new Event(softwaresystem, gui, "Assign Employee to project","Press employee id then enter.") {
												public void event(String input) throws Exception {
													gui.menuBack();
													project.addEmployeeToProject(softwaresystem.getEmployeeById(input));

												}
											});
											this.appendMenuItem(new Event(softwaresystem, gui, "See employees on project", "Press enter to continue") {
												public void event(String input) throws Exception {
													gui.menuBack();
													gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"See employees","List of employees working on projects") {
														{
															for (Employee employee : project.getEmployees()) {
																this.appendMenuItem(new MenuLabel(softwaresystem, gui, employee.toString()));
															}
														}
													});}});
											this.appendMenuItem(new Event(softwaresystem,gui,"Assign Projectmanager","Insert ID of employee to assign to PM, then press Enter.") {
												public void event(String input) throws Exception {
													gui.menuBack();
													softwaresystem.appointProjectManagerForProject(project, softwaresystem.getEmployeeById(input));
												}

											});
											//// test
											this.appendMenuItem(new Event(softwaresystem,gui,"Create an activity","Insert in format: ID of activity,estimated time left") {
												public void event(String input) throws Exception {
													gui.menuBack();
													String[] modifiedInput;
													String actname = null;
													int timeleft = -1;

													try {
														modifiedInput = input.split(",");

														if (modifiedInput.length==2) {
															actname = modifiedInput[0];
															timeleft = Integer.parseInt(modifiedInput[1]);
														}
													}catch(Exception e) {
														throw new Exception("Invalid input");
													}

													try {

														if(actname!=null && timeleft!=-1) project.createActivity(new Activity(actname,timeleft));
														else throw new Exception("Invalid input");

													}catch(Exception e) {
														throw new Exception(e.getMessage());
													}
												}
											});
											this.appendMenuItem(new Event(softwaresystem,gui,"List of activities","Press enter to continue") {
												public void event(String input) throws Exception {
													gui.menuBack();
													gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"List of activities","List of activities") {
														{
															for(Activity activity : project.getActivities()) {
																appendMenuItem(new Menu(softwaresystem,gui,activity.getActivityName(),activity.getActivityName()) {
																	{
																		appendMenuItem(new Event(softwaresystem,gui,"Add Employee to activity","Insert ID of employee you wish to add. Press enter") {
																			public void event(String input) throws Exception {
																				gui.menuBack();
																				project.assignEmployeeToActivity(activity, softwaresystem.getEmployeeById(input));
																			}
																		});
																		appendMenuItem(new Event(softwaresystem,gui,"See employees on activity","Press enter to continue") {
																			public void event(String input) throws Exception {
																				gui.menuBack();
																				gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"List of employees on activity","Employees on activity: "+activity.toString()) {
																					{
																						for (Employee employee : activity.getEmployees()) {
																							appendMenuItem(new MenuLabel(softwaresystem,gui,employee.toString()));
																						}
																					}
																				});
																			}
																		});

																		appendMenuItem(new Event(softwaresystem,gui,"Set new estimated time","Insert estimated time. Press enter.") {
																			public void event(String input) throws Exception {
																				gui.menuBack();
																				try{
																					activity.setEstimatedTime(Double.parseDouble(input));
																				}catch(Exception e) {
																					throw new Exception("Invalid input.");
																				}
																			}
																		});

																		appendMenuItem(new Event(softwaresystem,gui,"Time description","Press enter to continue.") {
																			public void event(String input) throws Exception {
																				gui.menuBack();
																				gui.printOutput("Estimated time left is: "+
																						Double.toString( activity.getTimeLeft())+ "hours");
																				gui.printOutput("Time spent on project: "+
																						Double.toString(activity.getTimeSpent())+ " hours");
																				gui.printOutput("Start week: "+activity.getStartWeek()+ " End week: "+ activity.getEndWeek());

																			}
																		});

																		appendMenuItem(new Event(softwaresystem,gui,"Set work period for activity","Insert in this format: startWeek,endWeek") {
																			public void event(String input) throws Exception {
																				gui.menuBack();
																				int startWeek = -1, endWeek = -1;
																				String[] modifiedInput = input.split(",");
																				// modify input
																				try {

																					if(modifiedInput.length == 2) {
																						startWeek=Integer.parseInt(modifiedInput[0]);
																						endWeek=Integer.parseInt(modifiedInput[1]);
																					}

																				}catch(Exception e) {
																					throw new Exception("Invalid Input");
																				}

																				try {
																					if(startWeek!=-1 && endWeek!=-1)
																						project.setWorkPeriodForActivity(activity, startWeek, endWeek);
																					else throw new Exception("Invalid input");
																				}catch(Exception e) {
																					throw new Exception(e.getMessage());
																				}


																			}
																		});

																	}
																});
															}
														}
													});

												}
											});
											this.appendMenuItem(new Event(softwaresystem,gui,"Get avaiable workers","Insert input as format: startweek,endweek. Hit enter") {
												public void event(String input) throws Exception {
													gui.menuBack();
													ArrayList<Employee> avaiableEmployees =  new ArrayList<Employee>();
													int startWeek=-1,endWeek=-1;

													try {
														String[] modifiedInput = input.split(",");

														if(modifiedInput.length==2) {
															startWeek = Integer.parseInt(modifiedInput[0]);
															endWeek = Integer.parseInt(modifiedInput[1]);
														}else {
															throw new Exception();
														}

													}catch(Exception e) {
														throw new Exception("invalid input");
													}

													if(startWeek!=-1 && endWeek!=-1)
														avaiableEmployees =  (ArrayList<Employee>) softwaresystem.getAvailableEmployees(startWeek, endWeek);
													if (avaiableEmployees.size()>0) {
														Menu avaiableworkersmenu = new Menu(softwaresystem,gui,"","Avaiable workers");
														for(Employee employee : avaiableEmployees) {
															avaiableworkersmenu.appendMenuItem(new MenuLabel(softwaresystem,gui,employee.toString()));
														}
														gui.appendMenuItemToDisplayMenu(avaiableworkersmenu);
													}
													else gui.printOutput("No avaiable employees");

												}
											});
										}
									});}}});}});	}};

		appendMenuItem(projectmenu);


		Menu employeemenu = new Menu(softwaresystem,gui,"Employee Menu","Employee Menu") {
			{
				appendMenuItem(new Event(softwaresystem,gui,"Create an employee","Insert id for new employee") {
					public void event(String input) throws Exception {
						gui.menuBack();
						softwaresystem.addEmployeeToSystem(new Employee(input));

					}
				});
				appendMenuItem(new Event(softwaresystem,gui,"See employees","Press enter to continue") {
					public void event(String input) throws Exception {
						gui.menuBack();
						gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"See employees","List of employees") {
							{
								for (Employee employee : softwaresystem.getEmployees()) {
									this.appendMenuItem(new Menu(softwaresystem,gui,employee.toString(),employee.toString()) {
										{
											if (employee.getProject()!= null) this.appendMenuItem(new MenuLabel(softwaresystem,gui,employee.getProject().toString()));
										}

									});
								}
							}
						});
					}
				});
				appendMenuItem(new Event(softwaresystem,gui,"Remove employee","Insert ID of employee from system.") {
					public void event(String input) throws Exception {
						gui.menuBack();
						softwaresystem.removeEmployeeFromSystem(softwaresystem.getEmployeeById(input));
					}
				});
			}
		};

		appendMenuItem(employeemenu);

		Event personalMenu = new Event(softwaresystem,gui,"My project","Press enter to continue") {
			public void event(String input) throws Exception {
				gui.menuBack();

				Employee user = softwaresystem.getSessionUser();
				Project project = softwaresystem.getSessionUser().getProject();
				boolean userHasProject = project!=null;
				String projectStatus;

				if(userHasProject) projectStatus = project.toString();
				else projectStatus = "You have no project";

				gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"My project","My project : "+projectStatus) {
					{
						if(userHasProject) {
							appendMenuItem(new Event(softwaresystem,gui,"My activities","Press enter to continue") {
								public void event(String input) throws Exception {
									gui.menuBack();
									gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"My activites","List of my activites") {
										{
											for(Activity activity : project.getActivitiesForEmployee(user)) {
												appendMenuItem(new Menu(softwaresystem,gui,activity.toString(),activity.toString()) {
													{
														appendMenuItem(new Event(softwaresystem,gui,"Set time worked","Insert time you have worked on this activity. Press enter") {
															public void event(String input) throws Exception {
																gui.menuBack();
																double inputToDouble = -1;
																try{
																	try{
																		inputToDouble = Double.parseDouble(input);
																	}
																	catch(Exception e) {throw new Exception("Invalid input");}
																	if(inputToDouble!=-1)
																		activity.setTimeWorkedOnActivity(inputToDouble);
																}catch(Exception e) {
																	throw new Exception(e.getMessage());
																}
															}
														});
														appendMenuItem(new Event(softwaresystem,gui,"Time description","Press enter to continue.") {
															public void event(String input) throws Exception {
																gui.menuBack();
																gui.printOutput("Estimated time left is: "+
																		Double.toString(activity.getTimeLeft())+ " hours");
																gui.printOutput("Time spent on project: "+
																		Double.toString(activity.getTimeSpent())+ " hours");

															}
														});
														appendMenuItem(new Event(softwaresystem,gui,"Recent times spent on activity","Press enter to continue.") {
															public void event(String input) throws Exception {
																gui.menuBack();
																gui.appendMenuItemToDisplayMenu(new Menu(softwaresystem,gui,"","Recent times spent on activity. Access a time spent to edit.") {
																	{
																		for(WorkRegistration time : activity.getWorkRegistrations()) {
																			appendMenuItem(new Event(softwaresystem,gui,time.toString(),"Edit the time. Insert new value") {
																				public void event(String input) throws Exception {
																					gui.menuBack();
																					gui.menuBack();
																					double inputToDouble;
																					try {
																						inputToDouble = Double.parseDouble(input);
																					}catch(Exception e) {
																						throw new Exception("Invalid input.");
																					}
																					activity.editTimeWorkedOnActivity(time,inputToDouble);

																				}});}}});}});}});}}});}});}}});}};
		appendMenuItem(personalMenu);
	}
}