package cli.gui;

import cli.MainGUI.GUI;
import app.SoftwareSystem;
import cli.menu.Event;

public class LoginScreen extends Event{

	SoftwareSystem system;
	GUI gui;
	
	public LoginScreen(SoftwareSystem softwaresystem, GUI gui) {
		super(softwaresystem, gui, "", "Press your initials to login. Then enter");
		this.system = softwaresystem;
		this.gui = gui;
	}
	
	@Override
	public void event(String input) throws Exception {
		system.login(input);
		//If no expections thrown:
		gui.appendMenuItemToDisplayMenu(new MainMenu(system,gui));
		
	}

}
