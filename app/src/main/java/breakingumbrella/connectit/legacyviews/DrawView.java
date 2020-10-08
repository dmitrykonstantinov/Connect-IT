package breakingumbrella.connectit.legacyviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by dem3n on 19.05.2017.
 */

public class DrawView extends View {
	private float startX, startY, stopX, stopY;
	Paint paint = new Paint();

	public void setAttributes(float startX, float startY, float stopX, float stopY) {
		this.startX = startX;
		this.startY = startY;
		this.stopX = stopX;
		this.stopY = stopY;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public DrawView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}
}
