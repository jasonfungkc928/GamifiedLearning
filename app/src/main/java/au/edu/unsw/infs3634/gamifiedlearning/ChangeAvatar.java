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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeAvatar extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userUID;

    private int newAvatar;
    private ImageView avatarChange;
    private Button confirm;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        //Set Page Title
        setTitle("Change Avatar");

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            newAvatar = extras.getInt("Image ID");

        }

        avatarChange = findViewById(R.id.avatarChange);
        confirm = findViewById(R.id.confirm);
        back = findViewById(R.id.backBtn);

        avatarChange.setImageResource(newAvatar);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmChange();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void confirmChange(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userUID = user.getUid();

        databaseReference.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                if (profile != null){
                    String fName = profile.firstName;
                    String lName = profile.lastName;
                    String email = profile.email;
                    int points = profile.points;
                    boolean first = profile.first;
                    boolean second = profile.second;
                    boolean third = profile.third;

                    User changeUser = new User(fName, lName, email, newAvatar, points, first, second, third);

                    FirebaseUser userRegistration = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(changeUser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ChangeAvatar.this, "Avatar Changed", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ChangeAvatar.this, MenuActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void goBack(){
        Intent intent = new Intent(ChangeAvatar.this, MyAccount.class);
        startActivity(intent);
    }
}