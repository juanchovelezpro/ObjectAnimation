package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import view.MainWindow;

public class Application {

	private ArrayList<Object> myObjects;

	private Object object;
	private Object currentObject;

	private BufferedImage currentSprite;
	private MainWindow window;

	public Application(MainWindow window) {

		this.window = window;
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

	public MainWindow getWindow() {
		return window;
	}

	public void setWindow(MainWindow window) {
		this.window = window;
	}

	public Object getCurrentObject() {
		return currentObject;
	}

	public void setCurrentObject(Object currentObject) {
		this.currentObject = currentObject;
	}
}
