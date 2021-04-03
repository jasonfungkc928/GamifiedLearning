package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeamsDetailActivity extends AppCompatActivity {

    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.gamifiedlearning.intent_message";
    //Initialise variables
    private int mId;
    private TextView tvTeamName;
    private TextView tvFullName;
    private TextView tvAbbreviation;
    private TextView tvCitys;
    private TextView tvConferences;
    private TextView tvDivisions;
    private ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_detail2);
        //Assign variables
        tvTeamName = findViewById(R.id.tvTeamName);
        tvFullName = findViewById(R.id.tvFullName);
        tvAbbreviation = findViewById(R.id.tvAbbreviation);
        tvCitys = findViewById(R.id.tvCitys);
        tvConferences = findViewById(R.id.tvConferences);
        tvDivisions = findViewById(R.id.tvDivisions);
        ivSearch = findViewById(R.id.ivSearch);
        //Get intent message
        Intent intent = getIntent();
        mId = intent.getIntExtra(INTENT_MESSAGE, 0);
        //Initialise retrofit and convert the JSON response into a JAVA object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TeamService service = retrofit.create(TeamService.class);
        //Get the first 3 pages of the endpoint
        for(int i=1; i<4; i++) {
            Call<TeamResponse> teamResponseCall = service.getTeamResponse();
            teamResponseCall.enqueue(new Callback<TeamResponse>() {
                @Override
                public void onResponse(Call<TeamResponse> call, retrofit2.Response<TeamResponse> teamResponse) {
                    //List assigned
                    List<TeamDatum> datums = teamResponse.body().getData();
                    for (final TeamDatum datum : datums) {
                        if (datum.getId().equals(mId)) {
                            //If their id are the same, then set text to the textView
                            setTitle(datum.getName());
                            tvTeamName.setText(datum.getName());
                            tvFullName.setText(datum.getFullName());
                            tvAbbreviation.setText(datum.getAbbreviation());
                            tvConferences.setText(datum.getConference());
                            tvDivisions.setText(datum.getDivision());
                            tvCitys.setText(datum.getCity());
                            //Once the user clicked the button, it will navigate user to the website with the team name on the search bar
                            ivSearch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    searchPlayer(datum.getFullName());
                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<TeamResponse> call, Throwable t) {

                }
            });
        }
    }
    //Method to navigate user to the website with the team name searched
    private void searchPlayer(String fullName){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q= " + fullName));
        startActivity(intent);
    }
    }