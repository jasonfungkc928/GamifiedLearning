package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LearnActivity extends AppCompatActivity {

    //Initialize variables
    private Button btnLearn;
    private TextView tvInformation;
    private TextView tvReady;
    private Button btnMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        //Set Page Title
        setTitle("Learn");

        //Assign variables
        btnLearn = findViewById(R.id.btnLearn);
        tvInformation = findViewById(R.id.tvInformation);
        tvReady = findViewById(R.id.tvReady);
        btnMainMenu = findViewById(R.id.btnMainMenu);
        //Once the button is clicked, user will be navigate to the learn1 page
        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLearn1();
            }
        });
        //Once the button is clicked, user will be navigate to the MainMenu Page
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainMenu();
            }
        });
        //Set text to textView
        tvInformation.setText("Welcome to the learning section of the app, in this section you will learn about different NBA players such as their position, height, weight, and team that they are belonged to.");
        tvReady.setText("If you are ready to learn, please press the START LEARNING button below");
    }

    //Method for user to navigate to Learn1 Page
    public void navigateToLearn1(){
        Intent myIntent = new Intent(LearnActivity.this, LearnMenuActivity.class);
        startActivity(myIntent);
    }

    //Method for user to navigate to MainMenu Page
    public void navigateToMainMenu(){
        Intent myIntent = new Intent(LearnActivity.this, MenuActivity.class);
        startActivity(myIntent);
    }

}