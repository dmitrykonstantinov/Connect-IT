package breakingumbrella.connectit.presentation.settings;

import dagger.Subcomponent;

@Subcomponent
public interface PolicySubComponent {

    void inject(PolicyActivity mainActivity);

    @Subcomponent.Builder
    interface Builder {
        PolicySubComponent build();
    }

}
