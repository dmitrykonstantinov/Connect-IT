package breakingumbrella.connectit.presentation.aigamemode.di;

import breakingumbrella.connectit.presentation.aigamemode.AiGameActivity;
import breakingumbrella.connectit.drawer.GridDrawerModule;
import dagger.Subcomponent;

@AiGameScope
@Subcomponent(modules = {GridDrawerModule.class, AiGameModule.class})
public interface AiGameSubComponent {
	void inject(AiGameActivity aiGameActivity);

	@Subcomponent.Builder
	interface Builder {
		AiGameSubComponent build();
		Builder aiGameModuleBuilder(AiGameModule aiGameModule);
	}
}
