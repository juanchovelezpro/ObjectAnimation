package threads;

import animation.Animation;

public class AnimationThread extends Thread {

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
