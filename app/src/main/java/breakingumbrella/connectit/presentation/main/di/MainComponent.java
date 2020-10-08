package breakingumbrella.connectit.presentation.main.di;

import breakingumbrella.connectit.presentation.main.MainActivity;
import dagger.Subcomponent;

@MainScope
@Subcomponent(modules = {MainModule.class})
public interface MainComponent {

	void inject(MainActivity mainActivity);

	@Subcomponent.Builder
	interface Builder {
		MainComponent build();

		Builder chooseCampaignLvlModuleBuilder(MainModule mainModule);
	}

}
