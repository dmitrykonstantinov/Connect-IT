package breakingumbrella.connectit.presentation.main;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import javax.inject.Inject;

import androidx.drawerlayout.widget.DrawerLayout;

import breakingumbrella.connectit.BuildConfig;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.presentation.myabilities.MyAbilitiesActivity;
import breakingumbrella.connectit.presentation.profile.ProfileActivity;
import breakingumbrella.connectit.presentation.settings.PolicyActivity;
import breakingumbrella.connectit.presentation.testmenu.TestMenuActivity;

public class MenuController {

    MainActivity activity;
    DrawerLayout drawerLayout;

    @Inject
    MenuController() {
    }

    public void init(MainActivity activity) {
        this.activity = activity;
        this.drawerLayout = activity.findViewById(R.id.drawer_layout);
        setUpClickListeners();
    }

    private void setUpClickListeners() {
        activity.findViewById(R.id.policyContainer).setOnClickListener(v -> {
            Intent intent = new Intent(activity, PolicyActivity.class);
            activity.transitionToActivity(intent);
            drawerLayout.closeDrawer(Gravity.LEFT);
        });

        if (BuildConfig.DEBUG) {
            activity.findViewById(R.id.profileContainer).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.profileContainer).setOnClickListener(v -> {
                Intent intent = new Intent(activity, ProfileActivity.class);
                activity.transitionToActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
            });
        }
//        activity.findViewById(R.id.testMenuContainer).setOnClickListener(v -> {
//            Intent intent = new Intent(activity, TestMenuActivity.class);
//            activity.transitionToActivity(intent);
//            drawerLayout.closeDrawer(Gravity.LEFT);
//        });
        activity.findViewById(R.id.myAbilitiesContainer).setOnClickListener(v -> {
            Intent intent = new Intent(activity, MyAbilitiesActivity.class);
            activity.transitionToActivity(intent);
            drawerLayout.closeDrawer(Gravity.LEFT);
        });
    }

}
