package view;

import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import model.Project;
import threads.ThreadUpdater;
import tools.ImageLoader;

public class MainWindow extends JFrame {

	public static final int WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getWidth();
	public static final int HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getHeight();

	private boolean isFullScreen = true;
	private Project app;
	private PanelOptions options;
	private Canvas canvas;
	private ThreadUpdater updater;

	public MainWindow() {

		setTitle("Object Animation");
		setLayout(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		createSourceFolders();
		onClose();
		setSize(WIDTH, HEIGHT);
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		setIconImage(ImageLoader.cargarImagen("images/icon.jpg"));

		try {

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception ex) {

			ex.printStackTrace();
			
		}

		app = new Project();
		app.read();
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

		File dataFolder = new File(folder.getPath() + "/data");
		dataFolder.mkdirs();

	}

	public void onClose() {

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {

				String[] options = { "Back to Application", "Save & Exit", "Exit without saving" };

				int ans = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Exit Object Animation",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				switch (ans) {

				case 1:
					app.save();
					System.out.println("Saved.");
					dispose();
					setVisible(false);
					System.exit(0);
					break;

				case 2:
					System.out.println("App closed without saving.");
					dispose();
					setVisible(false);
					System.exit(0);
					break;

				}

			}

		});

	}

	public PanelOptions getOptions() {
		return options;
	}

	public void setOptions(PanelOptions options) {
		this.options = options;
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}

	public Project getApp() {
		return app;
	}

	public void setApp(Project app) {
		this.app = app;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public static void main(String[] args) {
		MainWindow m = new MainWindow();
		m.setVisible(true);
	}

}
