package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;

public class Application implements ISaverReader, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PATH = System.getProperty("user.home");

	private ArrayList<Project> projects;
	private Project currentProject;

	public Application() {

		createSourceFolder();
		projects = new ArrayList<>();
		currentProject = null;
		read();

	}

	public void addProject(String nameID) throws Exception {

		if (projects.isEmpty()) {

			currentProject = new Project(nameID);
			currentProject.createFolder();
			projects.add(currentProject);

		} else {

			int index = exists(nameID);

			if (index >= 0) {

				throw new Exception("There is another project with the name ->" + nameID);

			} else {

				currentProject = new Project(nameID);
				currentProject.createFolder();
				projects.add(Math.abs(index) - 1, currentProject);

			}

		}

	}

	public void removeProject(String nameID) throws Exception {

		int index = exists(nameID);

		if (index >= 0) {

			projects.remove(Math.abs(index) - 1);

		} else {

			throw new Exception("There is no a project with the name ->" + nameID);

		}

	}

	public int exists(String nameID) {

		int index = Collections.binarySearch(projects, new Project(nameID));

		return index;
	}

	private void createSourceFolder() {

		File folder = new File(PATH + "/ObjectAnimationFiles");
		folder.mkdirs();

		File objects = new File(folder.getPath() + "/MyProjects");
		objects.mkdirs();

		File dataFolder = new File(folder.getPath() + "/data");
		dataFolder.mkdirs();

	}

	public static void saveFile(File selectedFile, String target) {

		Path from = Paths.get(selectedFile.toURI());

		Path to = Paths.get(target);

		try {
			Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Data saved.");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void save() {

		FileOutputStream fS = null;
		ObjectOutputStream oS = null;


		try {

			currentProject.save();
			
			File folderData = new File(PATH + "/ObjectAnimationFiles/data");
			folderData.mkdirs();

			fS = new FileOutputStream(folderData.getAbsolutePath() + "/app.dat", false);
			oS = new ObjectOutputStream(fS);

			oS.writeObject(projects);

			System.out.println("Log: Saving Projects -> Projects ID: " + projects);
			

		} catch (FileNotFoundException ex) {

			System.out.println(ex.getMessage());

		} catch (IOException ex) {

			System.out.println(ex.getMessage());

		} finally {

			try {

				if (projects != null) {

					fS.close();

				}
				if (oS != null) {

					oS.close();

				}

			} catch (IOException ex) {

				System.out.println("An error ocurred saving... "+ ex.getMessage());

			}
		}

	}

	@Override
	public void read() {

		FileInputStream fS = null;
		ObjectInputStream oS = null;

		String fullPath = PATH + "/ObjectAnimationFiles/data/app.dat";
		File verify = new File(fullPath);
		if (verify.exists()) {

			try {

				fS = new FileInputStream(fullPath);
				oS = new ObjectInputStream(fS);
				projects = (ArrayList<Project>) oS.readObject();

			} catch (Exception ex) {

				ex.printStackTrace();

			} finally {

				try {
					if (fS != null) {
						fS.close();
					}
					if (oS != null) {
						oS.close();
					}
				} catch (IOException e) {

					e.printStackTrace();

				}

			}

			System.out.println("Log:  Reading Projects... \n" + projects);
			for (int i = 0; i < projects.size(); i++) {

				projects.get(i).read();

			}

		}

	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}

}
