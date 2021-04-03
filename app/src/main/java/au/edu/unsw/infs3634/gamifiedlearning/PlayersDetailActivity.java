package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayersDetailActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.gamifiedlearning.intent_message";

    //Initialize variables
    private int mId;
    private TextView tvPlayerName;
    private TextView tvPosition;
    private TextView tvHeightFeet;
    private TextView tvHeightInch;
    private TextView tvWeightPound;
    private TextView tvTeam;
    private TextView tvDivision;
    private TextView tvCity;
    private TextView tvConference;
    private ImageView ivSearch;
    private static final String TAG = "PlayersDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn2);

        //Assign variables
        tvPlayerName = findViewById(R.id.tvPlayerName);
        tvPosition = findViewById(R.id.tvPosition);
        tvHeightFeet = findViewById(R.id.tvHeightFeet);
        tvHeightInch = findViewById(R.id.tvHeightInch);
        tvWeightPound = findViewById(R.id.tvWeightPound);
        tvTeam = findViewById(R.id.tvTeam);
        tvConference = findViewById(R.id.tvConference);
        tvDivision = findViewById(R.id.tvDivision);
        tvCity = findViewById(R.id.tvCity);
        ivSearch = findViewById(R.id.ivSearch);
        //Getting the intent and the intent message by getIntExtra
        Intent intent = getIntent();
        mId = intent.getIntExtra(INTENT_MESSAGE, 0);
        //Initialise retrofit and convert the JSON response into a JAVA object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LearnService service = retrofit.create(LearnService.class);
        //Get the first 3 pages of the endpoint
        for(int i=1; i<4; i++) {
            Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
            playerResponseCall.enqueue(new Callback<PlayerResponse>() {
                @Override
                public void onResponse(Call<PlayerResponse> call, retrofit2.Response<PlayerResponse> playerResponse) {
                    //List assigned
                    List<Datum> datums = playerResponse.body().getData();
                    Log.d(TAG, "onResponse: GET DATA");
                    Log.d(TAG, "onResponse: ID IS " + mId);
                    for (final Datum datum : datums) {
                        if (datum.getId().equals(mId)) {
                            //if the id are the same then set text to TextView
                            setTitle(datum.getFirstName() + " " + datum.getLastName());
                            tvPlayerName.setText(datum.getFirstName() + " " + datum.getLastName());
                            tvPosition.setText(datum.getPosition());
                            Log.d(TAG, "onResponse: TEAM IS " + datum.getTeam().getFullName());
                            tvTeam.setText(datum.getTeam().getFullName());
                            tvConference.setText(datum.getTeam().getConference());
                            tvDivision.setText(datum.getTeam().getDivision());
                            tvCity.setText(datum.getTeam().getCity());


                            Log.d(TAG, "onResponse: Height is " + datum.getHeightFeet());
                            //Some of the data retrieved are null so we implemenet a try catch to prevent crashing and if the data is null, N/A will be shown
                            try {
                                tvHeightFeet.setText((Integer) datum.getHeightFeet());
                                tvHeightInch.setText((Integer) datum.getHeightInches());
                                tvWeightPound.setText((Integer) datum.getWeightPounds());
                            } catch (Exception e) {
                                tvHeightFeet.setText("N/A");
                                tvHeightInch.setText("N/A");
                                tvWeightPound.setText("N/A");
                            }
                            //Once user clicked the button, it will navigate user to the website with players name on the search bar
                            ivSearch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    searchPlayer(datum.getLastName(), datum.getFirstName());
                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<PlayerResponse> call, Throwable t) {

                }
            });
        }
    }
    //Method to navigate user to the website with players name on the search bar
    private void searchPlayer(String lastName, String firstName){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q= " + lastName + " " + firstName));
        startActivity(intent);
    }
}