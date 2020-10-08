package breakingumbrella.connectit.domain.classicgame.usecase;

import javax.inject.Inject;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.data.repositories.profilegc.IGCProfileRepository;
import breakingumbrella.connectit.domain.classicgame.middleware.FinishGame;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.entity.context.GCProfileWrapperKt;
import breakingumbrella.connectit.entity.context.GameMetaInfo;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public class FinishGameUseCase implements IFinishGameUseCase {

	private IBaseGameFinishPresentation gameFinishPresentation;
	private IGameContextRepository gameRepository;
	private IProfileRepository profileRepository;
	private IGCProfileRepository gcProfileRepository;
	private FinishGame finishGame;
	private GameMetaInfo gameMetaInfo;
	private GlobalConfig globalConfig;

	@Inject
	public FinishGameUseCase(IGameContextRepository gameRepository, IGCProfileRepository gcProfileRepository,
							 IProfileRepository profileRepository, GameMetaInfo gameMetaInfo, GlobalConfig globalConfig) {
		this.gameRepository = gameRepository;
		this.gameMetaInfo = gameMetaInfo;
		this.globalConfig = globalConfig;
		this.profileRepository = profileRepository;
		this.gcProfileRepository = gcProfileRepository;
		finishGame = new FinishGame();
	}

	@Override
	public void surrender(IBaseGameFinishPresentation gameFinishPresentation, IAppErrorHandler appErrorHandler,
						  IApiErrorHandler apiErrorHandler) {
		this.gameFinishPresentation = gameFinishPresentation;
		gameRepository.getGCSById(gameMetaInfo.getGameId(), gameContext -> {
			GCProfileWrapperKt.getEnemy(gameContext, gameMetaInfo.getGcProfileId()).setScore(99);
			finishGame.finish(gameContext, gameMetaInfo, globalConfig, gameRepository, profileRepository,
					gameFinishPresentation::onGameFinish, exc1 -> apiErrorHandler.handleApiError(new ApiError(exc1)));
		}, exc1 -> apiErrorHandler.handleApiError(new ApiError(exc1)));
	}

	@Override
	public void terminate(IAppErrorHandler appErrorHandler) {
		gcProfileRepository.flush();
		if(appErrorHandler == null) {
			throw new Error("AppErrorHandler was not init during lateInit");
		}
	}
}
