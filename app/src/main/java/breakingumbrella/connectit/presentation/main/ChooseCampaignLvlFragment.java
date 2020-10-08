package breakingumbrella.connectit.presentation.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.entity.campaign.Trip;
import breakingumbrella.connectit.presentation.campaign.CampaignActivity;

public class ChooseCampaignLvlFragment extends Fragment implements IOnScroll {

    private HashMap<Integer, Integer> images = new HashMap<>();
    private GetTripDescription getTripDescription = new GetTripDescription();
    private INextPage nextPage;
    private IPreviousPage previousPage;

    private void initImages() {
        //Key is rewardCode
        images.put(15, R.drawable.ic_durable);
        images.put(16, R.drawable.ic_invisible);
        images.put(17, R.drawable.ic_destroy);
        images.put(18, R.drawable.ic_second_chance);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        nextPage = (INextPage) context;
        previousPage = (IPreviousPage) context;
        initImages();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initImages();
        View view = inflater.inflate(R.layout.fragment_choose_lvl, container, false);

        Trip trip = (Trip) getArguments().getSerializable("Trip");
        int tripNumber = getArguments().getInt("TripNumber");
        int playNowPosition = getArguments().getInt("PlayNowPosition");
        boolean firstFragment = getArguments().getBoolean("FirstFragment", false);

        if (firstFragment) {
            view.findViewById(R.id.leftArrowImg).setVisibility(View.VISIBLE);
            view.findViewById(R.id.rightArrowImg).setVisibility(View.VISIBLE);
            view.findViewById(R.id.leftArrowImg).setOnClickListener(v -> previousPage.previous());
            view.findViewById(R.id.rightArrowImg).setOnClickListener(v -> nextPage.next());
        }

        if (trip.isTripCompleted()) {
            setTripCompletedInfo(trip, view);
        }

        ((TextView) view.findViewById(R.id.lbTitle)).setText(((TextView) view.findViewById(R.id.lbTitle)).getText().toString() + " " + (tripNumber + 1));

        RecyclerView recyclerView = view.findViewById(R.id.lvlRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new LevelsAdapter(getActivity(), trip.getLvls(), playNowPosition, position -> {
            Intent intent = new Intent(getActivity(), CampaignActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("TripNumber", tripNumber);
            bundle.putInt("LvlNumber", position);
            bundle.putInt("activityToLaunch", 1);
            intent.putExtras(bundle);
            startActivity(intent);
            return null;
        }));

        return view;
    }

    private void setTripCompletedInfo(Trip trip, View view) {
        TripDescription desc = getTripDescription.getDesc(getActivity(), trip.getRewardCode());
        ((TextView) view.findViewById(R.id.rewardTxt)).setText(desc.getRewardText());

        Drawable rewardedAbilityImg = getResources().getDrawable(images.get(trip.getRewardCode()));

        ((ImageView) view.findViewById(R.id.presentImg)).setImageDrawable(getResources().getDrawable(R.drawable.ic_present_opened));
        ((ImageView) view.findViewById(R.id.presentContentImg)).setVisibility(View.VISIBLE);
        ((ImageView) view.findViewById(R.id.presentContentImg)).setImageDrawable(rewardedAbilityImg);

        view.findViewById(R.id.rewardLayout).getLayoutParams().height = displayPointToPixelConverter(128, getActivity());
        view.findViewById(R.id.rewardLayout).getLayoutParams().width = displayPointToPixelConverter(128, getActivity());

        view.requestLayout();
    }


    protected int displayPointToPixelConverter(int dp, Activity activity) {
        final float scale = activity.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onScroll() {
        getView().findViewById(R.id.leftArrowImg).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.rightArrowImg).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSelection() {
        getView().findViewById(R.id.leftArrowImg).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.rightArrowImg).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.leftArrowImg).setOnClickListener(v -> previousPage.previous());
        getView().findViewById(R.id.rightArrowImg).setOnClickListener(v -> nextPage.next());
    }
}
