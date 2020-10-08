package breakingumbrella.connectit.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.codemybrainsout.ratingdialog.RatingDialog;

import java.util.Queue;

import javax.annotation.Nullable;
import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.ad.AdHandler;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.drawer.error.ErrorDialog;
import breakingumbrella.connectit.drawer.newability.NewAbilityDialog;
import breakingumbrella.connectit.drawer.tutorial.MenuTutorialDialog;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.profile.CampaignLvls;
import breakingumbrella.connectit.entity.profile.CampaignPosition;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.ErrorHolder;
import breakingumbrella.connectit.error.IError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;
import breakingumbrella.connectit.presentation.launcher.LauncherActivity;
import breakingumbrella.connectit.stats.AnalyticsHandler;
import breakingumbrella.connectit.stats.RateUsHandler;
import breakingumbrella.connectit.stats.events.AdClicked;
import breakingumbrella.connectit.stats.events.AdVideoWasShown;
import breakingumbrella.connectit.stats.events.AdWasShown;
import breakingumbrella.connectit.stats.events.RateUsWasShown;
import breakingumbrella.connectit.stats.events.RewardedClick;
import breakingumbrella.connectit.stats.events.UserPassStep7Tutorial;

public class MainActivity extends CustomServiceActivity implements IApiErrorHandler, IAppErrorHandler, INextPage, IPreviousPage {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private ScreenSlidePagerAdapter pagerAdapter;

    @Inject
    ErrorHolder errorHolder;

    @Inject
    AdHandler adHandler;

    @Inject
    AnalyticsHandler analyticsHandler;

    @Inject
    GlobalConfig globalConfig;

    @Inject
    IAppErrorHandler appErrorHandler;

    @Inject
    CampaignLvls campaignLvls;

    @Inject
    MenuController menuController;

    @Inject
    MainActivitySpecialCasesController specialController;

    @Inject
    RateUsHandler rateUsHandler;

    private Queue<Integer> controlChain;

    boolean consentValue = true;

    private CampaignPosition position;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationExtender.getComponentInjector().getChooseCampaignLvlComponent(this).inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureNavigationDrawer();
        configureToolbar();

        position = globalConfig.getProfile().getCampaignPosition();
        campaignLvls.setPosition(position);

        mPager = findViewById(R.id.lvlViewPager);
        mPager.setPageTransformer(true, new CubeOutTransformer());
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(pageChangeListener);

        menuController.init(this);
//        initAdMediator();

        controlChain = specialController.getControlChain(this);
        handleControlChain();

        mPager.setCurrentItem(position.getTrip());
    }

    private void handleControlChain() {
        Integer controlEvent = controlChain.poll();
        if (controlEvent == null) {
            return;
        }
        switch (controlEvent) {
            //Recursion
            case MainActivitySpecialCases.showError:
                showError(errorHolder.getError());
                break;
            case MainActivitySpecialCases.showNewAbilityUnlocked:
                showNewAbilityUnlocked(this::handleControlChain);
                break;
            case MainActivitySpecialCases.showAd:
                showInterstitial(this::handleControlChain);
                break;
            case MainActivitySpecialCases.showAdVideo:
                showRewardedVideo(this::handleControlChain);
                break;
            case MainActivitySpecialCases.showRateUs:
                tryShowRateUs(this::handleControlChain);
                break;
            case MainActivitySpecialCases.showTutorial:
                showTutorialStep(this::handleControlChain);
                break;
        }

    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
    }

    private void configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            // Android home
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }

    private void showTutorialStep(IOnSpecialCaseComplete onSpecialCaseComplete) {
        MenuTutorialDialog dialog = new MenuTutorialDialog();
        dialog.showFragmentDialog(this, onSpecialCaseComplete::onComplete);
        analyticsHandler.logEvent(new UserPassStep7Tutorial(0));
    }

    private void showNewAbilityUnlocked(IOnSpecialCaseComplete onSpecialCaseComplete) {
        NewAbilityDialog dialog = new NewAbilityDialog();
        dialog.showFragmentDialog(this, globalConfig.getAbilityType(), onSpecialCaseComplete::onComplete);
        globalConfig.setAbilityType(AbilityType.none);
        globalConfig.setNewAbilityUnlocked(false);
    }

    private boolean showError(@Nullable IError error) {
        if (error == null) {
            return false;
        }
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.showFragmentDialog(this, this::restartApp, error);
        return true;
    }

    private void restartApp() {
        //It's not actually restart app. Just start user flow from start
        Intent intent = new Intent(this, LauncherActivity.class);
        transitionToActivity(intent, this);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            this.finishAffinity();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void tryShowRateUs(IOnSpecialCaseComplete onSpecialCaseComplete) {
        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
//				.icon(getDrawable(R.drawable.noll_d))
//				.session(7)
                .threshold(3)
                .title(getString(R.string.how_was_experience))
                .titleTextColor(R.color.black)
                .positiveButtonText(getString(R.string.not_now))
                .negativeButtonText(getString(R.string.never))
                .positiveButtonTextColor(R.color.grey_500)
                .negativeButtonTextColor(R.color.black)
                .formTitle(getString(R.string.submit_feedback))
                .formHint(getString(R.string.tell_us_smth))
                .formSubmitText(getString(R.string.submit))
                .formCancelText(getString(R.string.cancel))
                .ratingBarColor(R.color.mainGrey)
                .playstoreUrl("https://play.google.com/store/apps/details?id=breakingumbrella.connectit")
                .onRatingBarFormSumbit(feedback -> Toast.makeText(MainActivity.this, "smth", Toast.LENGTH_LONG).show()).build();

        ratingDialog.show();
        ratingDialog.setOnDismissListener(dialog -> {
            rateUsHandler.setShown(this);
            analyticsHandler.logEvent(new RateUsWasShown());
            onSpecialCaseComplete.onComplete();
        });
    }


    private int pagePosition;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pagePosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    ((IOnScroll) pagerAdapter.getRegisteredFragment(pagePosition)).onSelection();//this will be called when animation ends
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING: {
                    ((IOnScroll) pagerAdapter.getRegisteredFragment(pagePosition)).onScroll();
                }
                break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    break;
            }
        }
    };

    @Override
    public void next() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    public void previous() {
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Trip", campaignLvls.getTrips().get(position));
            bundle.putInt("TripNumber", position);
            int lvlPos = -1;
            if (MainActivity.this.position.getTrip() == position) {
                lvlPos = MainActivity.this.position.getLvl();
            }
            bundle.putInt("PlayNowPosition", lvlPos);
            if (position == 0) {
                bundle.putBoolean("FirstFragment", true);
            }
            ChooseCampaignLvlFragment fragment = new ChooseCampaignLvlFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return campaignLvls.getTrips().size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    @Override
    public void handleAppError(AppError appError) {
        appErrorHandler.handleAppError(appError);
    }

    @Override
    public void handleApiError(ApiError apiError) {
    }

    public void transitionToActivity(Intent intent) {
        startActivity(intent);
        this.overridePendingTransition(R.animator.slid_enter,
                R.animator.slid_exit);
    }

    //  ============================ AD REGION ============================================//

//    private void initAdMediator() {
//        String appKey = "f9b3c0bab7ec28a8ebbced865d5250008bdc5591d313b0e2";
//        //True only for russia
//        int adTypes = Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO;
//        Appodeal.initialize(this, appKey, adTypes, true);
//    }

    private void showInterstitial(IOnSpecialCaseComplete onSpecialCaseComplete) {
        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean b) {

            }

            @Override
            public void onInterstitialFailedToLoad() {

            }

            @Override
            public void onInterstitialShown() {
                adHandler.adWasShown(MainActivity.this);
                analyticsHandler.logEvent(new AdWasShown(adHandler.getShowReason()));
                onSpecialCaseComplete.onComplete();
            }

            @Override
            public void onInterstitialShowFailed() {

            }

            @Override
            public void onInterstitialClicked() {
                analyticsHandler.logEvent(new AdClicked());
            }

            @Override
            public void onInterstitialClosed() {

            }

            @Override
            public void onInterstitialExpired() {

            }
        });
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }

    private void showRewardedVideo(IOnSpecialCaseComplete onSpecialCaseComplete) {
        Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacks() {
            @Override
            public void onRewardedVideoLoaded(boolean b) {

            }

            @Override
            public void onRewardedVideoFailedToLoad() {

            }

            @Override
            public void onRewardedVideoShown() {
                adHandler.adWasShown(MainActivity.this);
                analyticsHandler.logEvent(new AdVideoWasShown());
                onSpecialCaseComplete.onComplete();
            }

            @Override
            public void onRewardedVideoShowFailed() {

            }

            @Override
            public void onRewardedVideoFinished(double v, String s) {

            }

            @Override
            public void onRewardedVideoClosed(boolean b) {

            }

            @Override
            public void onRewardedVideoExpired() {

            }

            @Override
            public void onRewardedVideoClicked() {
                analyticsHandler.logEvent(new RewardedClick());
            }

        });
        Appodeal.show(this, Appodeal.REWARDED_VIDEO);
    }
}
