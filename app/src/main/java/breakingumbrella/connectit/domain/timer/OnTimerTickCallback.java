package breakingumbrella.connectit.domain.timer;

/**
 * Created by dmitryk on 31.07.2017.
 */

public interface OnTimerTickCallback {
	void OnTick(long progress, long milSecondsRemain);
	void OnTimerFinish();
}
