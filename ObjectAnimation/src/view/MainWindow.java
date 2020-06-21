package view;

import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import model.Application;
import model.Project;
import threads.ThreadUpdater;
import tools.ImageLoader;

public class MainWindow extends JFrame {

	public static final int WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getWidth();
	public static final int HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getHeight();

	private boolean isFullScreen = true;

	private Application app;
	private PanelOptions options;
	private Canvas canvas;
	private ThreadUpdater updater;

	public MainWindow() {

		setTitle("Object Animation");
		setLayout(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		onClose();

		setIconImage(ImageLoader.cargarImagen("images/icon.jpg"));

		try {

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		app = new Application();

		createOrChooseProject();

		options = new PanelOptions(this);
		canvas = new Canvas(this);

		add(options);
		add(canvas);

		updater = new ThreadUpdater(this);
		updater.start();
		
		app.getCurrentProject().initAllThreads();

	}

	public void createOrChooseProject() {

		String[] options = { "Create a project", "Choose a project" };

		int option = JOptionPane.showOptionDialog(this, "Select an option", "What you want to do?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (option != -1) {

			switch (option) {

			case 0:

				createProject();

				break;

			case 1:

				chooseProject();

				break;

			}
		} else {

			System.exit(0);

		}

	}

	public void createProject() {

		String nameProject = JOptionPane.showInputDialog(this, "Enter a name to your project", "Project Name",
				JOptionPane.QUESTION_MESSAGE);

		if (nameProject != null && !nameProject.equals("")) {
			try {
				app.addProject(nameProject);
			} catch (Exception e) {

				JOptionPane.showMessageDialog(this, e.getMessage(), "Error creating Project",
						JOptionPane.ERROR_MESSAGE);

				createOrChooseProject();

			}

		} else {
			JOptionPane.showMessageDialog(this, "It can't create a project with an empty name.\nTry again",
					"Error Creating Project", JOptionPane.ERROR_MESSAGE);

			createOrChooseProject();

		}

	}

	public void chooseProject() {

		int size = app.getProjects().size();

		if (size > 0) {

			String[] listProjects = new String[size];

			for (int i = 0; i < size; i++) {

				listProjects[i] = app.getProjects().get(i).getNameID();

			}

			String project = (String) JOptionPane.showInputDialog(this, "Select a project", "Projects",
					JOptionPane.DEFAULT_OPTION, null, listProjects, listProjects[0]);

			if (project != null) {

				int index = Collections.binarySearch(app.getProjects(), new Project(project));

				app.setCurrentProject(app.getProjects().get(index));

			} else {

				createOrChooseProject();

			}

		} else {

			JOptionPane.showMessageDialog(this, "There are no projects. Try to create a new project",
					"No Projects Found", JOptionPane.ERROR_MESSAGE);

			createOrChooseProject();

		}

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

					System.out.println("Log: " + app.getProjects());

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

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
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
