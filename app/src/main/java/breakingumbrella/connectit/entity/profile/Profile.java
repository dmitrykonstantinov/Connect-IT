package breakingumbrella.connectit.entity.profile;

import java.io.Serializable;
import java.util.Date;

public class Profile implements Serializable {

    private String id;
    private int elo;
    private String nickName;
    private Boolean isVerified = false;
    private Date dateOfCreation;
    private Integer profileVersion;

    public Integer getProfileVersion() {
        return profileVersion;
    }

    public void setProfileVersion(Integer profileVersion) {
        this.profileVersion = profileVersion;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    private ProfileFeatures profileFeatures;

    private CampaignPosition campaignPosition;

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName.substring(0, Math.min(nickName.length(), 12));
    }

    public CampaignPosition getCampaignPosition() {
        return campaignPosition;
    }

    public void setCampaignPosition(CampaignPosition campaignPosition) {
        this.campaignPosition = campaignPosition;
    }

    public ProfileFeatures getProfileFeatures() {
        return profileFeatures;
    }

    public void setProfileFeatures(ProfileFeatures profileFeatures) {
        this.profileFeatures = profileFeatures;
    }

}
