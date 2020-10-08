package breakingumbrella.connectit.presentation.campaign.di;

import breakingumbrella.connectit.drawer.GridDrawerModule;
import breakingumbrella.connectit.presentation.campaign.CampaignActivity;
import dagger.Subcomponent;

@CampaignScope
@Subcomponent(modules = {GridDrawerModule.class, CampaignModule.class})
public interface CampaignSubComponent {
    void inject(CampaignActivity campaignActivity);

    @Subcomponent.Builder
    interface Builder {
        CampaignSubComponent build();
        Builder campaignModuleBuilder(CampaignModule campaignModule);
    }
}
