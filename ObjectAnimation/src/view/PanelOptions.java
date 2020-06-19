package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import animation.Animation;
import model.Object;

public class PanelOptions extends JPanel {

	public static final int WIDTH = 300;
	public static final int HEIGHT = MainWindow.HEIGHT;

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

	public PanelOptions(MainWindow window) {

		this.window = window;

		setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocation(MainWindow.WIDTH - WIDTH, 0);

		settingComponents();

	}

	public void settingComponents() {

		JLabel lblSprite = new JLabel("Sprite Sheet");
		lblSprite.setFont(new Font("Lato", Font.BOLD, 25));
		lblSprite.setHorizontalAlignment(SwingConstants.CENTER);
		lblSprite.setBounds(0, 449, 300, 45);
		add(lblSprite);

		labNoSprites = new JLabel("No sprites found!");
		labNoSprites.setHorizontalAlignment(SwingConstants.CENTER);
		labNoSprites.setFont(new Font("Lato", Font.BOLD, 12));
		labNoSprites.setBounds(0, 493, 300, 15);
		add(labNoSprites);

		JButton btnAddSprite = new JButton("Add Sprite Sheet");
		btnAddSprite.addActionListener(a -> {

			// Handler sprites.
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
		btnAddSprite.setBounds(29, 510, 239, 31);
		add(btnAddSprite);

		JLabel lblRows = new JLabel("# Rows");
		lblRows.setFont(new Font("Lato", Font.BOLD, 15));
		lblRows.setBounds(10, 550, 61, 26);
		add(lblRows);

		JLabel lblCols = new JLabel("# Cols");
		lblCols.setFont(new Font("Lato", Font.BOLD, 15));
		lblCols.setBounds(156, 549, 55, 28);
		add(lblCols);

		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 9999, 1);

		spinnerRows = new JSpinner(model);
		spinnerRows.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int rows = Integer.parseInt(spinnerRows.getModel().getValue().toString());
				int cols = Integer.parseInt(spinnerCols.getModel().getValue().toString());

				spinnerNumSprites.setModel(new SpinnerNumberModel(rows * cols, 1, rows * cols, 1));
			}
		});
		spinnerRows.setBounds(68, 550, 76, 26);
		add(spinnerRows);

		SpinnerNumberModel model2 = new SpinnerNumberModel(1, 1, 9999, 1);
		spinnerCols = new JSpinner(model2);
		spinnerCols.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				int rows = Integer.parseInt(spinnerRows.getModel().getValue().toString());
				int cols = Integer.parseInt(spinnerCols.getModel().getValue().toString());

				spinnerNumSprites.setModel(new SpinnerNumberModel(rows * cols, 1, rows * cols, 1));

			}
		});
		spinnerCols.setBounds(209, 550, 76, 26);
		add(spinnerCols);

		JLabel lblNumSprites = new JLabel("# Sprites");
		lblNumSprites.setFont(new Font("Lato", Font.BOLD, 15));
		lblNumSprites.setBounds(8, 584, 63, 31);
		add(lblNumSprites);

		spinnerNumSprites = new JSpinner();
		spinnerNumSprites.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerNumSprites.setBounds(68, 586, 76, 26);
		add(spinnerNumSprites);

		JLabel lblDelay = new JLabel("Delay");
		lblDelay.setFont(new Font("Lato", Font.BOLD, 15));
		lblDelay.setBounds(156, 585, 55, 28);
		add(lblDelay);

		spinnerDelay = new JSpinner();
		spinnerDelay.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
		spinnerDelay.setBounds(209, 586, 76, 26);
		add(spinnerDelay);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 627, 300, 2);
		add(separator);

		JLabel lblObject = new JLabel("Object");
		lblObject.setHorizontalAlignment(SwingConstants.CENTER);
		lblObject.setFont(new Font("Lato", Font.BOLD, 25));
		lblObject.setBounds(0, 202, 300, 45);
		add(lblObject);

		JLabel lblPosX = new JLabel("x");
		lblPosX.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosX.setFont(new Font("Lato", Font.BOLD, 15));
		lblPosX.setBounds(8, 335, 61, 26);
		add(lblPosX);

		JLabel lblPosY = new JLabel("y");
		lblPosY.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosY.setFont(new Font("Lato", Font.BOLD, 15));
		lblPosY.setBounds(154, 334, 55, 28);
		add(lblPosY);

		spinnerPosX = new JSpinner();
		spinnerPosX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject().setX(Integer.parseInt(spinnerPosX.getModel().getValue().toString()));

			}
		});
		spinnerPosX.setBounds(66, 335, 76, 26);
		spinnerPosX.getModel().setValue(window.getApp().getObject().getX());
		add(spinnerPosX);

		spinnerPosY = new JSpinner();
		spinnerPosY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject().setY(Integer.parseInt(spinnerPosY.getModel().getValue().toString()));
			}
		});
		spinnerPosY.setBounds(207, 335, 76, 26);
		spinnerPosY.getModel().setValue(window.getApp().getObject().getY());
		add(spinnerPosY);

		JLabel lblSpeedX = new JLabel("Speed X");
		lblSpeedX.setFont(new Font("Lato", Font.BOLD, 15));
		lblSpeedX.setBounds(6, 369, 63, 31);
		add(lblSpeedX);

		spinnerSpeedX = new JSpinner();
		spinnerSpeedX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setSpeedX(Integer.parseInt(spinnerSpeedX.getModel().getValue().toString()));

			}
		});
		spinnerSpeedX.setBounds(66, 371, 76, 26);
		add(spinnerSpeedX);

		JLabel lblSpeedY = new JLabel("Speed Y");
		lblSpeedY.setFont(new Font("Lato", Font.BOLD, 15));
		lblSpeedY.setBounds(145, 371, 67, 26);
		add(lblSpeedY);

		spinnerSpeedY = new JSpinner();
		spinnerSpeedY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setSpeedY(Integer.parseInt(spinnerSpeedY.getModel().getValue().toString()));

			}
		});
		spinnerSpeedY.setBounds(205, 371, 76, 26);
		add(spinnerSpeedY);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 449, 300, 2);
		add(separator_1);

		JLabel lblAnimation = new JLabel("Animation");
		lblAnimation.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnimation.setFont(new Font("Lato", Font.BOLD, 25));
		lblAnimation.setBounds(0, 627, 300, 45);
		add(lblAnimation);

		JLabel lblTimeRefresh = new JLabel("Time Refresh (ms)");
		lblTimeRefresh.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeRefresh.setFont(new Font("Lato", Font.BOLD, 12));
		lblTimeRefresh.setBounds(5, 667, 117, 31);
		add(lblTimeRefresh);

		spinnerTimeRefresh = new JSpinner();
		spinnerTimeRefresh.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

			}
		});
		spinnerTimeRefresh.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerTimeRefresh.setBounds(112, 669, 174, 26);
		add(spinnerTimeRefresh);

		JButton btnPause = new JButton("Pause");
		btnPause.setBounds(168, 707, 117, 28);
		add(btnPause);

		JButton btnResume = new JButton("Resume");
		btnResume.setBounds(25, 707, 117, 28);
		add(btnResume);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(0, 0, 300, 2);
		add(separator_1_1);

		JLabel lblOptions = new JLabel("Options");
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setFont(new Font("Lato", Font.BOLD, 25));
		lblOptions.setBounds(0, 0, 300, 45);
		add(lblOptions);

		JButton btnAddAnimation = new JButton("Add Animation to Object");
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
		btnAddAnimation.setBounds(52, 87, 190, 31);
		add(btnAddAnimation);

		JButton btnMyAnimations = new JButton("My Animations");
		btnMyAnimations.setBounds(52, 159, 190, 31);
		add(btnMyAnimations);

		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(0, 202, 300, 2);
		add(separator_1_1_1);

		JLabel lblRefreshMove = new JLabel("Refresh Move (ms)");
		lblRefreshMove.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefreshMove.setFont(new Font("Lato", Font.BOLD, 12));
		lblRefreshMove.setBounds(0, 406, 117, 31);
		add(lblRefreshMove);

		spinnerRefreshMove = new JSpinner();
		spinnerRefreshMove.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setRefreshMove(Integer.parseInt(spinnerRefreshMove.getModel().getValue().toString()));

			}
		});
		spinnerRefreshMove.setModel(new SpinnerNumberModel(new Integer(10), new Integer(1), null, new Integer(1)));
		spinnerRefreshMove.setBounds(110, 409, 174, 26);
		add(spinnerRefreshMove);

		JLabel lblWidth = new JLabel("Width");
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		lblWidth.setFont(new Font("Lato", Font.BOLD, 15));
		lblWidth.setBounds(6, 304, 61, 26);
		add(lblWidth);

		JLabel lblHeight = new JLabel("Height");
		lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeight.setFont(new Font("Lato", Font.BOLD, 15));
		lblHeight.setBounds(140, 306, 67, 22);
		add(lblHeight);

		spinnerWidth = new JSpinner();
		spinnerWidth.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				window.getApp().getCurrentObject()
						.setWidth(Integer.parseInt(spinnerWidth.getModel().getValue().toString()));
				;
			}
		});
		spinnerWidth.setModel(new SpinnerNumberModel(new Integer(100), new Integer(10), null, new Integer(1)));
		spinnerWidth.setBounds(64, 304, 76, 26);
		add(spinnerWidth);

		spinnerHeight = new JSpinner();
		spinnerHeight.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				window.getApp().getCurrentObject()
						.setHeight(Integer.parseInt(spinnerHeight.getModel().getValue().toString()));
				;

			}
		});
		spinnerHeight.setModel(new SpinnerNumberModel(new Integer(100), new Integer(10), null, new Integer(1)));
		spinnerHeight.setBounds(205, 304, 76, 26);
		add(spinnerHeight);

		JButton btnCreateObject = new JButton("Create a new Object");
		btnCreateObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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
		btnCreateObject.setBounds(52, 50, 190, 31);
		add(btnCreateObject);

		JButton btnSaveAnimations = new JButton("Save Animations Into Object");
		btnSaveAnimations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnSaveAnimations.setBounds(52, 124, 190, 31);
		add(btnSaveAnimations);

		JLabel lblNoSkinFound = new JLabel("No skin found!");
		lblNoSkinFound.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoSkinFound.setFont(new Font("Lato", Font.BOLD, 12));
		lblNoSkinFound.setBounds(0, 244, 300, 15);
		add(lblNoSkinFound);

		JButton btnUploadSkin = new JButton("Add Skin");
		btnUploadSkin.setBounds(29, 263, 239, 31);
		add(btnUploadSkin);

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
}
