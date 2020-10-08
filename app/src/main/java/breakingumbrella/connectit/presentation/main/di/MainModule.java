package breakingumbrella.connectit.presentation.main.di;

import breakingumbrella.connectit.IServiceActivity;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;
import breakingumbrella.connectit.error.handlers.UIAppErrorHandler;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

	private final IServiceActivity serviceActivity;

	public MainModule(IServiceActivity serviceActivity){
		this.serviceActivity = serviceActivity;
	}

	@Provides
	@MainScope
	public IServiceActivity provideServiceActivity() {
		return serviceActivity;
	}

	@Provides
	@MainScope
	public IAppErrorHandler provideAppErrorHandler(UIAppErrorHandler uiAppErrorHandler) {
		return uiAppErrorHandler;
	}

}
