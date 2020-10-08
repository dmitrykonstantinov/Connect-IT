package breakingumbrella.connectit;

import android.app.Activity;
import com.robotium.recorder.executor.Executor;

@SuppressWarnings("rawtypes")
public class MainActivityExecutor extends Executor {

	@SuppressWarnings("unchecked")
	public MainActivityExecutor() throws Exception {
		super((Class<? extends Activity>) Class.forName("breakingumbrella.tictactocmobileapp.view.MainActivity"),  "breakingumbrella.tictactocmobileapp.R.id.", new android.R.id(), false, false, "1493915618037");
	}

	public void setUp() throws Exception { 
		super.setUp();
	}
}
