package au.edu.unsw.infs3634.gamifiedlearning;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Games {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("home_team_id")
    @Expose
    private Integer homeTeamId;
    @SerializedName("home_team_score")
    @Expose
    private Integer homeTeamScore;
    @SerializedName("period")
    @Expose
    private Integer period;
    @SerializedName("postseason")
    @Expose
    private Boolean postseason;
    @SerializedName("season")
    @Expose
    private Integer season;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("visitor_team_id")
    @Expose
    private Integer visitorTeamId;
    @SerializedName("visitor_team_score")
    @Expose
    private Integer visitorTeamScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Integer homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Boolean getPostseason() {
        return postseason;
    }

    public void setPostseason(Boolean postseason) {
        this.postseason = postseason;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getVisitorTeamId() {
        return visitorTeamId;
    }

    public void setVisitorTeamId(Integer visitorTeamId) {
        this.visitorTeamId = visitorTeamId;
    }

    public Integer getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(Integer visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }

}