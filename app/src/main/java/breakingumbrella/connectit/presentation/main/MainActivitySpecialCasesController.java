package breakingumbrella.connectit.presentation.main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import breakingumbrella.connectit.ad.ShowType;

public class MainActivitySpecialCasesController {

    @Inject
    MainActivitySpecialCasesController() {
        controlChain = new LinkedList<>();
        controlChainInner = new TreeSet<>();
    }

    private MainActivity mainActivity;
    private Queue<Integer> controlChain;
    private SortedSet<Integer> controlChainInner;

    public Queue<Integer> getControlChain(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        createControlChain();
        mapToQueue();
        return controlChain;
    }

    private void mapToQueue() {
        controlChain.addAll(controlChainInner);
    }

    private void createControlChain() {
        ShowType showType = mainActivity.adHandler.requestAd();
        if (showType == ShowType.interstitial) {
            controlChainInner.add(MainActivitySpecialCases.showAd);
        }
        if (showType == ShowType.rewardedVideo) {
            controlChainInner.add(MainActivitySpecialCases.showAdVideo);
        }
        if (mainActivity.errorHolder.isErrorExist()) {
            controlChainInner.add(MainActivitySpecialCases.showError);
        }
        if (mainActivity.globalConfig.isNewAbilityUnlocked()) {
            controlChainInner.add(MainActivitySpecialCases.showNewAbilityUnlocked);
        }
        if (mainActivity.rateUsHandler.shouldShow(mainActivity, mainActivity.globalConfig)) {
            controlChainInner.add(MainActivitySpecialCases.showRateUs);
        }
        if (mainActivity.getIntent().getBooleanExtra("ShowTutorial", false)) {
            controlChainInner.add(MainActivitySpecialCases.showTutorial);
        }
    }


}
