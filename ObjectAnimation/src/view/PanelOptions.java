package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import animation.Animation;
import model.Object;
import tools.ImageLoader;

public class PanelOptions extends JPanel implements MouseMotionListener, MouseListener {

	public static final int WIDTH = 310;
	public static final int HEIGHT = MainWindow.HEIGHT * 4;

	private MainWindow window;

	private File fileSelectedSprite;
	private File fileSelectedSkin;
	private File backgroundImageCanvas;
	private JLabel labNoSprites;
	private JSpinner spinnerRows;
	private JSpinner spinnerCols;
	private JSpinner spinnerNumSprites;
	private JSpinner spinnerDelay;
	private JSpinner spinnerPosX;
	private JSpinner spinnerPosY;
	private JSpinner spinnerSpeedX;
	private JSpinner spinnerSpeedY;
	private JSpinner spinnerTimeRefresh;
	private JSpinner spinnerWidth;
	private JSpinner spinnerHeight;
	private JSpinner spinnerRefreshMove;

	// Point to get the origin position when the panel gonna be dragged up or down.
	private Point origin;
	private JButton btnCreateObject;
	private JLabel lblOptions;
	private JButton btnAddAnimation;
	private JButton btnFullScreen;
	private JButton btnAddSprite;
	private JButton btnMyObjects;
	private JButton btnBackgroundImage;
	private JButton btnUploadSkin;
	private JButton btnSettings;
	private JButton btnCanvasColor;
	private JLabel lblPosX;
	private JLabel lblDelay;
	private JLabel lblBackgroundImage;
	private JLabel lblNumSprites;
	private JLabel lblAnimation;
	private JLabel lblSpeedX;
	private JLabel lblRefreshMove;
	private JLabel lblCanvas;
	private JLabel lblPosY;
	private JLabel lblCurrentObject;
	private JLabel lblPlayPause;
	private JLabel lblHeight;
	private JLabel lblCols;
	private JLabel lblWidth;
	private JLabel lblRows;
	private JLabel lblObject;
	private JLabel lblTimeRefresh;
	private JLabel lblBackgroundColor;
	private JLabel lblSprite;
	private JLabel lblSpeedY;
	private JPanel panelOptions;
	private JPanel panelAnimation;
	private JPanel panelCanvas;
	private JPanel panelSpriteSheet;
	private JPanel panelObject;
	private JPanel aux;

	public PanelOptions(MainWindow window) {

		this.window = window;
		setLayout(new GridLayout(1, 1));
		setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		setSize(WIDTH, HEIGHT);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLocation(MainWindow.WIDTH - WIDTH, 0);

		settingComponents();

		addMouseListener(this);
		addMouseMotionListener(this);

	}

	public void settingComponents() {

		aux = new JPanel();

		SpringLayout sl_aux = new SpringLayout();
		aux.setLayout(sl_aux);

		panelSpriteSheet = new JPanel();
		sl_aux.putConstraint(SpringLayout.NORTH, panelSpriteSheet, 422, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.WEST, panelSpriteSheet, 1, SpringLayout.WEST, this);
		sl_aux.putConstraint(SpringLayout.SOUTH, panelSpriteSheet, 592, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.EAST, panelSpriteSheet, 307, SpringLayout.WEST, this);
		aux.add(panelSpriteSheet);
		panelSpriteSheet.setLayout(null);

		lblSprite = new JLabel("Sprite Sheet");
		lblSprite.setBounds(10, 0, 300, 45);
		panelSpriteSheet.add(lblSprite);
		lblSprite.setFont(new Font("Lato", Font.BOLD, 25));
		lblSprite.setHorizontalAlignment(SwingConstants.CENTER);

		labNoSprites = new JLabel("No sprites found!");
		labNoSprites.setBounds(10, 44, 300, 15);
		panelSpriteSheet.add(labNoSprites);
		labNoSprites.setHorizontalAlignment(SwingConstants.CENTER);
		labNoSprites.setFont(new Font("Lato", Font.BOLD, 12));

		btnAddSprite = new JButton("Add Sprite Sheet");
		btnAddSprite.setBounds(39, 61, 239, 31);
		panelSpriteSheet.add(btnAddSprite);

		lblRows = new JLabel("# Rows");
		lblRows.setHorizontalAlignment(SwingConstants.CENTER);
		lblRows.setBounds(10, 101, 76, 26);
		panelSpriteSheet.add(lblRows);
		lblRows.setFont(new Font("Lato", Font.BOLD, 15));

		lblCols = new JLabel("# Cols");
		lblCols.setHorizontalAlignment(SwingConstants.CENTER);
		lblCols.setBounds(108, 100, 76, 28);
		panelSpriteSheet.add(lblCols);
		lblCols.setFont(new Font("Lato", Font.BOLD, 15));

		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 9999, 1);

		SpinnerNumberModel model2 = new SpinnerNumberModel(1, 1, 9999, 1);

		spinnerRows = new JSpinner(model);
		spinnerRows.setBounds(12, 124, 84, 26);
		panelSpriteSheet.add(spinnerRows);
		spinnerCols = new JSpinner(model2);
		spinnerCols.setBounds(110, 124, 84, 26);
		panelSpriteSheet.add(spinnerCols);

		lblNumSprites = new JLabel("# Sprites");
		lblNumSprites.setBounds(215, 99, 63, 31);
		panelSpriteSheet.add(lblNumSprites);
		lblNumSprites.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerNumSprites = new JSpinner();
		spinnerNumSprites.setBounds(206, 124, 84, 26);
		panelSpriteSheet.add(spinnerNumSprites);
		spinnerNumSprites.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 162, 310, 2);
		panelSpriteSheet.add(separator);
		spinnerCols.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				int rows = Integer.parseInt(spinnerRows.getModel().getValue().toString());
				int cols = Integer.parseInt(spinnerCols.getModel().getValue().toString());

				spinnerNumSprites.setModel(new SpinnerNumberModel(rows * cols, 1, rows * cols, 1));

			}
		});
		spinnerRows.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int rows = Integer.parseInt(spinnerRows.getModel().getValue().toString());
				int cols = Integer.parseInt(spinnerCols.getModel().getValue().toString());

				spinnerNumSprites.setModel(new SpinnerNumberModel(rows * cols, 1, rows * cols, 1));
			}
		});
		btnAddSprite.addActionListener(a -> {

			// Adding a sprite sheet
			JFileChooser chooser = new JFileChooser();
			chooser.showDialog(window, "Upload Sprites");

			String pathImage = "";
			BufferedImage sprite = null;

			try {

				if (chooser.getSelectedFile() != null) {
					fileSelectedSprite = chooser.getSelectedFile();
					pathImage = fileSelectedSprite.getAbsolutePath();
					sprite = ImageIO.read(new File(pathImage));
					window.getApp().setCurrentSprite(sprite);
					labNoSprites.setText(fileSelectedSprite.getName());

					// Resized image to the Dialog

					Image resized = ImageLoader.resizeToFit(sprite, new Dimension(250, 250));

					JOptionPane.showMessageDialog(window, "The Sprite has been uploaded successfully",
							"Sprite Uploaded", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(resized));

				}

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		});

		panelObject = new JPanel();
		sl_aux.putConstraint(SpringLayout.NORTH, panelObject, 167, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.WEST, panelObject, 1, SpringLayout.WEST, this);
		sl_aux.putConstraint(SpringLayout.SOUTH, panelObject, 422, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.EAST, panelObject, 306, SpringLayout.WEST, this);
		aux.add(panelObject);
		panelObject.setLayout(null);

		lblObject = new JLabel("Object");
		lblObject.setBounds(1, 6, 306, 45);
		panelObject.add(lblObject);
		lblObject.setHorizontalAlignment(SwingConstants.CENTER);
		lblObject.setFont(new Font("Lato", Font.BOLD, 25));

		lblPosX = new JLabel("x");
		lblPosX.setBounds(14, 139, 61, 26);
		panelObject.add(lblPosX);
		lblPosX.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosX.setFont(new Font("Lato", Font.BOLD, 15));

		lblPosY = new JLabel("y");
		lblPosY.setBounds(160, 138, 55, 28);
		panelObject.add(lblPosY);
		lblPosY.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosY.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerPosX = new JSpinner();
		spinnerPosX.setBounds(72, 139, 76, 26);
		panelObject.add(spinnerPosX);

		spinnerPosY = new JSpinner();
		spinnerPosY.setBounds(213, 139, 76, 26);
		panelObject.add(spinnerPosY);

		lblSpeedX = new JLabel("Speed X");
		lblSpeedX.setBounds(12, 173, 63, 31);
		panelObject.add(lblSpeedX);
		lblSpeedX.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerSpeedX = new JSpinner();
		spinnerSpeedX.setBounds(72, 175, 76, 26);
		panelObject.add(spinnerSpeedX);

		lblSpeedY = new JLabel("Speed Y");
		lblSpeedY.setBounds(151, 175, 67, 26);
		panelObject.add(lblSpeedY);
		lblSpeedY.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerSpeedY = new JSpinner();
		spinnerSpeedY.setBounds(211, 175, 76, 26);
		panelObject.add(spinnerSpeedY);

		lblRefreshMove = new JLabel("Refresh Move (ms)");
		lblRefreshMove.setBounds(2, 210, 117, 31);
		panelObject.add(lblRefreshMove);
		lblRefreshMove.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefreshMove.setFont(new Font("Lato", Font.BOLD, 13));

		spinnerRefreshMove = new JSpinner();
		spinnerRefreshMove.setBounds(116, 213, 174, 26);
		panelObject.add(spinnerRefreshMove);
		spinnerRefreshMove.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setRefreshMove(Integer.parseInt(spinnerRefreshMove.getModel().getValue().toString()));

			}
		});
		spinnerRefreshMove.setModel(new SpinnerNumberModel(new Integer(10), new Integer(1), null, new Integer(1)));

		lblWidth = new JLabel("Width");
		lblWidth.setBounds(12, 108, 61, 26);
		panelObject.add(lblWidth);
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		lblWidth.setFont(new Font("Lato", Font.BOLD, 15));

		lblHeight = new JLabel("Height");
		lblHeight.setBounds(146, 110, 67, 22);
		panelObject.add(lblHeight);
		lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeight.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerWidth = new JSpinner();
		spinnerWidth.setBounds(70, 108, 76, 26);
		panelObject.add(spinnerWidth);
		spinnerWidth.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				window.getApp().getCurrentObject()
						.setWidth(Integer.parseInt(spinnerWidth.getModel().getValue().toString()));
				;
			}
		});
		spinnerWidth.setModel(new SpinnerNumberModel(new Integer(100), new Integer(10), null, new Integer(1)));

		spinnerHeight = new JSpinner();
		spinnerHeight.setBounds(211, 108, 76, 26);
		panelObject.add(spinnerHeight);
		spinnerHeight.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				window.getApp().getCurrentObject()
						.setHeight(Integer.parseInt(spinnerHeight.getModel().getValue().toString()));
				;

			}
		});
		spinnerHeight.setModel(new SpinnerNumberModel(new Integer(100), new Integer(10), null, new Integer(1)));

		lblCurrentObject = new JLabel("Current Object: null");
		lblCurrentObject.setBounds(2, 48, 305, 15);
		panelObject.add(lblCurrentObject);
		lblCurrentObject.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentObject.setFont(new Font("Lato", Font.BOLD, 12));

		btnUploadSkin = new JButton("Add Skin");
		btnUploadSkin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Adding a skin to the object

				JFileChooser chooser = new JFileChooser();
				chooser.showDialog(window, "Add Skin to Object");

				String pathImage = "";
				BufferedImage skin = null;

				try {

					if (chooser.getSelectedFile() != null) {
						fileSelectedSkin = chooser.getSelectedFile();
						pathImage = fileSelectedSkin.getAbsolutePath();
						skin = ImageIO.read(new File(pathImage));

						window.getApp().saveFile(chooser, "skin");

						window.getApp().getCurrentObject().setSkin(skin);

					}

				} catch (Exception ex) {

					ex.printStackTrace();

				}

			}
		});
		btnUploadSkin.setBounds(35, 67, 239, 31);
		panelObject.add(btnUploadSkin);

		JSeparator separator_1_1_2_1 = new JSeparator();
		separator_1_1_2_1.setBounds(-21, 247, 326, 2);
		panelObject.add(separator_1_1_2_1);
		spinnerSpeedY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setSpeedY(Integer.parseInt(spinnerSpeedY.getModel().getValue().toString()));

			}
		});
		spinnerSpeedX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setSpeedX(Integer.parseInt(spinnerSpeedX.getModel().getValue().toString()));

			}
		});
		spinnerPosY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject().setY(Integer.parseInt(spinnerPosY.getModel().getValue().toString()));
			}
		});
		spinnerPosY.getModel().setValue(window.getApp().getObject().getY());
		spinnerPosX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject().setX(Integer.parseInt(spinnerPosX.getModel().getValue().toString()));

			}
		});
		spinnerPosX.getModel().setValue(window.getApp().getObject().getX());

		panelAnimation = new JPanel();
		sl_aux.putConstraint(SpringLayout.NORTH, panelAnimation, 587, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.WEST, panelAnimation, 1, SpringLayout.WEST, this);
		sl_aux.putConstraint(SpringLayout.SOUTH, panelAnimation, 746, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.EAST, panelAnimation, 307, SpringLayout.WEST, this);
		aux.add(panelAnimation);
		panelAnimation.setLayout(null);

		lblAnimation = new JLabel("Animation");
		lblAnimation.setBounds(93, 6, 133, 43);
		panelAnimation.add(lblAnimation);
		lblAnimation.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnimation.setFont(new Font("Lato", Font.BOLD, 25));

		lblTimeRefresh = new JLabel("Time Refresh (ms)");
		lblTimeRefresh.setBounds(6, 51, 122, 31);
		panelAnimation.add(lblTimeRefresh);
		lblTimeRefresh.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeRefresh.setFont(new Font("Lato", Font.BOLD, 14));

		spinnerTimeRefresh = new JSpinner();
		spinnerTimeRefresh.setBounds(130, 53, 174, 26);
		panelAnimation.add(spinnerTimeRefresh);
		spinnerTimeRefresh.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

			}
		});
		spinnerTimeRefresh.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(0, 157, 326, 2);
		panelAnimation.add(separator_1_1);

		lblPlayPause = new JLabel("");
		lblPlayPause.setBounds(239, 9, 40, 40);
		panelAnimation.add(lblPlayPause);
		lblPlayPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				// Resume and pause current object's animation
				Animation currentAnimation = window.getApp().getCurrentObject().getAnimation();

				if (currentAnimation != null) {
					if (!currentAnimation.isPause()) {
						currentAnimation.setPause(true);
					} else {
						currentAnimation.setPause(false);
						currentAnimation.getThread().resumeAnimation();
					}

				}
			}
		});
		BufferedImage iconPlay = ImageLoader.cargarSprites("images/playpause.png");
		Image myIconPlay = iconPlay.getScaledInstance(lblPlayPause.getWidth(), lblPlayPause.getHeight(),
				Image.SCALE_SMOOTH);
		lblPlayPause.setIcon(new ImageIcon(myIconPlay));

		lblDelay = new JLabel("Delay");
		lblDelay.setBounds(6, 80, 122, 28);
		panelAnimation.add(lblDelay);
		lblDelay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDelay.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerDelay = new JSpinner();
		spinnerDelay.setBounds(130, 81, 174, 26);
		panelAnimation.add(spinnerDelay);
		spinnerDelay.setModel(new SpinnerNumberModel(0, 0, 1000, 1));

		btnAddAnimation = new JButton("Add Animation to Object");
		btnAddAnimation.setBounds(70, 120, 190, 31);
		panelAnimation.add(btnAddAnimation);
		btnAddAnimation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Adding animation to current object

				BufferedImage currentSprite = window.getApp().getCurrentSprite();

				if (currentSprite != null) {
					int sprites = Integer.parseInt(spinnerNumSprites.getModel().getValue().toString());
					int rows = Integer.parseInt(spinnerRows.getModel().getValue().toString());
					int cols = Integer.parseInt(spinnerCols.getModel().getValue().toString());
					int delay = Integer.parseInt(spinnerDelay.getModel().getValue().toString());
					int refresh = Integer.parseInt(spinnerTimeRefresh.getModel().getValue().toString());

					try {

						String id = JOptionPane.showInputDialog(window, "Put a name ID to this Animation",
								"Animation ID", JOptionPane.QUESTION_MESSAGE);

						if (id != null && !id.equals("")) {
							Animation animation = new Animation(currentSprite, sprites, delay, cols, rows,
									window.getApp().getCurrentObject(), refresh, id);

							window.getApp().getCurrentObject().setAnimation(animation);

							window.getApp().getCurrentObject().getAnimation().getThread().start();
						} else {

							JOptionPane.showMessageDialog(window,
									"It can't add an Animation without an ID. \n Try again please.",
									"Error No Animation ID", JOptionPane.ERROR_MESSAGE);

						}
					} catch (Exception ex) {

						JOptionPane.showMessageDialog(null, ex.getCause() + "\n" + ex.getLocalizedMessage()
								+ "\n There is a problem with the sprite sheet. Please check columns and rows and the quantity of sprites.",
								"FATAL ERROR", JOptionPane.ERROR_MESSAGE);

					}

				} else {

					JOptionPane.showMessageDialog(null, "First add a Sprite Sheet", "Error - No Sprite sheet found",
							JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		panelOptions = new JPanel();
		sl_aux.putConstraint(SpringLayout.NORTH, panelOptions, 0, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.WEST, panelOptions, 1, SpringLayout.WEST, this);
		sl_aux.putConstraint(SpringLayout.SOUTH, panelOptions, 176, SpringLayout.NORTH, this);
		sl_aux.putConstraint(SpringLayout.EAST, panelOptions, 309, SpringLayout.WEST, this);
		aux.add(panelOptions);
		panelOptions.setLayout(null);

		lblOptions = new JLabel("Options");
		lblOptions.setBounds(95, 4, 121, 37);
		panelOptions.add(lblOptions);
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setFont(new Font("Lato", Font.BOLD, 25));

		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Open Settings
				JDialog settings = new JDialog(window, "Settings");

				settings.getContentPane().setLayout(new GridLayout(1, 3));
				JLabel lblTheme = new JLabel("Theme");
				JButton btnDefault = new JButton("Default");
				btnDefault.addActionListener(a -> {

					setDefaultTheme();

				});
				JButton btnDark = new JButton("Dark");
				btnDark.addActionListener(a -> {

					setDarkMode();

				});
				settings.getContentPane().add(lblTheme);
				settings.getContentPane().add(btnDefault);
				settings.getContentPane().add(btnDark);
				settings.pack();
				settings.setLocationRelativeTo(null);
				settings.setVisible(true);

			}
		});
		btnSettings.setBounds(60, 121, 190, 31);
		panelOptions.add(btnSettings);

		btnCreateObject = new JButton("Create a new Object");
		btnCreateObject.setToolTipText("Create an object and add it to the canvas");
		btnCreateObject.setBounds(60, 50, 190, 31);
		panelOptions.add(btnCreateObject);

		btnMyObjects = new JButton("My Objects");
		btnMyObjects.setToolTipText("Shows the list of all the objects created");
		btnMyObjects.setBounds(60, 85, 190, 31);
		panelOptions.add(btnMyObjects);

		JSeparator separator_1_1_2 = new JSeparator();
		separator_1_1_2.setBounds(-18, 164, 326, 2);
		panelOptions.add(separator_1_1_2);

		btnFullScreen = new JButton("");
		btnFullScreen.setToolTipText("Exit FullScreen Mode");
		btnFullScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (window.isFullScreen()) {

					window.dispose();
					window.setUndecorated(false);
					window.setFullScreen(false);
					window.setVisible(true);

					btnFullScreen.setToolTipText("Open FullScreen Mode");

				} else {

					window.dispose();
					window.setUndecorated(true);
					window.setFullScreen(true);
					window.setVisible(true);
					btnFullScreen.setToolTipText("Exit FullScreen Mode");

				}

			}
		});
		btnFullScreen.setBounds(253, 4, 49, 37);
		BufferedImage fullScreenImage = ImageLoader.cargarSprites("images/fullScreen.png");
		Image iconFullScreen = fullScreenImage.getScaledInstance(btnFullScreen.getWidth() - 10,
				btnFullScreen.getHeight() - 10, Image.SCALE_SMOOTH);
		btnFullScreen.setIcon(new ImageIcon(iconFullScreen));

		panelOptions.add(btnFullScreen);
		btnMyObjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnCreateObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Creating and adding an object

				String name = JOptionPane.showInputDialog(null, "Please enter a name to this Object",
						"Adding an Object", JOptionPane.QUESTION_MESSAGE);

				try {
					if (name != null && !name.equalsIgnoreCase("")) {
						window.getApp().addObject(name);
						updateValues();
					} else {
						JOptionPane.showMessageDialog(null, "Please enter a name or the object it won't be added",
								"Error", JOptionPane.ERROR_MESSAGE);
					}

				} catch (Exception e) {

					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		add(aux);

		panelCanvas = new JPanel();
		sl_aux.putConstraint(SpringLayout.NORTH, panelCanvas, 3, SpringLayout.SOUTH, panelAnimation);
		sl_aux.putConstraint(SpringLayout.WEST, panelCanvas, 1, SpringLayout.WEST, aux);
		sl_aux.putConstraint(SpringLayout.SOUTH, panelCanvas, 162, SpringLayout.SOUTH, panelAnimation);
		sl_aux.putConstraint(SpringLayout.EAST, panelCanvas, 0, SpringLayout.EAST, panelSpriteSheet);
		aux.add(panelCanvas);
		SpringLayout sl_panelCanvas = new SpringLayout();
		panelCanvas.setLayout(sl_panelCanvas);

		lblCanvas = new JLabel("Canvas");
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, lblCanvas, 10, SpringLayout.NORTH, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, lblCanvas, -215, SpringLayout.EAST, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, lblCanvas, -84, SpringLayout.EAST, panelCanvas);
		lblCanvas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCanvas.setFont(new Font("Lato", Font.BOLD, 25));
		panelCanvas.add(lblCanvas);

		btnCanvasColor = new JButton("Select a Color");
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, btnCanvasColor, 7, SpringLayout.SOUTH, lblCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, btnCanvasColor, 157, SpringLayout.WEST, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.SOUTH, btnCanvasColor, -83, SpringLayout.SOUTH, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, btnCanvasColor, -10, SpringLayout.EAST, panelCanvas);
		btnCanvasColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JColorChooser colorChooser = new JColorChooser();
				JButton btnChooseColor = new JButton("Pick this Color");

				JDialog dialogColor = new JDialog(window, "Canvas Color");
				dialogColor.getContentPane().setLayout(new BorderLayout());

				dialogColor.getContentPane().add(colorChooser, BorderLayout.CENTER);
				dialogColor.getContentPane().add(btnChooseColor, BorderLayout.SOUTH);

				dialogColor.pack();
				dialogColor.setLocationRelativeTo(null);
				dialogColor.setVisible(true);

				colorChooser.isVisible();

				btnChooseColor.addActionListener(a -> {

					// Setting canvas color

					window.getCanvas().setBackground(colorChooser.getColor());
					btnCanvasColor.setBackground(colorChooser.getColor());

					dialogColor.dispose();

				});

			}
		});
		btnCanvasColor.setBackground(new Color(255, 255, 255));
		panelCanvas.add(btnCanvasColor);

		btnBackgroundImage = new JButton("Add an Image");
		btnBackgroundImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Adding background image to canvas

				JFileChooser chooser = new JFileChooser();
				chooser.showDialog(window, "Upload Background to Canvas");

				String pathImage = "";
				BufferedImage background = null;

				try {

					if (chooser.getSelectedFile() != null) {
						backgroundImageCanvas = chooser.getSelectedFile();
						pathImage = backgroundImageCanvas.getAbsolutePath();
						background = ImageIO.read(new File(pathImage));
						window.getCanvas().setBackgroundImage(background);

					}

				} catch (Exception ex) {

					ex.printStackTrace();

				}

			}
		});
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, btnBackgroundImage, 6, SpringLayout.SOUTH, btnCanvasColor);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, btnBackgroundImage, 157, SpringLayout.WEST, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, btnBackgroundImage, 0, SpringLayout.EAST, btnCanvasColor);
		panelCanvas.add(btnBackgroundImage);

		lblBackgroundColor = new JLabel("Background Color");
		lblBackgroundColor.setFont(new Font("Lato", Font.BOLD, 13));
		lblBackgroundColor.setHorizontalAlignment(SwingConstants.CENTER);
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, lblBackgroundColor, 0, SpringLayout.NORTH, btnCanvasColor);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, lblBackgroundColor, 10, SpringLayout.WEST, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.SOUTH, lblBackgroundColor, 0, SpringLayout.SOUTH, btnCanvasColor);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, lblBackgroundColor, -26, SpringLayout.WEST, btnCanvasColor);
		panelCanvas.add(lblBackgroundColor);

		lblBackgroundImage = new JLabel("Background Image");
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, lblBackgroundImage, 6, SpringLayout.NORTH, btnBackgroundImage);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, lblBackgroundImage, 0, SpringLayout.WEST, lblBackgroundColor);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, lblBackgroundImage, 0, SpringLayout.EAST, lblBackgroundColor);
		lblBackgroundImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackgroundImage.setFont(new Font("Lato", Font.BOLD, 13));
		panelCanvas.add(lblBackgroundImage);

	}

	public void setDarkMode() {

		aux.setBackground(new Color(75, 75, 75));

		for (int i = 0; i < aux.getComponentCount(); i++) {

			JPanel temp = (JPanel) aux.getComponent(i);

			temp.setBackground(new Color(75, 75, 75));

			for (int j = 0; j < temp.getComponents().length; j++) {

				if (temp.getComponent(j) instanceof JLabel) {

					temp.getComponent(j).setForeground(Color.WHITE);

				}

				if (temp.getComponent(j) instanceof JButton) {

					temp.getComponent(j).setBackground(Color.BLACK);
					temp.getComponent(j).setForeground(Color.WHITE);

				}

			}

		}

		BufferedImage fsWhite = ImageLoader.cargarSprites("images/fullScreenWhite.png");
		Image fsIconWhite = fsWhite.getScaledInstance(btnFullScreen.getWidth() - 10, btnFullScreen.getHeight() - 10,
				Image.SCALE_SMOOTH);
		btnFullScreen.setIcon(new ImageIcon(fsIconWhite));

		BufferedImage playWhite = ImageLoader.cargarSprites("images/playpauseWhite.png");
		Image playIconWhite = playWhite.getScaledInstance(lblPlayPause.getWidth(), lblPlayPause.getHeight(),
				Image.SCALE_SMOOTH);
		lblPlayPause.setIcon(new ImageIcon(playIconWhite));

	}

	public void setDefaultTheme() {

		aux.setBackground(null);

		for (int i = 0; i < aux.getComponentCount(); i++) {

			JPanel temp = (JPanel) aux.getComponent(i);

			temp.setBackground(null);

			for (int j = 0; j < temp.getComponents().length; j++) {

				if (temp.getComponent(j) instanceof JLabel) {

					temp.getComponent(j).setForeground(Color.BLACK);

				}

				if (temp.getComponent(j) instanceof JButton) {

					temp.getComponent(j).setBackground(null);
					temp.getComponent(j).setForeground(Color.BLACK);

				}

			}

		}

		BufferedImage fs = ImageLoader.cargarSprites("images/fullScreen.png");
		Image fsIcon = fs.getScaledInstance(btnFullScreen.getWidth() - 10, btnFullScreen.getHeight() - 10,
				Image.SCALE_SMOOTH);
		btnFullScreen.setIcon(new ImageIcon(fsIcon));

		BufferedImage play = ImageLoader.cargarSprites("images/playpause.png");
		Image playIcon = play.getScaledInstance(lblPlayPause.getWidth(), lblPlayPause.getHeight(), Image.SCALE_SMOOTH);
		lblPlayPause.setIcon(new ImageIcon(playIcon));

	}

	public JLabel getLabSpriteTextFile() {
		return labNoSprites;
	}

	public JSpinner getSpinnerRows() {
		return spinnerRows;
	}

	public JSpinner getSpinnerCols() {
		return spinnerCols;
	}

	public JSpinner getSpinnerNumSprites() {
		return spinnerNumSprites;
	}

	public JSpinner getSpinnerDelay() {
		return spinnerDelay;
	}

	public JSpinner getSpinnerPosX() {
		return spinnerPosX;
	}

	public JSpinner getSpinnerPosY() {
		return spinnerPosY;
	}

	public JSpinner getSpinnerSpeedX() {
		return spinnerSpeedX;
	}

	public JSpinner getSpinnerSpeedY() {
		return spinnerSpeedY;
	}

	public JSpinner getSpinnerTimeRefresh() {
		return spinnerTimeRefresh;
	}

	public JSpinner getSpinnerWidth() {
		return spinnerWidth;
	}

	public JSpinner getSpinnerHeight() {
		return spinnerHeight;
	}

	public File getfileSelectedSprite() {
		return fileSelectedSprite;
	}

	public void setfileSelectedSprite(File fileSelectedSprite) {
		this.fileSelectedSprite = fileSelectedSprite;
	}

	public void updateValues() {

		Object currentObject = window.getApp().getCurrentObject();

		lblCurrentObject.setText("Current Object: " + currentObject.getNameID());

		spinnerPosX.getModel().setValue(currentObject.getX());
		spinnerPosY.getModel().setValue(currentObject.getY());
		spinnerWidth.getModel().setValue(currentObject.getWidth());
		spinnerHeight.getModel().setValue(currentObject.getHeight());
		spinnerRefreshMove.getModel().setValue(currentObject.getRefreshMove());
		spinnerSpeedX.getModel().setValue(currentObject.getSpeedX());
		spinnerSpeedY.getModel().setValue(currentObject.getSpeedY());

		if (currentObject.getAnimation() != null) {
			spinnerTimeRefresh.getModel().setValue(currentObject.getAnimation().getTimeRefresh());
		} else {
			spinnerTimeRefresh.getModel().setValue(1);
		}
	}

	public JSpinner getSpinnerRefreshMove() {
		return spinnerRefreshMove;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		window.setCursor(new Cursor(Cursor.MOVE_CURSOR));

		if (origin != null) {

			int deltaY = origin.y - e.getY();

			setLocation(getX(), getY() - deltaY);

		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		origin = new Point(e.getPoint());

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	public JButton getBtnCreateObject() {
		return btnCreateObject;
	}

	public JLabel getLblOptions() {
		return lblOptions;
	}

	public JButton getBtnAddAnimation() {
		return btnAddAnimation;
	}

	public JButton getBtnFullScreen() {
		return btnFullScreen;
	}

	public JButton getBtnAddSprite() {
		return btnAddSprite;
	}

	public JButton getBtnMyObjects() {
		return btnMyObjects;
	}

	public JButton getBtnBackgroundImage() {
		return btnBackgroundImage;
	}

	public JButton getBtnUploadSkin() {
		return btnUploadSkin;
	}

	public JButton getBtnSettings() {
		return btnSettings;
	}

	public JButton getBtnCanvasColor() {
		return btnCanvasColor;
	}

	public JLabel getLblPosX() {
		return lblPosX;
	}

	public JLabel getLblDelay() {
		return lblDelay;
	}

	public JLabel getLblBackgroundImage() {
		return lblBackgroundImage;
	}

	public JLabel getLblNumSprites() {
		return lblNumSprites;
	}

	public JLabel getLblAnimation() {
		return lblAnimation;
	}

	public JLabel getLblSpeedX() {
		return lblSpeedX;
	}

	public JLabel getLblRefreshMove() {
		return lblRefreshMove;
	}

	public JLabel getLblCanvas() {
		return lblCanvas;
	}

	public JLabel getLblPosY() {
		return lblPosY;
	}

	public JLabel getLblNoSkinFound() {
		return lblCurrentObject;
	}

	public JLabel getLblPlayPause() {
		return lblPlayPause;
	}

	public JLabel getLblHeight() {
		return lblHeight;
	}

	public JLabel getLblCols() {
		return lblCols;
	}

	public JLabel getLblWidth() {
		return lblWidth;
	}

	public JLabel getLblRows() {
		return lblRows;
	}

	public JLabel getLblObject() {
		return lblObject;
	}

	public JLabel getLblTimeRefresh() {
		return lblTimeRefresh;
	}

	public JLabel getLblBackgroundColor() {
		return lblBackgroundColor;
	}

	public JLabel getLblSprite() {
		return lblSprite;
	}

	public JLabel getLblSpeedY() {
		return lblSpeedY;
	}

	public JPanel getPanelOptions() {
		return panelOptions;
	}

	public JPanel getPanelAnimation() {
		return panelAnimation;
	}

	public JPanel getPanelCanvas() {
		return panelCanvas;
	}

	public JPanel getPanelSpriteSheet() {
		return panelSpriteSheet;
	}

	public JPanel getPanelObject() {
		return panelObject;
	}

	public JPanel getAux() {
		return aux;
	}
}
