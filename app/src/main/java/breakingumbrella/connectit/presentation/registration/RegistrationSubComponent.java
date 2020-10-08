package breakingumbrella.connectit.presentation.registration;

import dagger.Subcomponent;

@RegistrationScope
@Subcomponent
public interface RegistrationSubComponent {

    void inject(RegistrationActivity registrationActivity);

    @Subcomponent.Builder
    interface Builder {
        RegistrationSubComponent build();
    }

}
