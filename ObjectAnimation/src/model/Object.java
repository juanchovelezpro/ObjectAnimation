package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import animation.Animation;
import view.Canvas;

public class Object extends Thread implements Comparable<Object> {

	private ArrayList<Animation> animations;

	private String nameID;
	private int x;
	private int y;
	private int speedX;
	private int speedY;
	private int width;
	private int height;
	private Image sprite;
	private int refreshMove;
	private Animation animation;
	private Application app;

	public Object(Application app) {

		this.app = app;
		nameID = "";
		speedX = 0;
		speedY = 0;
		width = 100;
		height = 100;
		x = Canvas.WIDTH / 2 - width / 2;
		y = Canvas.HEIGHT / 2 - height / 2;
		sprite = null;
		refreshMove = 10;
		animation = null;

		animations = new ArrayList<>();

		start();

	}

	public void addAnimation(String idName) {

//		Path from = Paths.get(chooser.getSelectedFile().toURI());
//
//		String dest = System.getProperty("user.dir");
//		Path to = Paths.get(dest + "\\" + "ObjectAnimationFiles\\" + chooser.getSelectedFile().getName());
//
//		Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
	}

	public int exists(String idName) {

		return 0;
	}

	public ArrayList<Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(ArrayList<Animation> animations) {
		this.animations = animations;
	}

	public Object(String nameID) {

		this.nameID = nameID;

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

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
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

	public void action() {

	}

	public void render(Graphics g) {

		if (sprite != null)
			g.drawImage(sprite, x, y, null);
		else
			g.drawRect(x, y, width, height);

		if (animation != null)
			animation.drawAnimation(g, x, y, 0);

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

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public int getRefreshMove() {
		return refreshMove;
	}

	public void setRefreshMove(int refreshMove) {
		this.refreshMove = refreshMove;
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

		} else {

			return -1;

		}

	}

	@Override
	public String toString() {

		return "ID NAME:" + nameID + ", X: " + x;

	}

}
