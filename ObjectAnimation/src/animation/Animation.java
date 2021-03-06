package animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

import model.ISaverReader;
import model.Object;
import threads.AnimationThread;

/**
 * This class represents the animation of puts sprite sheets in a sequence.
 * 
 * @author Juan Camilo V�lez Olaya
 *
 */
public class Animation implements Comparable<Animation>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	/**
	 * The speed of the animation
	 */
	private int delay;

	/**
	 * The quantity of frames (Sprite Sheets)
	 */
	private int frames;

	/**
	 * An auxiliar index
	 */
	private int index = 0;

	/**
	 * An auxiliar index to next frames method.
	 */
	private int count = 0;

	/**
	 * The number of columns of the image to run the animation.
	 */
	private int col;

	/**
	 * The number of rows of the image to run the animation
	 */
	private int row;

	/**
	 * It is the image to get the sprite sheets.
	 */
	private transient BufferedImage spriteSheet;

	/**
	 * It is an array of the sub images (sprites)
	 */
	private transient BufferedImage[] images;

	/**
	 * It is the current sprite for the animation.
	 */
	private transient BufferedImage currentImage;

	/**
	 * Variable to know if the animation has finished.
	 */
	private boolean alive;

	/**
	 * Variable to pause the animation
	 */
	private boolean pause;

	private String nameID;

	private AnimationThread thread;

	private int timeRefresh;

	public Animation(String nameID) {

		this.nameID = nameID;

	}

	/**
	 * Constructor to create an animation
	 * 
	 * @param image  The image with sprites.
	 * @param frames The quantity of sprites.
	 * @param speed  The speed of the animation
	 * @param col    The number of columns that the image has of sprites.
	 * @param row    The number of rows that the image has of sprites.
	 */
	public Animation(BufferedImage spriteSheet, int frames, int delay, int col, int row, int timeRefresh,
			String nameID) {

		this.spriteSheet = spriteSheet;
		this.frames = frames;
		this.delay = delay;
		this.col = col;
		this.row = row;
		this.timeRefresh = timeRefresh;
		this.nameID = nameID;
		thread = new AnimationThread(this);
		images = new BufferedImage[frames];
		alive = true;
		pause = false;

		fillSprites();

	}

	public void createFolder(String pathObjectFolder) {

		File animFolder = new File(pathObjectFolder + "/animations/" + nameID);
		animFolder.mkdirs();

		File spriteFolder = new File(pathObjectFolder + "/animations/" + nameID + "/SpriteSheet");
		spriteFolder.mkdirs();

		File currentImage = new File(pathObjectFolder + "/animations/" + nameID + "/CurrentImage");
		currentImage.mkdirs();

	}

	/**
	 * Puts all the sprites sheets in an array.
	 * 
	 */
	public void fillSprites() {

		SpriteSheet ss = new SpriteSheet(spriteSheet);

		int k = 0;

		for (int i = 1; i <= row; i++) {

			for (int j = 1; j <= col; j++, k++) {

				if(k<frames)
				images[k] = ss.grabImage(i, j, (int) spriteSheet.getWidth() / col, (int) spriteSheet.getHeight() / row);

			}

		}

	}

	/**
	 * Change the current image with the next sprite.
	 */
	public void nextFrame() {

		currentImage = images[count];

		count++;

		if (count >= frames) { // You can modify frames - 1 to another number if your image does not have
								// sprite sheets in all the image.
								// If your image has sprite sheets in all the image you can put only frames.
			alive = false;
			count = 0;

		}

	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public AnimationThread getThread() {
		return thread;
	}

	public void setThread(AnimationThread thread) {
		this.thread = thread;
	}

	public int getTimeRefresh() {
		return timeRefresh;
	}

	public void setTimeRefresh(int timeRefresh) {
		this.timeRefresh = timeRefresh;
	}

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	/**
	 * Uses the method next frames to simulate an animation changing frames in an
	 * execution
	 */
	public void runAnimation() {

		index++;
		if (index > delay) {

			index = 0;
			nextFrame();

		}

	}

	/**
	 * Draws the animation
	 * 
	 * @param g      The graphics to draw.
	 * @param x      Pos in X
	 * @param y      Pos in Y
	 * @param offset Move the position in X
	 */
	public void drawAnimation(Graphics g, double x, double y, int offset) {

		g.drawImage(currentImage, (int) x - offset, (int) y, null);

	}

	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}

	public void setFrames(int frames) {

		this.frames = frames;
	}

	public int getFrames() {
		return frames;
	}

	public void setCol(int col) {

		this.col = col;

	}

	public void setRow(int row) {

		this.row = row;

	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public int compareTo(Animation o) {

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

		return "Animation ID: " + nameID;

	}

}