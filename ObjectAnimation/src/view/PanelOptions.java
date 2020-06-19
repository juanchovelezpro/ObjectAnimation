package view;

import java.awt.Color;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
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
	private File fileSelected;
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

		JPanel aux = new JPanel();

		SpringLayout springLayout = new SpringLayout();
		aux.setLayout(springLayout);

		JPanel panelSpriteSheet = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelSpriteSheet, 422, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panelSpriteSheet, 1, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panelSpriteSheet, 592, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelSpriteSheet, 307, SpringLayout.WEST, this);
		aux.add(panelSpriteSheet);
		panelSpriteSheet.setLayout(null);

		JLabel lblSprite = new JLabel("Sprite Sheet");
		lblSprite.setBounds(10, 0, 300, 45);
		panelSpriteSheet.add(lblSprite);
		lblSprite.setFont(new Font("Lato", Font.BOLD, 25));
		lblSprite.setHorizontalAlignment(SwingConstants.CENTER);

		labNoSprites = new JLabel("No sprites found!");
		labNoSprites.setBounds(10, 44, 300, 15);
		panelSpriteSheet.add(labNoSprites);
		labNoSprites.setHorizontalAlignment(SwingConstants.CENTER);
		labNoSprites.setFont(new Font("Lato", Font.BOLD, 12));

		JButton btnAddSprite = new JButton("Add Sprite Sheet");
		btnAddSprite.setBounds(39, 61, 239, 31);
		panelSpriteSheet.add(btnAddSprite);

		JLabel lblRows = new JLabel("# Rows");
		lblRows.setHorizontalAlignment(SwingConstants.CENTER);
		lblRows.setBounds(10, 101, 76, 26);
		panelSpriteSheet.add(lblRows);
		lblRows.setFont(new Font("Lato", Font.BOLD, 15));

		JLabel lblCols = new JLabel("# Cols");
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

		JLabel lblNumSprites = new JLabel("# Sprites");
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
			chooser.showDialog(null, "Upload Sprites");

			String pathImage = "";
			BufferedImage sprite = null;

			try {

				if (chooser.getSelectedFile() != null) {
					fileSelected = chooser.getSelectedFile();
					pathImage = fileSelected.getAbsolutePath();
					sprite = ImageIO.read(new File(pathImage));
					window.getApp().setCurrentSprite(sprite);
					labNoSprites.setText(fileSelected.getName());
					JOptionPane.showMessageDialog(null, "The Sprite has been uploaded successfully", "Sprite Uploaded",
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon(sprite));

				}

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		});

		JPanel panelObject = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelObject, 167, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panelObject, 1, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panelObject, 422, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelObject, 306, SpringLayout.WEST, this);
		aux.add(panelObject);
		panelObject.setLayout(null);

		JLabel lblObject = new JLabel("Object");
		lblObject.setBounds(1, 6, 306, 45);
		panelObject.add(lblObject);
		lblObject.setHorizontalAlignment(SwingConstants.CENTER);
		lblObject.setFont(new Font("Lato", Font.BOLD, 25));

		JLabel lblPosX = new JLabel("x");
		lblPosX.setBounds(14, 139, 61, 26);
		panelObject.add(lblPosX);
		lblPosX.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosX.setFont(new Font("Lato", Font.BOLD, 15));

		JLabel lblPosY = new JLabel("y");
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

		JLabel lblSpeedX = new JLabel("Speed X");
		lblSpeedX.setBounds(12, 173, 63, 31);
		panelObject.add(lblSpeedX);
		lblSpeedX.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerSpeedX = new JSpinner();
		spinnerSpeedX.setBounds(72, 175, 76, 26);
		panelObject.add(spinnerSpeedX);

		JLabel lblSpeedY = new JLabel("Speed Y");
		lblSpeedY.setBounds(151, 175, 67, 26);
		panelObject.add(lblSpeedY);
		lblSpeedY.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerSpeedY = new JSpinner();
		spinnerSpeedY.setBounds(211, 175, 76, 26);
		panelObject.add(spinnerSpeedY);

		JLabel lblRefreshMove = new JLabel("Refresh Move (ms)");
		lblRefreshMove.setBounds(6, 210, 117, 31);
		panelObject.add(lblRefreshMove);
		lblRefreshMove.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefreshMove.setFont(new Font("Lato", Font.BOLD, 12));

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

		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(12, 108, 61, 26);
		panelObject.add(lblWidth);
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		lblWidth.setFont(new Font("Lato", Font.BOLD, 15));

		JLabel lblHeight = new JLabel("Height");
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

		JLabel lblNoSkinFound = new JLabel("No skin found!");
		lblNoSkinFound.setBounds(6, 48, 300, 15);
		panelObject.add(lblNoSkinFound);
		lblNoSkinFound.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoSkinFound.setFont(new Font("Lato", Font.BOLD, 12));

		JButton btnUploadSkin = new JButton("Add Skin");
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

		JPanel panelAnimation = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelAnimation, 587, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panelAnimation, 1, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panelAnimation, 746, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelAnimation, 307, SpringLayout.WEST, this);
		aux.add(panelAnimation);
		panelAnimation.setLayout(null);

		JLabel lblAnimation = new JLabel("Animation");
		lblAnimation.setBounds(93, 6, 133, 43);
		panelAnimation.add(lblAnimation);
		lblAnimation.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnimation.setFont(new Font("Lato", Font.BOLD, 25));

		JLabel lblTimeRefresh = new JLabel("Time Refresh (ms)");
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

		JLabel lblPlayPause = new JLabel("");
		lblPlayPause.setBounds(239, 9, 40, 40);
		panelAnimation.add(lblPlayPause);
		lblPlayPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				// Resume and pause current object's animation

			}
		});
		BufferedImage iconPlay = ImageLoader.cargarSprites("images/playpause.png");
		Image myIconPlay = iconPlay.getScaledInstance(lblPlayPause.getWidth(), lblPlayPause.getHeight(),
				Image.SCALE_SMOOTH);
		lblPlayPause.setIcon(new ImageIcon(myIconPlay));

		JLabel lblDelay = new JLabel("Delay");
		lblDelay.setBounds(6, 80, 122, 28);
		panelAnimation.add(lblDelay);
		lblDelay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDelay.setFont(new Font("Lato", Font.BOLD, 15));

		spinnerDelay = new JSpinner();
		spinnerDelay.setBounds(130, 81, 174, 26);
		panelAnimation.add(spinnerDelay);
		spinnerDelay.setModel(new SpinnerNumberModel(0, 0, 1000, 1));

		JButton btnAddAnimation = new JButton("Add Animation to Object");
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
						Animation animation = new Animation(currentSprite, sprites, delay, cols, rows,
								window.getApp().getCurrentObject(), refresh);

						window.getApp().getCurrentObject().setAnimation(animation);

						window.getApp().getCurrentObject().getAnimation().getThread().start();

//						spinnerTimeRefresh.getModel().setValue(window.getApp().getCurrentObject().getAnimation().getTimeRefresh());

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

		JPanel panelOptions = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelOptions, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panelOptions, 1, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panelOptions, 176, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panelOptions, 309, SpringLayout.WEST, this);
		aux.add(panelOptions);
		panelOptions.setLayout(null);

		JLabel lblOptions = new JLabel("Options");
		lblOptions.setBounds(95, 4, 121, 37);
		panelOptions.add(lblOptions);
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setFont(new Font("Lato", Font.BOLD, 25));

		JButton btnSettings = new JButton("Settings");
		btnSettings.setBounds(60, 121, 190, 31);
		panelOptions.add(btnSettings);

		JButton btnCreateObject = new JButton("Create a new Object");
		btnCreateObject.setToolTipText("Create an object and add it to the canvas");
		btnCreateObject.setBounds(60, 50, 190, 31);
		panelOptions.add(btnCreateObject);

		JButton btnMyObjects = new JButton("My Objects");
		btnMyObjects.setToolTipText("Shows the list of all the objects created");
		btnMyObjects.setBounds(60, 85, 190, 31);
		panelOptions.add(btnMyObjects);

		JSeparator separator_1_1_2 = new JSeparator();
		separator_1_1_2.setBounds(-18, 164, 326, 2);
		panelOptions.add(separator_1_1_2);

		JButton btnFullScreen = new JButton("");
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
						defaultValuesSpinners();
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
		
		JPanel panelCanvas = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelCanvas, 3, SpringLayout.SOUTH, panelAnimation);
		springLayout.putConstraint(SpringLayout.WEST, panelCanvas, 1, SpringLayout.WEST, aux);
		springLayout.putConstraint(SpringLayout.SOUTH, panelCanvas, 162, SpringLayout.SOUTH, panelAnimation);
		springLayout.putConstraint(SpringLayout.EAST, panelCanvas, 0, SpringLayout.EAST, panelSpriteSheet);
		aux.add(panelCanvas);
		SpringLayout sl_panelCanvas = new SpringLayout();
		panelCanvas.setLayout(sl_panelCanvas);
		
		JLabel lblCanvas = new JLabel("Canvas");
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, lblCanvas, 10, SpringLayout.NORTH, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, lblCanvas, -215, SpringLayout.EAST, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, lblCanvas, -84, SpringLayout.EAST, panelCanvas);
		lblCanvas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCanvas.setFont(new Font("Lato", Font.BOLD, 25));
		panelCanvas.add(lblCanvas);
		
		JButton btnCanvasColor = new JButton("Background Color");
		btnCanvasColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JColorChooser colorChooser = new JColorChooser();
				
				colorChooser.isVisible();
				
			}
		});
		btnCanvasColor.setBackground(new Color(255, 255, 255));
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, btnCanvasColor, 7, SpringLayout.SOUTH, lblCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, btnCanvasColor, 53, SpringLayout.WEST, panelCanvas);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, btnCanvasColor, -44, SpringLayout.EAST, panelCanvas);
		panelCanvas.add(btnCanvasColor);
		
		JButton btnBackgroundImage = new JButton("Background Image");
		sl_panelCanvas.putConstraint(SpringLayout.NORTH, btnBackgroundImage, 6, SpringLayout.SOUTH, btnCanvasColor);
		sl_panelCanvas.putConstraint(SpringLayout.WEST, btnBackgroundImage, 0, SpringLayout.WEST, btnCanvasColor);
		sl_panelCanvas.putConstraint(SpringLayout.EAST, btnBackgroundImage, 0, SpringLayout.EAST, btnCanvasColor);
		panelCanvas.add(btnBackgroundImage);

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

	public File getFileSelected() {
		return fileSelected;
	}

	public void setFileSelected(File fileSelected) {
		this.fileSelected = fileSelected;
	}

	public void defaultValuesSpinners() {

		Object currentObject = window.getApp().getCurrentObject();

		spinnerPosX.getModel().setValue(currentObject.getX());
		spinnerPosY.getModel().setValue(currentObject.getY());
		spinnerWidth.getModel().setValue(currentObject.getWidth());
		spinnerHeight.getModel().setValue(currentObject.getHeight());
		spinnerRefreshMove.getModel().setValue(currentObject.getRefreshMove());
		spinnerSpeedX.getModel().setValue(currentObject.getSpeedX());
		spinnerSpeedY.getModel().setValue(currentObject.getSpeedY());

		if (currentObject.getAnimation() != null)
			spinnerTimeRefresh.getModel().setValue(currentObject.getAnimation().getTimeRefresh());
		else
			spinnerTimeRefresh.getModel().setValue(1);

	}

	public void updateValues() {

		Object currentObject = window.getApp().getCurrentObject();

		spinnerPosX.getModel().setValue(currentObject.getX());
		spinnerPosY.getModel().setValue(currentObject.getY());
		spinnerWidth.getModel().setValue(currentObject.getWidth());
		spinnerHeight.getModel().setValue(currentObject.getHeight());
		spinnerRefreshMove.getModel().setValue(currentObject.getRefreshMove());
		spinnerSpeedX.getModel().setValue(currentObject.getSpeedX());
		spinnerSpeedY.getModel().setValue(currentObject.getSpeedY());

		if (currentObject.getAnimation() != null)
			spinnerTimeRefresh.getModel().setValue(currentObject.getAnimation().getTimeRefresh());
		else
			spinnerTimeRefresh.getModel().setValue(1);

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
}
