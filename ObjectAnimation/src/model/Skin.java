package model;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;

public class Skin implements Comparable<Skin>, Serializable {


	private static final long serialVersionUID = 5L;
	private String nameID;
	private transient Image image;

	public Skin(String nameID) {

		this.nameID = nameID;

	}

	public Skin(String nameID, Image image) {
		super();
		this.nameID = nameID;
		this.image = image;

	}

	public void createFolder(String pathObjectFolder) {

		File skinFolder = new File(pathObjectFolder + "/skins/" + nameID);
		skinFolder.mkdirs();

	}

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public int compareTo(Skin o) {

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

		return "Skin ID: " + nameID;

	}

}
