package cli.menu;

import java.util.ArrayList;

import cli.MainGUI.GUI;
import app.SoftwareSystem;

public class MenuFromList extends Menu{

	String header, description;
	ArrayList<Object> listitems;
	
	public MenuFromList(SoftwareSystem softwaresystem, GUI gui,String header, String description, ArrayList<Object> listitems) {
		super(softwaresystem, gui, header, description);
		this.description = description;
		this.header = header;
		this.listitems = listitems;
		createMenuFromList();
		
	}

	@Override
	public void printEvent() {
		
	}
	
	private void createMenuFromList() {
		
	}

}
