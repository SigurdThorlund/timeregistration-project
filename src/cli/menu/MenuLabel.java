package cli.menu;

import cli.MainGUI.GUI;
import app.SoftwareSystem;

public class MenuLabel extends Event{

	public MenuLabel(SoftwareSystem softwaresystem, GUI gui, String header) {
		super(softwaresystem, gui, header, "Press enter to get back");
	}

	@Override
	public void event(String input) throws Exception {
		gui.menuBack();
	}
}

