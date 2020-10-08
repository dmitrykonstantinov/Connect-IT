package breakingumbrella.connectit.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.widget.TextView;

import javax.inject.Inject;

import breakingumbrella.connectit.R;

public class TutorialScoreAnimator {

	@Inject
	TutorialScoreAnimator() { }

	public void animate(Activity activity) {
		TextView view = activity.findViewById(R.id.Score_Player_1);
		AnimatorSet set = (AnimatorSet)  AnimatorInflater.loadAnimator(activity, R.animator.focus_on_score);
		set.setTarget(view);
		set.start();
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				AnimatorSet set1 = (AnimatorSet)  AnimatorInflater.loadAnimator(activity, R.animator.focus_on_score_back_to_default);
				set1.setTarget(view); // set the view you want to animate
				set1.start();
			}
		});
	}
}
