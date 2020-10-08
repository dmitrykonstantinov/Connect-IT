package breakingumbrella.connectit.presentation.signin

import breakingumbrella.connectit.entity.gameobjects.AbilityType
import breakingumbrella.connectit.entity.profile.CampaignPosition
import breakingumbrella.connectit.entity.profile.Profile
import breakingumbrella.connectit.entity.profile.ProfileFeatures
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.createProfile(): Profile {
    val profile = Profile()
    profile.id = this.uid
    profile.nickName = this.displayName
    profile.verified = this.isVerified()
    profile.campaignPosition = CampaignPosition()
    profile.profileFeatures = ProfileFeatures()
    val abilities = java.util.HashMap<String, Int>()
    abilities.put(AbilityType.durableFigure.toString(), 0)
    abilities.put(AbilityType.invisibleFigure.toString(), 0)
    abilities.put(AbilityType.destroyFigure.toString(), 0)
    abilities.put(AbilityType.secondChance.toString(), 0)
    profile.profileFeatures.abilities = abilities
    return profile
}

fun FirebaseUser.isVerified(): Boolean {
    if (this.providerData.size > 1) {
        return true
    } else {
        return false
    }
}