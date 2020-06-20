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
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import com.sun.javafx.runtime.SystemProperties;

import view.MainWindow;

public class Application implements Serializable {

	private ArrayList<Object> myObjects;

	private Object object;
	private Object currentObject;
	private BufferedImage currentSprite;

	public Application() {

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

			object = new Object(this);

		} else {

			int index = exists(idName);
			System.out.println("index " + index);

			if (index >= 0) {

				throw new Exception("There is an object with this name already");

			} else {

				currentObject = object;

				myObjects.add(Math.abs(index) - 1, object);

				String dir = System.getProperty("user.dir");

				File objectFolder = new File(dir + "/ObjectAnimationFiles/MyObjects/" + idName);
				objectFolder.mkdirs();

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
				objects = (ArrayList<Object>) oS.readObject();

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
			initAllThreads();
		}

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
