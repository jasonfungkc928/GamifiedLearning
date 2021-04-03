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

public class TeamsActivity extends AppCompatActivity {

    //Initialize variables
    private RecyclerView mRecyclerView;
    private TeamsAdapter mAdapter;
    private static final String TAG = "TeamsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        //Set Page Title
        setTitle("Learn - Teams");

        //Assign variables
        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        Log.d(TAG, "onCreate: RecyclerView Initialised");
        TeamsAdapter.RecyclerViewClickListener listener = new TeamsAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, Integer mId) {
                launchLearnActivity2(mId);
            }
        };
        //Initialise adapter
        mAdapter = new TeamsAdapter(new ArrayList<TeamDatum>(), listener);

        Log.d(TAG, "onCreate: ADAPTER INITIALISED");
        //Set adapter to recyclerView
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreate: ADAPTER SET");
        //Initialise retrofit and convert the JSON response into a JAVA object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TeamService service = retrofit.create(TeamService.class);
        Log.d(TAG, "onCreate: RETROFIT ");
            Call<TeamResponse> teamResponseCall = service.getTeamResponse();
            Log.d(TAG, "onCreate: Call ");
            teamResponseCall.enqueue(new Callback<TeamResponse>() {
                @Override
                public void onResponse(Call<TeamResponse> call, Response<TeamResponse> teamResponse) {
                    //List assigned
                    List<TeamDatum> datums = teamResponse.body().getData();
                    Log.d(TAG, "onResponse: List assigned");
                    //Set data to adapter
                    mAdapter.setData(datums);
                    Log.d(TAG, "onResponse: SET DATA TO ADAPTER");
                }

                @Override
                public void onFailure(Call<TeamResponse> call, Throwable t) {

                }
            });

    }
    //Method to navigate user to TeamsDetailActivity and sending the message
    private void launchLearnActivity2(Integer message) {
        Intent intent = new Intent(TeamsActivity.this, TeamsDetailActivity.class);
        intent.putExtra(PlayersDetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_teams, menu);
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