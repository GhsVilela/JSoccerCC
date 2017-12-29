package com.example.ghsvi.jsoccercc;

/**
 * Created by ghsvi on 11/12/2017.
 */

public class EstruturaTimes {

    private String strTeam;
    private String strStadium;
    private String strDescriptionEN;
    private String strTeamBadge;

    public EstruturaTimes(String strTeam, String strStadium, String strDescriptionEN, String strTeamBadge) {
        this.strTeam = strTeam;
        this.strStadium = strStadium;
        this.strDescriptionEN = strDescriptionEN;
        this.strTeamBadge = strTeamBadge;
    }

    public String getStrTeam() {
        return strTeam;
    }

    public String getStrStadium() {
        return strStadium;
    }

    public String getStrDescriptionEN() {
        return strDescriptionEN;
    }

    public String getStrTeamBadge() {
        return strTeamBadge;
    }

    public void setStrTeamBadge(String strTeamBadge) {
        this.strTeamBadge = strTeamBadge;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public void setStrStadium(String strStadium) {
        this.strStadium = strStadium;
    }

    public void setStrDescriptionEN(String strDescriptionEN) {
        this.strDescriptionEN = strDescriptionEN;
    }
}

