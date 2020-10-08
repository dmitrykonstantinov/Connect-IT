package breakingumbrella.connectit;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import breakingumbrella.connectit.presentation.main.MainActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by Dmitry Konstantinov on 09.08.2018.
 */

public class ApplicationExtender extends MultiDexApplication {

    private static ComponentInjector componentInjector;

    public static ComponentInjector getComponentInjector() {
        return componentInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        componentInjector = new ComponentInjector(DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(getApplicationContext())).build());
        Stetho.initializeWithDefaults(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "getInstanceId failed", task.getException());
                return;
            }
            String token = task.getResult().getToken();
            Log.d(TAG, token);
        });


    }

}
