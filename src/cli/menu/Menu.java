package cli.menu;

import java.util.ArrayList;

import cli.MainGUI.GUI;
import app.SoftwareSystem;

public class Menu extends MenuItem{

	ArrayList<MenuItem> menuitems = new ArrayList<MenuItem>();
	protected SoftwareSystem softwaresystem;
	protected GUI gui;
	public String name;
	
	
	public Menu(SoftwareSystem softwaresystem, GUI gui, String header, String description) {
		super(header, description);
		this.softwaresystem = softwaresystem;
		this.gui = gui;
	}

	@Override
	public void printEvent() {
		int i = 1;
		for (MenuItem item : menuitems) {
			System.out.printf("%d) %s%n",i,item.getHeader());
			i++;
		}
		System.out.println("0) Back");
	}		
	public void appendMenuItem(MenuItem item) {
		menuitems.add(item);
	}

	@Override
	public void event(String input) throws Exception {
			try {
				if(Integer.parseInt(input)!=0) 
					gui.appendMenuItemToDisplayMenu(menuitems.get(Integer.parseInt(input)-1));
				else gui.menuBack();
			}catch(Exception e) {
				throw new Exception("Invalid input");
			}
	}
}
