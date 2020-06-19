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

				sleep(animation.getTimeRefresh());

				animation.runAnimation();

			} catch (Exception ex) {

				ex.printStackTrace();

			}

		}

	}

}
