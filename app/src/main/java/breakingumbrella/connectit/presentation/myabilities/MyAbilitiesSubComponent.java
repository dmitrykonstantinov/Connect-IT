package breakingumbrella.connectit.presentation.myabilities;

import dagger.Subcomponent;

@MyAbilitiesScope
@Subcomponent
public interface MyAbilitiesSubComponent {

    void inject(MyAbilitiesActivity abilitiesActivity);

    @Subcomponent.Builder
    interface Builder {
        MyAbilitiesSubComponent build();
    }

}
