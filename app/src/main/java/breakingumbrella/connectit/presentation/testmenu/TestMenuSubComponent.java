package breakingumbrella.connectit.presentation.testmenu;

import dagger.Subcomponent;

@TestMenuScope
@Subcomponent
public interface TestMenuSubComponent {

	void inject(TestMenuActivity testMenuActivity);

	@Subcomponent.Builder
	interface Builder {
		TestMenuSubComponent build();
	}

}
