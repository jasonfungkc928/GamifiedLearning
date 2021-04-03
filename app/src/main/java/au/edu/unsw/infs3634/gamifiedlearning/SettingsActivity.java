package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private Button btnChangePersonalDetails;
    private Button btnChangeAccountDetails;
    private Button btnResetProgress;
    private Button btnMainMenu;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Set Page Title
        setTitle("Settings");

        btnChangePersonalDetails = findViewById(R.id.btnChangePersonalDetails);
        btnChangeAccountDetails = findViewById(R.id.btnChangeAccountDetails);
        btnResetProgress = findViewById(R.id.btnResetProgress);
        btnMainMenu = findViewById(R.id.btnMainMenu);
        logout = (Button) findViewById(R.id.logout);

        btnChangeAccountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToChangeAccountDetails();
            }
        });
        btnChangePersonalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToChangePersonalDetails();
            }
        });
        btnResetProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToResetProgress();
            }
        });
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainMenu();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent signout = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(signout);
            }
        });
    }

    public void navigateToChangeAccountDetails(){
        Intent myAccount = new Intent(SettingsActivity.this, MyAccount.class);
        startActivity(myAccount);
    }

    public void navigateToChangePersonalDetails(){

    }

    public void navigateToResetProgress(){

    }

    public void navigateToMainMenu(){
        Intent myIntent = new Intent(SettingsActivity.this, MenuActivity.class);
        startActivity(myIntent);
    }
}