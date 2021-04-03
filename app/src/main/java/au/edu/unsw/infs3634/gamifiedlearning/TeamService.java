package au.edu.unsw.infs3634.gamifiedlearning;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TeamService {
    @GET("api/v1/teams")
    Call<TeamResponse> getTeamResponse();
}
