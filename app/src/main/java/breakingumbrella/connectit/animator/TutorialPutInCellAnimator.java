package breakingumbrella.connectit.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.View;
import android.widget.GridLayout;

import javax.inject.Inject;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.presentation.PresentationUtils;

public class TutorialPutInCellAnimator {

	@Inject
	TutorialPutInCellAnimator() {  }

	public void animate(Activity activity, Figure figure, int fieldSizY) {

		GridLayout grid = activity.findViewById(R.id.field);
		final View viewAnimate = grid.getChildAt(PresentationUtils.linearize(figure.getPositionX(), figure.getPositionY(), fieldSizY));

		AnimatorSet set = (AnimatorSet)  AnimatorInflater.loadAnimator(activity, R.animator.focus_in_cell);
		set.setTarget(viewAnimate); // set the view you want to animate
		set.start();
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				animateCellHighlighting(activity, viewAnimate);
			}
		});

	}

	public void animateCellHighlighting(Activity activity, View viewAnimate) {
		AnimatorSet set1 = (AnimatorSet)  AnimatorInflater.loadAnimator(activity, R.animator.hightlight_cell);
		set1.setTarget(viewAnimate); // set the view you want to animate
		set1.start();
	}

	public void animateAbilityHighlighting(Activity activity, View viewAnimate) {
		AnimatorSet set1 = (AnimatorSet)  AnimatorInflater.loadAnimator(activity, R.animator.hightlight_ability);
		set1.setTarget(viewAnimate); // set the view you want to animate
		set1.start();
	}

}
