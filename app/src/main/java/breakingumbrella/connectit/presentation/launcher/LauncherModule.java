package breakingumbrella.connectit.presentation.launcher;

import breakingumbrella.connectit.domain.createprofile.CreateProfileUseCase;
import breakingumbrella.connectit.domain.createprofile.ICreateProfileUseCase;
import dagger.Module;
import dagger.Provides;

@Module
public class LauncherModule {

	@Provides
	@LauncherScope
	public ICreateProfileUseCase provideCreateGameUseCase(CreateProfileUseCase createGameMPUseCase) {
		return createGameMPUseCase;
	}


}
