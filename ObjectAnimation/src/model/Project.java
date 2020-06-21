package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Project implements Comparable<Project>, Serializable, ISaverReader {

	private static final long serialVersionUID = 2L;

	private String nameID;
	private ArrayList<Object> myObjects;
	private Object currentObject;

	private transient BufferedImage currentSpriteSheet;

	public Project(String nameID) {

		this.nameID = nameID;
		currentObject = null;
		myObjects = new ArrayList<>();

	}

	public void createFolder() {

		File folder = new File(Application.PATH + "/ObjectAnimationFiles/myProjects/" + nameID);
		folder.mkdirs();
	}

	public void addObject(String idName) throws Exception {

		if (myObjects.isEmpty()) {

			currentObject = new Object(idName, this);

			myObjects.add(currentObject);

			currentObject.createFolders();

		} else {

			int index = exists(idName);

			if (index >= 0) {

				throw new Exception("There is an object with this name already");

			} else {

				currentObject = new Object(idName, this);

				myObjects.add(Math.abs(index) - 1, currentObject);

				currentObject.createFolders();

			}

		}

	}

	public int exists(String idName) {

		int index = Collections.binarySearch(myObjects, new Object(idName));

		return index;
	}

	public ArrayList<Object> getMyObjects() {
		return myObjects;
	}

	public void setMyObjects(ArrayList<Object> myObjects) {
		this.myObjects = myObjects;
	}

	public void save() {

		for (int i = 0; i < myObjects.size(); i++) {

			myObjects.get(i).save();

		}

	}

	public void read() {

		for (int i = 0; i < myObjects.size(); i++) {

			myObjects.get(i).read();

		}

	}

	public void initAllThreads() {

		for (int i = 0; i < myObjects.size(); i++) {

			myObjects.get(i).start();
			
				for(int j = 0 ; j<myObjects.get(i).getAnimations().size();j++) {
					
					if(myObjects.get(i).getAnimations().get(j) != null)
					myObjects.get(i).getAnimations().get(j).getThread().start();
					
				}

		}

	}

	public Object getCurrentObject() {
		return currentObject;
	}

	public void setCurrentObject(Object currentObject) {
		this.currentObject = currentObject;
	}

	public BufferedImage getCurrentSpriteSheet() {
		return currentSpriteSheet;
	}

	public void setCurrentSpriteSheet(BufferedImage currentSpriteSheet) {
		this.currentSpriteSheet = currentSpriteSheet;
	}

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	@Override
	public int compareTo(Project o) {

		if (nameID.compareTo(o.getNameID()) > 0) {

			return 1;

		} else if (nameID.compareTo(o.getNameID()) < 0) {

			return -1;

		} else {

			return 0;

		}

	}

	@Override
	public String toString() {

		return "Project ID: " + nameID + "\nObjects:" + myObjects;

	}

}
