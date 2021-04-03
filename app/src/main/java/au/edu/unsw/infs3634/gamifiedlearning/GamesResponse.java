package au.edu.unsw.infs3634.gamifiedlearning;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GamesResponse {

    @SerializedName("data")
    @Expose
    private List<GamesDatum> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<GamesDatum> getData() {
        return data;
    }

    public void setData(List<GamesDatum> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}