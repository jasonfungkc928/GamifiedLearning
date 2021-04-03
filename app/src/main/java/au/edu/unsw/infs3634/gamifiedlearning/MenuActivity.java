package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    //Initialize variables
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userUID;

    private Button quizBtn;
    private Button learnBtn;
    private Button medalBtn;
    private Button settingsBtn;
    private Button notesBtn;
    private Button leaderboardBtn;
    private ImageView ivAvatar;
    private TextView tvWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Set Page Title
        setTitle("NBA Fans");

        //Assign variables
        learnBtn = findViewById(R.id.learnBtn);
        quizBtn = findViewById(R.id.quizBtn);
        medalBtn = findViewById(R.id.medalsBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        notesBtn = findViewById(R.id.notesBtn);
        leaderboardBtn = findViewById(R.id.leaderboardBtn);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvWelcome = findViewById(R.id.tvWelcome);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userUID = user.getUid();

        //Set Default Greeting
        tvWelcome.setText("Welcome to the NBA Fans App!!");

        databaseReference.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                if (profile != null){
                    //Get User's First Name
                    String fName = profile.firstName;
                    //Format User's First Name - Capitalise the First Letter
                    String greetingName = fName.substring(0, 1).toUpperCase() + fName.substring(1);

                    //Set Customised Greeting
                    tvWelcome.setText("Welcome to the NBA Fans App " + greetingName + "!!");

                    //Get User's Avatar
                    int avatar = profile.avatar;
                    ivAvatar.setImageResource(avatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuActivity.this, "An Error Has Occurred Try Again!!", Toast.LENGTH_LONG).show();
            }
        });

        //Once the button is clicked, user will be navigate to Settings Page
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSettings();
            }
        });
        //Once the button is clicked, user will be navigate to Quiz Page
        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToQuiz();
            }
        });
        //Once the button is clicked, user will be navigate to Achievements Page
        medalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMedal();
            }
        });
        //Once the button is clicked, user will be navigate to Learn Page
        learnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLearn();
            }
        });
        //Once the button is clicked, user will be navigate to Notes Page
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNotes();
            }
        });
        //Once the button is clicked, user will be navigate to Avatar Page
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAvatar();
            }
        });
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLeaderBoard();
            }
        });

    }

    //Method to navigate user to Settings Page
    public void navigateToSettings(){
        Intent myIntent = new Intent(MenuActivity.this, SettingsActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to Learn Page
    public void navigateToLearn(){
        Intent myIntent = new Intent(MenuActivity.this, LearnActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to Quiz Page
    public void navigateToQuiz(){
        Intent myIntent = new Intent(MenuActivity.this, QuizActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to Medal Page
    public void navigateToMedal(){
        Intent myIntent = new Intent(MenuActivity.this, MedalActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to Notes Page
    public void navigateToNotes(){
        Intent myIntent = new Intent(MenuActivity.this, NotesActivity.class);
        startActivity(myIntent);
    }
    //Method to navigate user to Avatar Page
    public void navigateToAvatar(){
        Intent myIntent = new Intent(MenuActivity.this, MyAccount.class);
        startActivity(myIntent);
    }

    public void navigateToLeaderBoard(){
        Intent myIntent = new Intent(MenuActivity.this, LeaderBoardActivity.class);
        startActivity(myIntent);
    }
}