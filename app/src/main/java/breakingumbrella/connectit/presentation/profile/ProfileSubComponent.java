package breakingumbrella.connectit.presentation.profile;

import dagger.Subcomponent;

@ProfileScope
@Subcomponent
public interface ProfileSubComponent {

    void inject(ProfileActivity mainActivity);

    @Subcomponent.Builder
    interface Builder {
        ProfileSubComponent build();
    }

}
