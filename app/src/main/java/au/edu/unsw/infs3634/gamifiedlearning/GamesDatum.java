package au.edu.unsw.infs3634.gamifiedlearning;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GamesDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ast")
    @Expose
    private Integer ast;
    @SerializedName("blk")
    @Expose
    private Integer blk;
    @SerializedName("dreb")
    @Expose
    private Integer dreb;
    @SerializedName("fg3_pct")
    @Expose
    private Double fg3Pct;
    @SerializedName("fg3a")
    @Expose
    private Integer fg3a;
    @SerializedName("fg3m")
    @Expose
    private Integer fg3m;
    @SerializedName("fg_pct")
    @Expose
    private Double fgPct;
    @SerializedName("fga")
    @Expose
    private Integer fga;
    @SerializedName("fgm")
    @Expose
    private Integer fgm;
    @SerializedName("ft_pct")
    @Expose
    private Double ftPct;
    @SerializedName("fta")
    @Expose
    private Integer fta;
    @SerializedName("ftm")
    @Expose
    private Integer ftm;
    @SerializedName("game")
    @Expose
    private Games games;
    @SerializedName("min")
    @Expose
    private String min;
    @SerializedName("oreb")
    @Expose
    private Integer oreb;
    @SerializedName("pf")
    @Expose
    private Integer pf;
    @SerializedName("player")
    @Expose
    private Player player;
    @SerializedName("pts")
    @Expose
    private Integer pts;
    @SerializedName("reb")
    @Expose
    private Integer reb;
    @SerializedName("stl")
    @Expose
    private Integer stl;
    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("turnover")
    @Expose
    private Integer turnover;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAst() {
        return ast;
    }

    public void setAst(Integer ast) {
        this.ast = ast;
    }

    public Integer getBlk() {
        return blk;
    }

    public void setBlk(Integer blk) {
        this.blk = blk;
    }

    public Integer getDreb() {
        return dreb;
    }

    public void setDreb(Integer dreb) {
        this.dreb = dreb;
    }

    public Double getFg3Pct() {
        return fg3Pct;
    }

    public void setFg3Pct(Double fg3Pct) {
        this.fg3Pct = fg3Pct;
    }

    public Integer getFg3a() {
        return fg3a;
    }

    public void setFg3a(Integer fg3a) {
        this.fg3a = fg3a;
    }

    public Integer getFg3m() {
        return fg3m;
    }

    public void setFg3m(Integer fg3m) {
        this.fg3m = fg3m;
    }

    public Double getFgPct() {
        return fgPct;
    }

    public void setFgPct(Double fgPct) {
        this.fgPct = fgPct;
    }

    public Integer getFga() {
        return fga;
    }

    public void setFga(Integer fga) {
        this.fga = fga;
    }

    public Integer getFgm() {
        return fgm;
    }

    public void setFgm(Integer fgm) {
        this.fgm = fgm;
    }

    public Double getFtPct() {
        return ftPct;
    }

    public void setFtPct(Double ftPct) {
        this.ftPct = ftPct;
    }

    public Integer getFta() {
        return fta;
    }

    public void setFta(Integer fta) {
        this.fta = fta;
    }

    public Integer getFtm() {
        return ftm;
    }

    public void setFtm(Integer ftm) {
        this.ftm = ftm;
    }

    public Games getGames() {
        return games;
    }

    public void setGame(Games games) {
        this.games = games;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Integer getOreb() {
        return oreb;
    }

    public void setOreb(Integer oreb) {
        this.oreb = oreb;
    }

    public Integer getPf() {
        return pf;
    }

    public void setPf(Integer pf) {
        this.pf = pf;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getPts() {
        return pts;
    }

    public void setPts(Integer pts) {
        this.pts = pts;
    }

    public Integer getReb() {
        return reb;
    }

    public void setReb(Integer reb) {
        this.reb = reb;
    }

    public Integer getStl() {
        return stl;
    }

    public void setStl(Integer stl) {
        this.stl = stl;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }

}
