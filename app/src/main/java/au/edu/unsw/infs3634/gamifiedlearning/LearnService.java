package au.edu.unsw.infs3634.gamifiedlearning;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LearnService {
    @GET("api/v1/players")
    Call<PlayerResponse> getPlayerResponse(@Query("page")String page);
    
}
