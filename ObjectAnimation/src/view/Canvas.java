package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.Object;


public class Canvas extends JPanel implements MouseMotionListener, MouseListener{

	public static final int WIDTH = MainWindow.WIDTH - PanelOptions.WIDTH;
	public static final int HEIGHT = MainWindow.HEIGHT;

	private MainWindow window;
	
	private Color colorBackground;

	

	public Canvas(MainWindow window) {
		setBorder(new LineBorder(Color.DARK_GRAY, 1, true));

		this.window = window;
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(WIDTH, HEIGHT);
		setLocation(0, 5);

	
		
		addMouseListener(this);
		addMouseMotionListener(this);

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		renderObjects(g);

		repaint();

	}

	public void renderObjects(Graphics g) {

		ArrayList<Object> objects = window.getApp().getMyObjects();

		for (int i = 0; i < objects.size(); i++) {

			Object temp = objects.get(i);

			temp.render(g);

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent e) {

		ArrayList<Object> objects = window.getApp().getMyObjects();

		for (int i = 0; i < objects.size(); i++) {

			Object temp = objects.get(i);

			if (e.getX() >= temp.getX() && e.getX() <= temp.getX() + temp.getWidth() && e.getY() >= temp.getY()
					&& e.getY() <= temp.getY() + temp.getHeight()) {

				window.getApp().setCurrentObject(temp);
				temp.setX(e.getX() - temp.getWidth() / 2);
				temp.setY(e.getY() - temp.getHeight() / 2);

			}

		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		Object current = window.getApp().getCurrentObject();
		

		if (e.getX() >= current.getX() && e.getX() <= current.getX() + current.getWidth() && e.getY() >= current.getY()
				&& e.getY() <= current.getY() + current.getHeight()) {

			window.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			
			current.setX(e.getX() - current.getWidth() / 2);
			current.setY(e.getY() - current.getHeight() / 2);

			window.getOptions().updateValues();

		}else {
			
			window.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {


	}

}
