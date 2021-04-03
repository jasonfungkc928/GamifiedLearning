package au.edu.unsw.infs3634.gamifiedlearning;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GamesService {
    @GET("api/v1/stats")
    Call <GamesResponse> getGamesResponse(@Query("page")String page);
}
