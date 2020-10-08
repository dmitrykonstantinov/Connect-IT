package breakingumbrella.connectit;

import android.content.Context;
import android.content.Intent;

public interface IServiceActivity {

	void transitionToActivity(Intent intent, Context context);

	void moveToMainScreen();
}
