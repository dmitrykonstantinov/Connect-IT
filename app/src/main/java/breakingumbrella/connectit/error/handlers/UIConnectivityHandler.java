package breakingumbrella.connectit.error.handlers;

import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class UIConnectivityHandler implements INetworkConnectivityHandler {

	private WeakReference<Context> contextWeakReference;

	@Inject
	public UIConnectivityHandler(Context context) {
		this.contextWeakReference = new WeakReference<>(context);
	}

	@Override
	public void handleConnectionFailed() {
		Context context = contextWeakReference.get();
		if(context == null) {
			throw new Error("Can't resolve error cause of missing Android Context missing WeakReference");
		}
		Toast.makeText(context, "Can't connect to global internet", Toast.LENGTH_LONG).show();
	}

}
