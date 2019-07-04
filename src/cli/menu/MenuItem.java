package cli.menu;

import app.SoftwareSystem;

public abstract class MenuItem{

	private String header, description;
	protected SoftwareSystem softwaresystem;
	
	public MenuItem(String header, String description) {
		this.header = header; 
		this.description = description;
	}
	
	public abstract void printEvent();
	
	public abstract void event(String input) throws Exception;
	
	public boolean employee_has_permission(String employID) {
		return true;
	}
	
	public String getHeader() {
		return header;
	}
	public String getDescription() {
		return description;
	}
	
}
