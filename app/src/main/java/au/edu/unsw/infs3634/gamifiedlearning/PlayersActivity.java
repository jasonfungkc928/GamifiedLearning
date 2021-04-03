package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayersActivity extends AppCompatActivity {

    //Initialize variables
    private RecyclerView mRecyclerView;
    private PlayersAdapter mAdapter;
    private static final String TAG = "LearnActivity1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn1);

        //Set Page Title
        setTitle("Learn - Players");

        //Assign variables
        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        Log.d(TAG, "onCreate: RecyclerView Initialised");
        PlayersAdapter.RecyclerViewClickListener listener = new PlayersAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, Integer mId) {
                launchLearnActivity2(mId);
            }
        };
        //Initialise the adapter
        mAdapter = new PlayersAdapter(new ArrayList<Datum>(), listener);

        Log.d(TAG, "onCreate: ADAPTER INITIALISED");
        ////Set the adapter to recyclerView
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreate: ADAPTER SET");
        //Initialise retrofit and convert the JSON response into a JAVA object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LearnService service = retrofit.create(LearnService.class);
        Log.d(TAG, "onCreate: RETROFIT ");
        //Get the first 3 pages of the endpoint
        for(int i=1; i<4; i++) {
            Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
            Log.d(TAG, "onCreate: Call ");
            playerResponseCall.enqueue(new Callback<PlayerResponse>() {
                @Override
                public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> playerResponse) {
                   //List assigned
                    List<Datum> datums = playerResponse.body().getData();
                    Log.d(TAG, "onResponse: List assigned");
                    //Set data to adapter
                    mAdapter.setData(datums);
                    Log.d(TAG, "onResponse: SET DATA TO ADAPTER");
                    //Sort the list with ascending order
                    mAdapter.sort(PlayersAdapter.SORT_METHOD_ASCENDING);
                    Log.d(TAG, "onResponse: ON RESPONSE WORKS");
                }

                @Override
                public void onFailure(Call<PlayerResponse> call, Throwable t) {

                }
            });
        }

    }
    //Method to navigate user to playersDetailActivity and passing the data
    private void launchLearnActivity2(Integer message){
        Intent intent = new Intent(this, PlayersDetailActivity.class);
        intent.putExtra(PlayersDetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_learn, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_position:
                mAdapter.sort(PlayersAdapter.SORT_METHOD_POSITION);
                return true;
            case R.id.sort_descending:
                mAdapter.sort(PlayersAdapter.SORT_METHOD_DESCENDING);
                return true;
            case R.id.sort_ascending:
                mAdapter.sort(PlayersAdapter.SORT_METHOD_ASCENDING);
            case R.id.sort_teams:
                mAdapter.sort(PlayersAdapter.SORT_METHOD_TEAMS);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}