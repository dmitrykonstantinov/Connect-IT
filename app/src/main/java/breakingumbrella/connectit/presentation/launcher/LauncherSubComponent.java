package breakingumbrella.connectit.presentation.launcher;

import dagger.Subcomponent;

@LauncherScope
@Subcomponent(modules = {LauncherModule.class})
public interface LauncherSubComponent {

	void inject(LauncherActivity launcherActivity);

	@Subcomponent.Builder
	interface Builder {
		LauncherSubComponent build();
	}
}
