package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedalActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userUID;

    private ImageView gold;
    private ImageView silver;
    private ImageView bronze;
    private ImageView chest;
    private ImageView stack;
    private ImageView dime;
    private Button mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal);

        //Set Page Title
        setTitle("Achievements");

        gold = findViewById(R.id.gold);
        silver = findViewById(R.id.silver);
        bronze = findViewById(R.id.bronze);
        chest = findViewById(R.id.coinChest);
        stack = findViewById(R.id.coinStack);
        dime = findViewById(R.id.dime);
        mainMenu = findViewById(R.id.mainMenubtn);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainMenu();
            }
        });

        //Grey Out All Medals as Default
        gold.setColorFilter(Color.argb(155, 112, 112, 112));
        silver.setColorFilter(Color.argb(155, 112, 112, 112));
        bronze.setColorFilter(Color.argb(155, 112, 112, 112));
        chest.setColorFilter(Color.argb(155, 112, 112, 112));
        stack.setColorFilter(Color.argb(155, 112, 112, 112));
        dime.setColorFilter(Color.argb(155, 112, 112, 112));

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userUID = user.getUid();

        databaseReference.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                if (profile != null){
                    //Get User's Data from Firebase
                    boolean first = profile.first;
                    boolean second = profile.second;
                    boolean third = profile.third;
                    int points = profile.points;

                    //Check if the User has been in 1st Place
                    if (first == true){
                        gold.clearColorFilter();
                    }

                    //Check if the User has been in 2nd Place
                    if (second == true){
                        silver.clearColorFilter();
                    }

                    //Check if the User has been in 3rd Place
                    if (third == true){
                        bronze.clearColorFilter();
                    }

                    //Check how many points the user has and if its enough for this achievement
                    if (points >= 1){
                        dime.clearColorFilter();
                    }

                    //Check how many points the user has and if its enough for this achievement
                    if (points >= 500){
                        stack.clearColorFilter();
                    }

                    //Check how many points the user has and if its enough for this achievement
                    if (points >= 1000){
                        chest.clearColorFilter();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void toMainMenu(){
        Intent intent = new Intent(MedalActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}