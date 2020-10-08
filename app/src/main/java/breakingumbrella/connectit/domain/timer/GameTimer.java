package breakingumbrella.connectit.domain.timer;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {

	public GameTimer() {}

	private Timer timer;
	private long refreshRateMs;
	private long progress;
	private float timeRemain;
	private long startTime;
	private boolean isStoped = false;

	private long secondsToWork;
	private long startDelay;
	private boolean isProgressIsInverted;
	private OnTimerTickCallback callback;

	public void initTimer(float refreshRateHz, long startDelay, boolean isProgressIsInverted, OnTimerTickCallback callback) {
		this.callback = callback;
		this.startDelay = startDelay;
		this.isProgressIsInverted = isProgressIsInverted;
		timer = new Timer();
		refreshRateMs = (long) ((1f / refreshRateHz) * 1000L);
	}

	public void initTimer(int refreshRateHz, long startDelay, long secondsToWork, boolean isProgressIsInverted, OnTimerTickCallback callback) {
		this.callback = callback;
		this.startDelay = startDelay;
		this.timeRemain = secondsToWork;
		this.secondsToWork = secondsToWork;
		this.isProgressIsInverted = isProgressIsInverted;
		timer = new Timer();
		refreshRateMs = (long) ((1f / refreshRateHz) * 1000L);
	}

	public synchronized void startTimer() {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				if (startTime != 0) {
					timeRemain = secondsToWork - (System.currentTimeMillis() - startTime) / 1000f; //To seconds
				} else {
					startTime = System.currentTimeMillis();
				}
				progress = (int) (timeRemain * 100) / secondsToWork;
				if (timeRemain < 0) {
					stopTimer();
					callback.OnTimerFinish();
				}
				if (!isStoped) {
					if (isProgressIsInverted) {
						callback.OnTick(100 - progress, (int) timeRemain);
					} else {
						callback.OnTick(progress, (int) timeRemain);
					}
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, startDelay, refreshRateMs);
	}

	public synchronized void stopTimer() {
		isStoped = true;
		if(timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
	}
}
