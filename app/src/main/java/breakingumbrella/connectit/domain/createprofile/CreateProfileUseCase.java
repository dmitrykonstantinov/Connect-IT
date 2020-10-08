package breakingumbrella.connectit.domain.createprofile;

import java.util.Date;

import javax.inject.Inject;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.entity.profile.Profile;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;

public class CreateProfileUseCase implements ICreateProfileUseCase {

	private GlobalConfig globalConfig;
	private IProfileRepository profileRepository;

	@Inject
	CreateProfileUseCase(GlobalConfig globalConfig, IProfileRepository profileRepository) {
		this.globalConfig = globalConfig;
		this.profileRepository = profileRepository;
	}

	@Override
	public void createProfile(Profile localProfile, ICreateProfilePresentation createProfilePresentation, IApiErrorHandler apiErrorHandler) {
		localProfile.setElo(localProfile.getElo() == 0 ? 1000 : localProfile.getElo());
		profileRepository.isProfileExist(localProfile.getId(), isExist -> {
			if (isExist) {
				profileRepository.getProfile(localProfile.getId(), profile -> {
					globalConfig.setProfile(profile);
					createProfilePresentation.onProfileCreated();
				}, exc1 -> {
					apiErrorHandler.handleApiError(new ApiError(exc1));
					createLocalProfile(createProfilePresentation);
				});
			} else {
				//First time create profile
				localProfile.setDateOfCreation(new Date());
				localProfile.setProfileVersion(2);
				profileRepository.updateProfile(localProfile,
						Void -> {
							globalConfig.setProfile(localProfile);
							createProfilePresentation.onProfileCreated();
						},
						exc1 -> {
							apiErrorHandler.handleApiError(new ApiError(exc1));
							createLocalProfile(createProfilePresentation);
						});
			}
		}, exc1 -> {
			apiErrorHandler.handleApiError(new ApiError(exc1));
			createLocalProfile(createProfilePresentation);
		});
	}

	@Override
	public void createLocalProfile(ICreateProfilePresentation createProfilePresentation) {
		Profile profile = new Profile();
		profile.setNickName("You");
		createProfilePresentation.onProfileCreated();
	}
}
