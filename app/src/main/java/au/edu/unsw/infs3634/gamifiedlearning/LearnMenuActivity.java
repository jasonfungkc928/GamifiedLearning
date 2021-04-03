package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LearnMenuActivity extends AppCompatActivity {

    //Initialize variables
    private Button btnPlayers;
    private Button btnTeams;
    private Button btnGames;
    private Button btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_menu);
        //Set Page Title
        setTitle("Learn");
        //Assign variables
        btnPlayers = findViewById(R.id.btnPlayers);
        btnTeams = findViewById(R.id.btnTeams);
        btnGames = findViewById(R.id.btnGames);
        btnMain = findViewById(R.id.btnMain);

        //Once the user click the button, it will navigate the user to the playersActivity
        btnPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPlayers();
            }
        });
        //Once the user click the button, it will navigate the user to the teamActivity
        btnTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTeams();
            }
        });
        //Once the user click the button, it will navigate the user to the gamesActivity
        btnGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGames();
            }
        });
        //Once the user click the button, it will navigate the user to the menuActivity
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain();
            }
        });

    }
    //Method to navigate user to playersActivity
    public void navigateToPlayers(){
        Intent myIntent = new Intent(LearnMenuActivity.this, PlayersActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to teamActivity
    public void navigateToTeams(){
        Intent myIntent = new Intent(LearnMenuActivity.this, TeamsActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to gamesActivity
    public void navigateToGames(){
        Intent myIntent = new Intent(LearnMenuActivity.this, GamesActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to menuActivity
    public void navigateToMain(){
        Intent myIntent = new Intent(LearnMenuActivity.this, MenuActivity.class);
        startActivity(myIntent);
    }


}