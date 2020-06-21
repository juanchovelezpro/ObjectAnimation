package model;

import java.awt.image.BufferedImage;
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

public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Object> myObjects;

	private Object object;
	private Object currentObject;
	private transient BufferedImage currentSprite;

	public Project() {

		object = new Object(this);
		currentObject = object;
		currentSprite = null;
		myObjects = new ArrayList<>();

	}

	public void addObject(String idName) throws Exception {

		object.setNameID(idName);

		if (myObjects.isEmpty()) {

			currentObject = object;

			myObjects.add(object);

			String dir = System.getProperty("user.dir");

			File objectFolder = new File(dir + "/ObjectAnimationFiles/MyObjects/" + idName);
			objectFolder.mkdirs();

			File folders = new File(objectFolder + "/skins");
			folders.mkdirs();

			folders = new File(objectFolder + "/animations");
			folders.mkdirs();

			object = new Object(this);

		} else {

			int index = exists(idName);

			if (index >= 0) {

				throw new Exception("There is an object with this name already");

			} else {

				currentObject = object;

				myObjects.add(Math.abs(index) - 1, object);

				String dir = System.getProperty("user.dir");

				File objectFolder = new File(dir + "/ObjectAnimationFiles/MyObjects/" + idName);
				objectFolder.mkdirs();

				File folders = new File(objectFolder + "/skins");
				folders.mkdirs();

				folders = new File(objectFolder + "/animations");
				folders.mkdirs();

				object = new Object(this);

			}

		}

		System.out.println("List Objects Size: " + myObjects.size());
		System.out.println(myObjects.toString());

	}

	public int exists(String idName) {

		int index = Collections.binarySearch(myObjects, new Object(idName), new ComparatorObjectName());

		return index;
	}

	public ArrayList<Object> getMyObjects() {
		return myObjects;
	}

	public void setMyObjects(ArrayList<Object> myObjects) {
		this.myObjects = myObjects;
	}

	public void save() {

		FileOutputStream fS = null;
		ObjectOutputStream oS = null;
		ArrayList<Object> objects = myObjects;

		try {

			String userDir = System.getProperty("user.dir");
			File folderData = new File(userDir + "/ObjectAnimationFiles/data");
			folderData.mkdirs();

			fS = new FileOutputStream(folderData.getAbsolutePath() + "/app.dat", false);
			oS = new ObjectOutputStream(fS);

			oS.writeObject(objects);

		} catch (FileNotFoundException ex) {

			System.out.println(ex.getMessage());

		} catch (IOException ex) {

			System.out.println(ex.getMessage());

		} finally {

			try {

				if (objects != null) {

					fS.close();

				}
				if (oS != null) {

					oS.close();

				}

			} catch (IOException ex) {

				System.out.println(ex.getMessage());

			}
		}

	}

	public void read() {

		FileInputStream fS = null;
		ObjectInputStream oS = null;

		ArrayList<Object> objects = null;

		String userDir = System.getProperty("user.dir");
		String fullPath = userDir + "/ObjectAnimationFiles/data/app.dat";
		File verify = new File(fullPath);
		if (verify.exists()) {

			try {

				fS = new FileInputStream(fullPath);
				oS = new ObjectInputStream(fS);
				ArrayList<Object> readObject2 = (ArrayList<Object>) oS.readObject();
				ArrayList<Object> readObject = readObject2;
				objects = readObject;

				myObjects = objects;

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

			System.out.println("Reading Objects... \n" + myObjects);

			for (int i = 0; i < myObjects.size(); i++) {

				myObjects.get(i).read();

			}

			initAllThreads();
		}

	}

	public void saveFile(JFileChooser chooser, String type) {

		Path from = Paths.get(chooser.getSelectedFile().toURI());

		String dest = System.getProperty("user.dir");

		Path to = null;

		if (type.equals("skin"))
			to = Paths.get(dest + "/" + "ObjectAnimationFiles/MyObjects/" + currentObject.getNameID() + "/" + "skins/"
					+ chooser.getSelectedFile().getName());

		if (type.equals("animation"))
			to = Paths.get(dest + "/" + "ObjectAnimationFiles/MyObjects/" + currentObject.getNameID() + "/"
					+ "animations/" + chooser.getSelectedFile().getName());

		try {
			Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Data saved.");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String getPathSkins() {

		String dest = System.getProperty("user.dir");
		String skinsPath = dest + "\\" + "ObjectAnimationFiles" + "\\MyObjects\\" + currentObject.getNameID()
				+ "\\animations\\";

		return skinsPath;

	}

	public void initAllThreads() {

		for (int i = 0; i < myObjects.size(); i++) {

			myObjects.get(i).start();

		}

	}

	public BufferedImage getCurrentSprite() {
		return currentSprite;
	}

	public void setCurrentSprite(BufferedImage currentSprite) {
		this.currentSprite = currentSprite;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getCurrentObject() {
		return currentObject;
	}

	public void setCurrentObject(Object currentObject) {
		this.currentObject = currentObject;
	}
}
