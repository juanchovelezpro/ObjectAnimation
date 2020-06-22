package threads;

import view.MainWindow;
import view.PanelOptions;
import model.Object;

public class ThreadUpdater extends Thread {

	private MainWindow window;

	public ThreadUpdater(MainWindow window) {

		this.window = window;
	}

	public void updateObjectParameters() {

		PanelOptions options = window.getOptions();

		Object currentObject = window.getApp().getCurrentProject().getCurrentObject();

		if (currentObject != null) {
			if (currentObject.getSpeedX() != 0)
				options.getSpinnerPosX().setValue(currentObject.getX());

			if (currentObject.getSpeedY() != 0)
				options.getSpinnerPosY().setValue(currentObject.getY());

			int refresh = Integer
					.parseInt(window.getOptions().getSpinnerTimeRefresh().getModel().getValue().toString());

			if (refresh != 1) {
				if (currentObject.getAnimation() != null)
					currentObject.getAnimation().setTimeRefresh(refresh);
			}
		}

	}

	@Override
	public void run() {

		while (true) {

			updateObjectParameters();

		}

	}

}
