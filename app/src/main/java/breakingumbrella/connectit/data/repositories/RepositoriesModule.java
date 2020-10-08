package breakingumbrella.connectit.data.repositories;

import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.data.repositories.profile.ProfileRepository;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoriesModule {

    @Binds
    abstract IProfileRepository provideProfileRepository(ProfileRepository profileRepository);

}
