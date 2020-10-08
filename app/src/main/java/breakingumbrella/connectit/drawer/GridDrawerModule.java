package breakingumbrella.connectit.drawer;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class GridDrawerModule {

	@Binds
	abstract IGridDrawer provideGridDrawer(GridDrawer gridDrawer);

}
