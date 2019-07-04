package cli.menu;

import cli.MainGUI.GUI;
import app.SoftwareSystem;

public class Event extends MenuItem{

	protected SoftwareSystem softwaresystem;
	protected GUI gui;
	private String header, description;

	public Event(SoftwareSystem softwaresystem,GUI gui, String header, String description) {
		super(header, description);
		this.softwaresystem = softwaresystem;
		this.header = header;
		this.gui = gui;
		this.description = description;
	}

	@Override
	public void printEvent() {

	}

	@Override
	public void event(String input) throws Exception {
		//Override this on initiliazition to create event.
	}

}

