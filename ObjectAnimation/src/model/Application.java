package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Application {

	
	public static final String PATH = System.getProperty("user.home");
	
	
	private ArrayList<Project> projects;
	
	
	public Application() {
		
		projects = new ArrayList<>();
		
	}
	
	
	public static void main(String[] args) {
		
		String userHome = System.getProperty("user.home");
		
		File folder = new File(userHome);
		
		String[] files = folder.list();
		
		System.out.println(System.getProperty("user.home"));
		
		System.out.println(Arrays.toString(files));
		
	}
	
	
}
