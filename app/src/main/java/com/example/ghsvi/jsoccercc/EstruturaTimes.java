package com.example.ghsvi.jsoccercc;

/**
 * Created by ghsvi on 11/12/2017.
 */

public class EstruturaTimes {

    private String strTeam;
    private String strStadium;
    private String strDescriptionEN;
    private String strTeamBadge;
    private String strFacebook;
    private String strLeague;
    private String intFormedYear;
    private String strManager;

    public EstruturaTimes(String strTeam, String strStadium, String strDescriptionEN, String strTeamBadge, String strFacebook, String strLeague, String intFormedYear, String strManager) {
        this.strTeam = strTeam;
        this.strStadium = strStadium;
        this.strDescriptionEN = strDescriptionEN;
        this.strTeamBadge = strTeamBadge;
        this.strFacebook = strFacebook;
        this.strLeague = strLeague;
        this.intFormedYear = intFormedYear;
        this.strManager = strManager;
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

    public String getStrFacebook() {
        return strFacebook;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public String getIntFormedYear() {
        return intFormedYear;
    }

    public String getStrManager() {
        return strManager;
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

    public void setStrFacebook(String strFacebook) {
        this.strFacebook = strFacebook;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public void setIntFormedYear(String intFormedYear) {
        this.intFormedYear = intFormedYear;
    }

    public void setStrManager(String strManager) {
        this.strManager = strManager;
    }
}

