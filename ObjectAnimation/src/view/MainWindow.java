package view;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.*;

import com.sun.javafx.runtime.SystemProperties;

import model.Application;
import threads.AnimationThread;
import threads.ThreadUpdater;
import tools.ImageLoader;

public class MainWindow extends JFrame {

	public static final int WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getWidth();
	public static final int HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getHeight();

	private Application app;
	private PanelOptions options;
	private Canvas canvas;
	private ThreadUpdater updater;

	public MainWindow() {

		setTitle("Object Animation");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);

		setIconImage(ImageLoader.cargarImagen("images/icon.jpg"));

		try {

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception ex) {

		}

		createSourceFolders();

		app = new Application(this);
		options = new PanelOptions(this);
		canvas = new Canvas(this);

		add(options);
		add(canvas);

		updater = new ThreadUpdater(this);
		updater.start();

	}

	public void createSourceFolders() {

		String dir = System.getProperty("user.dir");
		File folder = new File(dir + "/ObjectAnimationFiles");
		folder.mkdirs();

		File objects = new File(folder.getPath() + "/MyObjects");
		objects.mkdirs();

	}

	public PanelOptions getOptions() {
		return options;
	}

	public void setOptions(PanelOptions options) {
		this.options = options;
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public static void main(String[] args) {
		MainWindow m = new MainWindow();
		m.setVisible(true);
	}

}
