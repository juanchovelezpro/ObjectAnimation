package view;

import java.awt.GraphicsEnvironment;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.UIManager;

import model.Application;
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
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

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
