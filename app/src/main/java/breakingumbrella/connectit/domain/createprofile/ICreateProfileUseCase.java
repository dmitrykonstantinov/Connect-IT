package breakingumbrella.connectit.domain.createprofile;

import breakingumbrella.connectit.entity.profile.Profile;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;

public interface ICreateProfileUseCase {

	void createProfile(Profile profile, ICreateProfilePresentation createProfilePresentation, IApiErrorHandler apiErrorHandler);

	void createLocalProfile(ICreateProfilePresentation createProfilePresentation);

}
