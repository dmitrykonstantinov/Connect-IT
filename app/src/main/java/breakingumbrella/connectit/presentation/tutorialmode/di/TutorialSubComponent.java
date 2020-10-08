package breakingumbrella.connectit.presentation.tutorialmode.di;

import breakingumbrella.connectit.drawer.GridDrawerModule;
import breakingumbrella.connectit.presentation.tutorialmode.TutorialGameActivity;
import dagger.Subcomponent;

@TutorialScope
@Subcomponent(modules = {GridDrawerModule.class, TutorialGameModule.class})
public interface TutorialSubComponent {

    void inject(TutorialGameActivity tutorialGameModeActivity);

    @Subcomponent.Builder
    interface Builder {
        TutorialSubComponent build();

        Builder tutorialScreenModuleBuilder(TutorialGameModule tutorialGameModule);
    }

}

