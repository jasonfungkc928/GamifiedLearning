package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GamesActivity extends AppCompatActivity {

    //Initialize variables
    private RecyclerView mRecyclerView;
    private GamesAdapter mAdapter;
    private static final String TAG = "GamesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        //Set Page Title
        setTitle("Learn - Stats");

        //Assign variables
        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        Log.d(TAG, "onCreate: RecyclerView Initialised");
        //Once the user click it, user will be navigate to the GamesDetailActivity
        GamesAdapter.RecyclerViewClickListener listener = new GamesAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, Integer mId) {
                launchLearnActivity2(mId);
            }
        };
        //Initialise the adapter
        mAdapter = new GamesAdapter(new ArrayList<GamesDatum>(), listener);

        Log.d(TAG, "onCreate: ADAPTER INITIALISED");
        //Set the adapter to recyclerView
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreate: ADAPTER SET");

        //Initialise retrofit and convert the JSON response into a JAVA object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GamesService service = retrofit.create(GamesService.class);
        Log.d(TAG, "onCreate: RETROFIT ");
        //Get the first 3 pages of the endpoint
        for (int i = 1; i < 4; i++) {
            Call<GamesResponse> gamesResponseCall = service.getGamesResponse(String.valueOf(i));
            Log.d(TAG, "onCreate: Call ");
            gamesResponseCall.enqueue(new Callback<GamesResponse>() {
                @Override
                public void onResponse(Call<GamesResponse> call, Response<GamesResponse> gamesResponse) {
                   //List assigned
                    List<GamesDatum> datums = gamesResponse.body().getData();
                    Log.d(TAG, "onResponse: List assigned");
                    //Set data to adapter
                    mAdapter.setData(datums);
                    Log.d(TAG, "onResponse: SET DATA TO ADAPTER");
                }

                @Override
                public void onFailure(Call<GamesResponse> call, Throwable t) {

                }
            });

        }
    }
    //Method to navigate users to GamesDetailActivity class
    private void launchLearnActivity2(Integer message) {
        Intent intent = new Intent(GamesActivity.this, GamesDetailActivity.class);
        intent.putExtra(GamesDetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_games, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}