package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import animation.Animation;
import view.Canvas;

public class Object extends Thread implements Comparable<Object>, Serializable, ISaverReader {

	private static final long serialVersionUID = 3L;

	private Project project;

	private String nameID;
	private int x;
	private int y;
	private int speedX;
	private int speedY;
	private int width;
	private int height;
	private int refreshMove;
	private Skin skin;
	private ArrayList<Skin> skins;
	private Animation animation;
	private ArrayList<Animation> animations;

	public Object(String nameID) {

		this.nameID = nameID;

	}

	public Object(String nameID, Project project) {

		this.nameID = nameID;
		this.project = project;
		nameID = "";
		speedX = 0;
		speedY = 0;
		width = 100;
		height = 100;
		x = Canvas.WIDTH / 2 - width / 2;
		y = Canvas.HEIGHT / 2 - height / 2;
		skin = null;
		refreshMove = 10;
		animation = null;

		skins = new ArrayList<>();
		animations = new ArrayList<>();

		start();

	}

	public void addAnimation(String idName, BufferedImage spriteSheet, int frames, int delay, int col, int row,
			int timeRefresh, File spriteSelected) throws Exception {

		if (animations.isEmpty()) {

			Animation newAnimation = new Animation(spriteSheet, frames, delay, col, row, timeRefresh, idName);
			animations.add(animation);

			String pathObjectFolder = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID()
					+ "/MyObjects/" + nameID;
			newAnimation.createFolder(pathObjectFolder);

			// Saving SpriteSheet into folder.
			Application.saveFile(spriteSelected,
					pathObjectFolder + "/animations/" + idName + "/SpriteSheet/" + spriteSelected.getName());

			animation = newAnimation;
			animation.getThread().start();

		} else {

			int index = animExists(idName);

			if (index >= 0) {

				throw new Exception("There is an animation with the name ->" + idName);

			} else {

				Animation newAnimation = new Animation(spriteSheet, frames, delay, col, row, timeRefresh, idName);
				animations.add(Math.abs(index) - 1, animation);

				String pathObjectFolder = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID()
						+ "/MyObjects/" + nameID;
				newAnimation.createFolder(pathObjectFolder);

				// Saving SpriteSheet into folder.
				Application.saveFile(spriteSelected,
						pathObjectFolder + "/animations/SpriteSheet/" + spriteSelected.getName());

				animation = newAnimation;
				animation.getThread().start();

			}
		}

	}

	public int animExists(String nameID) {

		int index = Collections.binarySearch(animations, new Animation(nameID));

		return index;

	}

	public void addSkin(String idName, Image image, File skinSelected) throws Exception {

		if (skins.isEmpty()) {

			skin = new Skin(idName, image);
			skins.add(skin);

			String pathObjectFolder = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID()
					+ "/MyObjects/" + nameID;
			skin.createFolder(pathObjectFolder);

			// Saving SpriteSheet into folder.
			Application.saveFile(skinSelected, pathObjectFolder + "/skins/" + idName + "/" + skinSelected.getName());

		} else {

			int index = skinExists(idName);

			if (index >= 0) {

				throw new Exception("There is a skin with the name ->" + idName);

			} else {

				skin = new Skin(idName, image);
				skins.add(Math.abs(index) - 1, skin);

				String pathObjectFolder = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID()
						+ "/MyObjects/" + nameID;
				skin.createFolder(pathObjectFolder);

				// Saving SpriteSheet into folder.
				Application.saveFile(skinSelected,
						pathObjectFolder + "/skins/" + idName + "/" + skinSelected.getName());

			}
		}

	}

	// Returns a number >= 0 if the skin exists. Negative if the skin doesn't
	// exists.
	public int skinExists(String idName) {

		int index = Collections.binarySearch(skins, new Skin(idName));

		return index;
	}

	public void createFolders() {

		// The path to create the folder of this object.
		String myPath = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID() + "/MyObjects/"
				+ nameID;

		// Create the Object Folder.
		File myFolder = new File(myPath);
		myFolder.mkdirs();

		// Create the animations folder into this Object folder.
		File animsFolder = new File(myFolder.getAbsolutePath() + "/animations");
		animsFolder.mkdirs();

		File skinsFolder = new File(myFolder.getAbsolutePath() + "/skins");
		skinsFolder.mkdirs();

	}

	public void move() {

		if (x >= Canvas.WIDTH - width || x <= 0) {

			speedX *= -1;

		}

		if (y >= Canvas.HEIGHT - height || y <= 0) {

			speedY *= -1;

		}

		x += speedX;
		y += speedY;

	}

	public void render(Graphics g) {

		if (skin != null)
			g.drawImage(skin.getImage(), x, y, null);
		else
			g.drawRect(x, y, width, height);

		if (animation != null)
			animation.drawAnimation(g, x, y, 0);

	}

	@Override
	public void save() {

		String myPath = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID() + "/MyObjects/"
				+ nameID + "/animations";

		for (int i = 0; i < animations.size(); i++) {

			File output = new File(myPath + "/" + animations.get(i).getNameID() + "/CurrentImage/current.png");

			try {
				ImageIO.write(animations.get(i).getCurrentImage(), "png", output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// To recover all images (transient to serialize) saved into this object.
	// (Including Skins and Animations Classes)
	@Override
	public void read() {

		String myFolderSkins = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID()
				+ "/MyObjects/" + nameID + "/skins";

		String myFolderAnim = Application.PATH + "/ObjectAnimationFiles/MyProjects/" + project.getNameID()
				+ "/MyObjects/" + nameID + "/animations";

		File fileSkin = new File(myFolderSkins);
		File[] skinsFiles = fileSkin.listFiles();

		File fileAnim = new File(myFolderAnim);
		File[] animFiles = fileAnim.listFiles();

		try {

			for (int i = 0; i < skinsFiles.length; i++) {

				// Read all the skins images
				int index = skinExists(skinsFiles[i].getName());

				if (index >= 0) {

					File theSkin = skinsFiles[i].listFiles()[0];

					skins.get(index).setImage(ImageIO.read(theSkin));

					if (skins.get(i).getNameID().equals(skin.getNameID())) {

						// Set the current Skin
						skin = skins.get(i);

					}
				}
			}

			for (int i = 0; i < animFiles.length; i++) {

				int index = animExists(animFiles[i].getName());

				if (index >= 0) {

					String inFolder = myFolderAnim + "/" + animFiles[i].getName();
					File spriteFolder = new File(inFolder + "/SpriteSheet");
					File mySprite = spriteFolder.listFiles()[0];

					animations.get(index).setSpriteSheet(ImageIO.read(mySprite));

					animations.get(index).fillSprites();

					File currentImageFolder = new File(inFolder + "/CurrentImage");
					File myCurrentImage = currentImageFolder.listFiles()[0];

					animations.get(index).setCurrentImage(ImageIO.read(myCurrentImage));

				}

			}
			
			

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public ArrayList<Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(ArrayList<Animation> animations) {
		this.animations = animations;
	}

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public int getRefreshMove() {
		return refreshMove;
	}

	public void setRefreshMove(int refreshMove) {
		this.refreshMove = refreshMove;
	}

	public ArrayList<Skin> getSkins() {
		return skins;
	}

	public void setSkins(ArrayList<Skin> skins) {
		this.skins = skins;
	}

	@Override
	public void run() {

		while (true) {

			try {
				sleep(refreshMove);
				move();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	public Rectangle getBounds() {

		return new Rectangle(x, y, width, height);

	}

	public boolean equals(Object ob) {

		return nameID.equals(ob.getNameID());

	}

	@Override
	public int compareTo(Object o) {

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

		return "ID NAME: " + nameID + "\nAnimations:" + animations + "\nSkins:" + skins;

	}

}
