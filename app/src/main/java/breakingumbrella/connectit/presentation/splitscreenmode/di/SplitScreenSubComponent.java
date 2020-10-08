package breakingumbrella.connectit.presentation.splitscreenmode.di;

import breakingumbrella.connectit.drawer.GridDrawerModule;
import breakingumbrella.connectit.presentation.splitscreenmode.SplitScreenGameActivity;
import dagger.Subcomponent;

@SplitScreenScope
@Subcomponent(modules = {GridDrawerModule.class, SplitScreenModule.class})
public interface SplitScreenSubComponent {

	void inject(SplitScreenGameActivity loadingClassicNetActivity);

	@Subcomponent.Builder
	interface Builder {
		SplitScreenSubComponent build();
		Builder splitScreenModuleBuilder(SplitScreenModule splitScreenModule);
	}

}
