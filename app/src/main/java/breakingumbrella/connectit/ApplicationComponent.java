package breakingumbrella.connectit;

import javax.inject.Singleton;

import breakingumbrella.connectit.data.repositories.RepositoriesModule;
import breakingumbrella.connectit.presentation.aigamemode.di.AiGameSubComponent;
import breakingumbrella.connectit.presentation.campaign.di.CampaignSubComponent;
import breakingumbrella.connectit.presentation.main.di.MainComponent;
import breakingumbrella.connectit.presentation.launcher.LauncherSubComponent;
import breakingumbrella.connectit.presentation.myabilities.MyAbilitiesSubComponent;
import breakingumbrella.connectit.presentation.testmenu.TestMenuSubComponent;
import breakingumbrella.connectit.presentation.profile.ProfileSubComponent;
import breakingumbrella.connectit.presentation.registration.RegistrationSubComponent;
import breakingumbrella.connectit.presentation.settings.PolicySubComponent;
import breakingumbrella.connectit.presentation.splitscreenmode.di.SplitScreenSubComponent;
import breakingumbrella.connectit.presentation.tutorialmode.di.TutorialSubComponent;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoriesModule.class})
public interface ApplicationComponent {

	SplitScreenSubComponent.Builder splitScreenSubComponentBuilder();

	AiGameSubComponent.Builder aiGameSubComponentBuilder();

	LauncherSubComponent.Builder launcherSubComponentBuilder();

	TestMenuSubComponent.Builder mainSubComponentBuilder();

	TutorialSubComponent.Builder tutorialSubComponentBuilder();

	MainComponent.Builder chooseCampaignLvlComponentBuilder();

	PolicySubComponent.Builder settingsSubComponentBuilder();

	RegistrationSubComponent.Builder registrationSubComponentBuilder();

	ProfileSubComponent.Builder profileSubComponentBuilder();

	MyAbilitiesSubComponent.Builder myAbilitiesSubComponent();

	CampaignSubComponent.Builder campaignComponentBuilder();

}
