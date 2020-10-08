package breakingumbrella.connectit;

import breakingumbrella.connectit.presentation.aigamemode.di.AiGameModule;
import breakingumbrella.connectit.presentation.aigamemode.di.AiGameSubComponent;
import breakingumbrella.connectit.presentation.campaign.di.CampaignModule;
import breakingumbrella.connectit.presentation.campaign.di.CampaignSubComponent;
import breakingumbrella.connectit.presentation.main.di.MainModule;
import breakingumbrella.connectit.presentation.main.di.MainComponent;
import breakingumbrella.connectit.presentation.launcher.LauncherSubComponent;
import breakingumbrella.connectit.presentation.myabilities.MyAbilitiesSubComponent;
import breakingumbrella.connectit.presentation.testmenu.TestMenuSubComponent;
import breakingumbrella.connectit.presentation.profile.ProfileSubComponent;
import breakingumbrella.connectit.presentation.registration.RegistrationSubComponent;
import breakingumbrella.connectit.presentation.settings.PolicySubComponent;
import breakingumbrella.connectit.presentation.splitscreenmode.di.SplitScreenModule;
import breakingumbrella.connectit.presentation.splitscreenmode.di.SplitScreenSubComponent;
import breakingumbrella.connectit.presentation.tutorialmode.ITutorialGameView;
import breakingumbrella.connectit.presentation.tutorialmode.di.TutorialGameModule;
import breakingumbrella.connectit.presentation.tutorialmode.di.TutorialSubComponent;

public class ComponentInjector {

	private ApplicationComponent applicationComponent;

	ComponentInjector(ApplicationComponent applicationComponent) {
		this.applicationComponent = applicationComponent;
	}

	private ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}

	public SplitScreenSubComponent getSplitScreenSubComponent(IServiceActivity serviceActivity) {
		SplitScreenSubComponent.Builder builder = getApplicationComponent().splitScreenSubComponentBuilder();
		builder.splitScreenModuleBuilder(new SplitScreenModule(serviceActivity));
		return builder.build();
	}

	public AiGameSubComponent getAiGameSubComponent(IServiceActivity serviceActivity) {
		AiGameSubComponent.Builder builder = getApplicationComponent().aiGameSubComponentBuilder();
		builder.aiGameModuleBuilder(new AiGameModule(serviceActivity));
		return builder.build();
	}

	public CampaignSubComponent getCampaignSubComponent(IServiceActivity serviceActivity) {
		CampaignSubComponent.Builder builder = getApplicationComponent().campaignComponentBuilder();
		builder.campaignModuleBuilder(new CampaignModule(serviceActivity));
		return builder.build();
	}

	public MainComponent getChooseCampaignLvlComponent(IServiceActivity serviceActivity) {
		MainComponent.Builder builder = getApplicationComponent().chooseCampaignLvlComponentBuilder();
		builder.chooseCampaignLvlModuleBuilder(new MainModule(serviceActivity));
		return builder.build();
	}

	public LauncherSubComponent getLauncherSubComponent() {
		return getApplicationComponent().launcherSubComponentBuilder().build();
	}

	public TestMenuSubComponent getMainSubComponent() {
		return getApplicationComponent().mainSubComponentBuilder().build();
	}

	public PolicySubComponent getSettingsSubComponent() {
		return getApplicationComponent().settingsSubComponentBuilder().build();
	}

	public ProfileSubComponent getProfileSubComponent() {
		return getApplicationComponent().profileSubComponentBuilder().build();
	}

	public MyAbilitiesSubComponent getAbilitiesSubComponent() {
		return getApplicationComponent().myAbilitiesSubComponent().build();
	}

	public RegistrationSubComponent getRegistrationSubComponent() {
		return getApplicationComponent().registrationSubComponentBuilder().build();
	}

	public TutorialSubComponent getTutorialSubComponent(ITutorialGameView tutorialGameView, IServiceActivity serviceActivity) {
		TutorialSubComponent.Builder builder = getApplicationComponent().tutorialSubComponentBuilder();
		builder.tutorialScreenModuleBuilder(new TutorialGameModule(tutorialGameView, serviceActivity)).build();
		return builder.build();
	}

}
