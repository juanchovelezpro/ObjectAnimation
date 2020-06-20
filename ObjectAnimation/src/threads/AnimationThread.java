package threads;

import java.io.Serializable;

import animation.Animation;

public class AnimationThread extends Thread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Animation animation;

	public AnimationThread(Animation animation) {

		this.animation = animation;

	}

	@Override
	public void run() {

		while (true) {

			try {

				if (!animation.isPause()) {
					sleep(animation.getTimeRefresh());

					animation.runAnimation();
				} else {

					synchronized (this) {

						wait();

					}

				}

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		}

	}

	public synchronized void resumeAnimation() {

		notify();
	}

}
