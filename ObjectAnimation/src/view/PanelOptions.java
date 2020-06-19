package view;

import java.awt.Color;
import java.awt.GridLayout;
import model.Object;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;

import animation.Animation;

import javax.swing.event.ChangeEvent;

public class PanelOptions extends JPanel {

	public static final int WIDTH = 300;
	public static final int HEIGHT = MainWindow.HEIGHT;

	private MainWindow window;
	private File fileSelected;
	private JLabel labSpriteTextFile;
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
		setBorder(new LineBorder(Color.DARK_GRAY, 1, true));

		this.window = window;
		setSize(WIDTH, HEIGHT);
		setLocation(MainWindow.WIDTH - WIDTH, 0);

		setLayout(null);

		JLabel lblNewLabel = new JLabel("Sprite Sheet");
		lblNewLabel.setFont(new Font("Lato", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 300, 45);
		add(lblNewLabel);

		labSpriteTextFile = new JLabel("No sprites found!");
		labSpriteTextFile.setHorizontalAlignment(SwingConstants.CENTER);
		labSpriteTextFile.setFont(new Font("Lato", Font.BOLD, 12));
		labSpriteTextFile.setBounds(0, 44, 300, 15);
		add(labSpriteTextFile);

		JButton btnNewButton = new JButton("Upload Sprite Sheet");
		btnNewButton.addActionListener(a -> {

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
					labSpriteTextFile.setText(fileSelected.getName());
					JOptionPane.showMessageDialog(null, "The Sprite has been uploaded successfully", "Sprite Uploaded",
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon(sprite));

				}

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		});
		btnNewButton.setBounds(29, 71, 239, 31);
		add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel("# Rows");
		lblNewLabel_2.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2.setBounds(6, 122, 61, 26);
		add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("# Cols");
		lblNewLabel_2_1.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(152, 121, 55, 28);
		add(lblNewLabel_2_1);

		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 9999, 1);

		spinnerRows = new JSpinner(model);
		spinnerRows.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int rows = Integer.parseInt(spinnerRows.getModel().getValue().toString());
				int cols = Integer.parseInt(spinnerCols.getModel().getValue().toString());

				spinnerNumSprites.setModel(new SpinnerNumberModel(rows * cols, 1, rows * cols, 1));
			}
		});
		spinnerRows.setBounds(64, 122, 76, 26);
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
		spinnerCols.setBounds(205, 122, 76, 26);
		add(spinnerCols);

		JLabel lblNewLabel_2_2 = new JLabel("# Sprites");
		lblNewLabel_2_2.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_2.setBounds(4, 156, 63, 31);
		add(lblNewLabel_2_2);

		spinnerNumSprites = new JSpinner();
		spinnerNumSprites.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerNumSprites.setBounds(64, 158, 76, 26);
		add(spinnerNumSprites);

		JLabel lblNewLabel_2_1_1 = new JLabel("Delay");
		lblNewLabel_2_1_1.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_1_1.setBounds(152, 157, 55, 28);
		add(lblNewLabel_2_1_1);

		spinnerDelay = new JSpinner();
		spinnerDelay.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
		spinnerDelay.setBounds(205, 158, 76, 26);
		add(spinnerDelay);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 212, 300, 2);
		add(separator);

		JLabel lblObject = new JLabel("Object");
		lblObject.setHorizontalAlignment(SwingConstants.CENTER);
		lblObject.setFont(new Font("Lato", Font.BOLD, 25));
		lblObject.setBounds(0, 209, 300, 45);
		add(lblObject);

		JLabel lblNewLabel_2_3 = new JLabel("POS X");
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_3.setBounds(8, 287, 61, 26);
		add(lblNewLabel_2_3);

		JLabel lblNewLabel_2_1_2 = new JLabel("POS Y");
		lblNewLabel_2_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_2.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_1_2.setBounds(154, 286, 55, 28);
		add(lblNewLabel_2_1_2);

		spinnerPosX = new JSpinner();
		spinnerPosX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject().setX(Integer.parseInt(spinnerPosX.getModel().getValue().toString()));

			}
		});
		spinnerPosX.setBounds(66, 287, 76, 26);
		spinnerPosX.getModel().setValue(window.getApp().getObject().getX());
		add(spinnerPosX);

		spinnerPosY = new JSpinner();
		spinnerPosY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject().setY(Integer.parseInt(spinnerPosY.getModel().getValue().toString()));
			}
		});
		spinnerPosY.setBounds(207, 287, 76, 26);
		spinnerPosY.getModel().setValue(window.getApp().getObject().getY());
		add(spinnerPosY);

		JLabel lblNewLabel_2_2_1 = new JLabel("SPEED X");
		lblNewLabel_2_2_1.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_2_1.setBounds(6, 321, 63, 31);
		add(lblNewLabel_2_2_1);

		spinnerSpeedX = new JSpinner();
		spinnerSpeedX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setSpeedX(Integer.parseInt(spinnerSpeedX.getModel().getValue().toString()));

			}
		});
		spinnerSpeedX.setBounds(66, 323, 76, 26);
		add(spinnerSpeedX);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("SPEED Y");
		lblNewLabel_2_1_1_1.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_1_1_1.setBounds(145, 323, 67, 26);
		add(lblNewLabel_2_1_1_1);

		spinnerSpeedY = new JSpinner();
		spinnerSpeedY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setSpeedY(Integer.parseInt(spinnerSpeedY.getModel().getValue().toString()));

			}
		});
		spinnerSpeedY.setBounds(205, 323, 76, 26);
		add(spinnerSpeedY);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 391, 300, 2);
		add(separator_1);

		JLabel lblProcess = new JLabel("Animation");
		lblProcess.setHorizontalAlignment(SwingConstants.CENTER);
		lblProcess.setFont(new Font("Lato", Font.BOLD, 25));
		lblProcess.setBounds(0, 391, 300, 45);
		add(lblProcess);

		JLabel lblNewLabel_2_3_1 = new JLabel("Time Refresh (ms)");
		lblNewLabel_2_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3_1.setFont(new Font("Lato", Font.BOLD, 12));
		lblNewLabel_2_3_1.setBounds(0, 433, 117, 31);
		add(lblNewLabel_2_3_1);

		spinnerTimeRefresh = new JSpinner();
		spinnerTimeRefresh.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				
			}
		});
		spinnerTimeRefresh.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerTimeRefresh.setBounds(107, 435, 174, 26);
		add(spinnerTimeRefresh);

		JButton btnNewButton_1 = new JButton("Start");
		btnNewButton_1.setBounds(10, 475, 90, 28);
		add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("Pause");
		btnNewButton_1_1.setBounds(107, 475, 90, 28);
		add(btnNewButton_1_1);

		JButton btnNewButton_1_1_1 = new JButton("Resume");
		btnNewButton_1_1_1.setBounds(205, 475, 90, 28);
		add(btnNewButton_1_1_1);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(0, 527, 300, 2);
		add(separator_1_1);

		JLabel lblHandler = new JLabel("Handler");
		lblHandler.setHorizontalAlignment(SwingConstants.CENTER);
		lblHandler.setFont(new Font("Lato", Font.BOLD, 25));
		lblHandler.setBounds(0, 527, 300, 45);
		add(lblHandler);

		JButton btnNewButton_2 = new JButton("Add Animation to Object");
		btnNewButton_2.addActionListener(new ActionListener() {
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
								window.getApp().getCurrentObject(),refresh);

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
		btnNewButton_2.setBounds(52, 614, 190, 31);
		add(btnNewButton_2);

		JButton btnNewButton_2_1_1 = new JButton("My Animations");
		btnNewButton_2_1_1.setBounds(52, 694, 190, 31);
		add(btnNewButton_2_1_1);

		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(0, 751, 300, 2);
		add(separator_1_1_1);

		JLabel lblNewLabel_2_3_1_1 = new JLabel("Refresh Move (ms)");
		lblNewLabel_2_3_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3_1_1.setFont(new Font("Lato", Font.BOLD, 12));
		lblNewLabel_2_3_1_1.setBounds(0, 358, 117, 31);
		add(lblNewLabel_2_3_1_1);

		spinnerRefreshMove = new JSpinner();
		spinnerRefreshMove.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				window.getApp().getCurrentObject()
						.setRefreshMove(Integer.parseInt(spinnerRefreshMove.getModel().getValue().toString()));

			}
		});
		spinnerRefreshMove.setModel(new SpinnerNumberModel(new Integer(10), new Integer(1), null, new Integer(1)));
		spinnerRefreshMove.setBounds(110, 361, 174, 26);
		add(spinnerRefreshMove);

		JLabel lblNewLabel_2_3_2 = new JLabel("WIDTH");
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_3_2.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_3_2.setBounds(6, 256, 61, 26);
		add(lblNewLabel_2_3_2);

		JLabel lblNewLabel_2_1_2_1 = new JLabel("HEIGHT");
		lblNewLabel_2_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_2_1.setFont(new Font("Lato", Font.BOLD, 15));
		lblNewLabel_2_1_2_1.setBounds(140, 258, 67, 22);
		add(lblNewLabel_2_1_2_1);

		spinnerWidth = new JSpinner();
		spinnerWidth.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				window.getApp().getCurrentObject()
						.setWidth(Integer.parseInt(spinnerWidth.getModel().getValue().toString()));
				;
			}
		});
		spinnerWidth.setModel(new SpinnerNumberModel(new Integer(100), new Integer(10), null, new Integer(1)));
		spinnerWidth.setBounds(64, 256, 76, 26);
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
		spinnerHeight.setBounds(205, 256, 76, 26);
		add(spinnerHeight);

		JButton btnNewButton_2_1 = new JButton("Create an Object");
		btnNewButton_2_1.addActionListener(new ActionListener() {
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
		btnNewButton_2_1.setBounds(52, 571, 190, 31);
		add(btnNewButton_2_1);

		JButton btnNewButton_2_1_2 = new JButton("Save Animations Into Object");
		btnNewButton_2_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnNewButton_2_1_2.setBounds(52, 651, 190, 31);
		add(btnNewButton_2_1_2);

	}

	public JLabel getLabSpriteTextFile() {
		return labSpriteTextFile;
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
		
		if(currentObject.getAnimation()!= null)
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
		
		if(currentObject.getAnimation()!= null)
		spinnerTimeRefresh.getModel().setValue(currentObject.getAnimation().getTimeRefresh());
		else
		spinnerTimeRefresh.getModel().setValue(1);

	}

	public JSpinner getSpinnerRefreshMove() {
		return spinnerRefreshMove;
	}
}
