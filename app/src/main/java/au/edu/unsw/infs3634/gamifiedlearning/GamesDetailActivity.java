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

public class GamesDetailActivity extends AppCompatActivity {

    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.gamifiedlearning.intent_message";
    private static final String TAG = "GamesDetailActivity";
    //Initialise variables
    private int mId;
    private TextView tvPlayersName;
    private TextView tvPoints;
    private TextView tvRebound;
    private TextView tvSteal;
    private TextView tvBlock;
    private TextView tvAssist;
    private ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_detail);
        //Assign variables
        tvPlayersName = findViewById(R.id.tvPlayersName);
        tvPoints = findViewById(R.id.tvPoints);
        tvRebound = findViewById(R.id.tvRebound);
        tvSteal = findViewById(R.id.tvSteal);
        tvBlock = findViewById(R.id.tvBlock);
        tvAssist = findViewById(R.id.tvAssist);
        ivSearch = findViewById(R.id.ivSearch);

        //Getting the intent message by getIntExtra
        Intent intent = getIntent();
        mId = intent.getIntExtra(INTENT_MESSAGE, 0);

        //Initialise retrofit and convert the JSON response into a JAVA object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GamesService service = retrofit.create(GamesService.class);
        //Get the first 3 pages from the endpoint
        for(int i=1; i<4; i++) {
            Call<GamesResponse> gamesResponseCall = service.getGamesResponse(String.valueOf(i));
            gamesResponseCall.enqueue(new Callback<GamesResponse>() {
                @Override
                public void onResponse(Call<GamesResponse> call, retrofit2.Response<GamesResponse> gamesResponse) {
                    //List assigned
                    List<GamesDatum> datums = gamesResponse.body().getData();
                    for (final GamesDatum datum : datums) {
                        Log.d(TAG, "onResponse: datumID" + datum.getId());
                        Log.d(TAG, "onResponse: mID" + mId);
                       //If the id are the same, then set title to the player name
                        if (datum.getId().equals(mId)) {
                            setTitle(datum.getPlayer().getFirstName() + " " + datum.getPlayer().getLastName());
                            tvPlayersName.setText(datum.getPlayer().getFirstName() + " " + datum.getPlayer().getLastName());
                            Log.d(TAG, "onResponse: Name" + datum.getPlayer().getFirstName() + datum.getPlayer().getLastName());
                            Log.d(TAG, "onResponse: tvPoints are " + datum.getPts());
                            Log.d(TAG, "onResponse: tvRebounds are" + datum.getReb());
                            Log.d(TAG, "onResponse: tvAssist are" + datum.getAst());
                            //Some of the data are null so implementing try catch can prevent the app from crashing
                            try {
                                tvPoints.setText(String.valueOf(datum.getPts()));
                                Log.d(TAG, "onResponse: PTS" + datum.getPts());
                                tvRebound.setText(String.valueOf(datum.getReb()));
                                Log.d(TAG, "onResponse: REB" + datum.getReb());
                                tvAssist.setText(String.valueOf(datum.getAst()));
                                Log.d(TAG, "onResponse: AST" + datum.getAst());
                                tvSteal.setText(String.valueOf(datum.getStl()));
                                tvBlock.setText(String.valueOf(datum.getBlk()));
                            } catch (Exception e) {
                                tvPoints.setText("N/A");
                                tvRebound.setText("N/A");
                                tvAssist.setText("N/A");
                                tvSteal.setText("N/A");
                                tvBlock.setText("N/A");
                            }
                            //Once the user click it, it will navigate the User to google with the player name on the search bar
                            ivSearch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    searchPlayer(datum.getPlayer().getLastName(), datum.getPlayer().getFirstName());
                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<GamesResponse> call, Throwable t) {

                }
            });
        }
    }
    //Method to navigate user to the website with players name on the serach bar (implicit intent)
    private void searchPlayer(String lastName, String firstName){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q= " + lastName + " " + firstName));
        startActivity(intent);
    }
}