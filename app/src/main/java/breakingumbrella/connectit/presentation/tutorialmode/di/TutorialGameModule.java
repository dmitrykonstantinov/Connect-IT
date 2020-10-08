package breakingumbrella.connectit.presentation.tutorialmode.di;

import breakingumbrella.connectit.IServiceActivity;
import breakingumbrella.connectit.domain.campaign.IUpdateProfileUseCase;
import breakingumbrella.connectit.domain.campaign.UpdateProfileUseCase;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowScore;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;
import breakingumbrella.connectit.domain.tutorial.usecase.CheckTutorialFieldUseCase;
import breakingumbrella.connectit.domain.tutorial.usecase.ICheckTutorialFieldUseCase;
import breakingumbrella.connectit.domain.tutorial.usecase.TutorialUseCase;
import breakingumbrella.connectit.domain.tutorial.usecase.ITutorialUseCase;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;
import breakingumbrella.connectit.error.handlers.UIAppErrorHandler;
import breakingumbrella.connectit.presentation.tutorialmode.ITutorialGameView;
import dagger.Module;
import dagger.Provides;

@Module
public class TutorialGameModule {

    private final IServiceActivity serviceActivity;
    private final ITutorialGameView tutorialGameView;

    public TutorialGameModule(ITutorialGameView tutorialGameView, IServiceActivity serviceActivity) {
        this.serviceActivity = serviceActivity;
        this.tutorialGameView = tutorialGameView;
    }

    @Provides
    @TutorialScope
    public IServiceActivity provideServiceActivity() {
        return serviceActivity;
    }

    @Provides
    @TutorialScope
    public ITutorialUseCase provideGetTutorialStepUseCase(TutorialUseCase getTutorialStepUseCase) {
        return getTutorialStepUseCase;
    }

    @Provides
    @TutorialScope
    public IUpdateProfileUseCase provideUpdateProfileUseCase(UpdateProfileUseCase updateProfileUseCase) {
        return updateProfileUseCase;
    }

    @Provides
    @TutorialScope
    public ICheckTutorialFieldUseCase provideCheckTutorialFieldUseCase(CheckTutorialFieldUseCase checkTutorialFieldUseCase) {
        return checkTutorialFieldUseCase;
    }

    @Provides
    @TutorialScope
    public IShowText provideShowText() {
        return tutorialGameView;
    }

    @Provides
    @TutorialScope
    public IShowScore provideShowScore() {
        return tutorialGameView;
    }

    @Provides
    @TutorialScope
    public IPutFigure providePutFigure() {
        return tutorialGameView;
    }

    @Provides
    @TutorialScope
    public IAppErrorHandler provideAppErrorHandler(UIAppErrorHandler uiAppErrorHandler) {
        return uiAppErrorHandler;
    }

}
