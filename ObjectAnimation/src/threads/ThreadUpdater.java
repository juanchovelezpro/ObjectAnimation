package threads;

import javax.swing.SpinnerNumberModel;

import view.MainWindow;
import view.PanelOptions;

public class ThreadUpdater extends Thread {

	private MainWindow window;

	public ThreadUpdater(MainWindow window) {

		this.window = window;
	}

	public void updateObjectParameters() {

		PanelOptions options = window.getOptions();

		if(window.getApp().getCurrentObject().getSpeedX() != 0)
		options.getSpinnerPosX().setValue(window.getApp().getCurrentObject().getX());
		
		if(window.getApp().getCurrentObject().getSpeedY() != 0)
		options.getSpinnerPosY().setValue(window.getApp().getCurrentObject().getY());


	}

	@Override
	public void run() {

		while (true) {

			updateObjectParameters();
			
			int refresh = Integer.parseInt(window.getOptions().getSpinnerTimeRefresh().getModel().getValue().toString());
			
			if(window.getApp().getCurrentObject().getAnimation()!=null)
			window.getApp().getCurrentObject().getAnimation().setTimeRefresh(refresh);
			
		}

	}

}
