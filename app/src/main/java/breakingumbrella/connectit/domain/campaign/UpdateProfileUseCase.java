package breakingumbrella.connectit.domain.campaign;

import javax.inject.Inject;

import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.entity.profile.Profile;

public class UpdateProfileUseCase implements IUpdateProfileUseCase {

	private IProfileRepository profileRepository;

	@Inject
	UpdateProfileUseCase(IProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public void updateProfile(Profile localProfile) {
		profileRepository.updateProfile(localProfile, aVoid -> { }, exc -> {});
	}


}
