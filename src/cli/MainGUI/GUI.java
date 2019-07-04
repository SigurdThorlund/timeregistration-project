package cli.MainGUI;

import java.util.ArrayList;
import java.util.Scanner;

import app.*;
import cli.gui.LoginScreen;
import cli.menu.MenuItem;

public class GUI {

	private ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
	private Scanner in = new Scanner(System.in);
	private SoftwareSystem softwaresystem = new SoftwareSystem();
	
	public GUI() throws Exception {
		
		this.menu.add(new LoginScreen(softwaresystem, this));
		
		this.run();
	}
	
	public void run() {
		boolean run  = true;
		
		while(run) {
			printHeader();
			try {
			makeAction();
			}catch(Exception e) {
				printError(e.getMessage());
			}
		}
	}
	
	public void makeAction() throws Exception{
		getCurrentMenuItem().printEvent();
		getCurrentMenuItem().event(awaitInput(in));
	}
	
	public void appendMenuItemToDisplayMenu(MenuItem item) {
		menu.add(item);
	}
	
	public void menuBack(){
		if(menu.size()>2) menu.remove(menu.size()-1);
		
		else if(menu.size() == 2) {
			menu.remove(menu.size()-1);
			softwaresystem.logout();
		}
		
		else printError("Cant go back from here");
	}
	
	public String awaitInput(Scanner in) {
		System.out.print("Input: ");
		return in.nextLine();
	}
	
	public MenuItem getCurrentMenuItem() {
		return menu.get(menu.size()-1);
	}
	
	private void printLine() {
		System.out.println("################################");	
	}
	
	private void printDescription() {
		System.out.println("# "+this.getCurrentMenuItem().getDescription());
	}
	public void printHeader() {
		System.out.println();
		System.out.println();
		printLine();
		printDescription();
		printLine();
	}
	
	public void printError(String msg){
		System.err.println(msg);
		try {
			Thread.sleep(2);
		}catch(InterruptedException ie) {
			System.err.println("Error 201. Something went wrong with the output thread!");
		}
	}
	
	public void printOutput(String msg) {
		System.out.println("");
		System.out.println(msg);
	}
	
}
