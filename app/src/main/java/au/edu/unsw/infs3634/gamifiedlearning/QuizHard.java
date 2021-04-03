package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class QuizHard extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_hard);

        setTitle("Hard");

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userUID = user.getUid();

        databaseReference.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                if (profile != null) {
                    String fName = profile.firstName;
                    String lName = profile.lastName;
                    String email = profile.email;
                    int avatar = profile.avatar;
                    int points = profile.points;
                    boolean first = profile.first;
                    boolean second = profile.second;
                    boolean third = profile.third;

                    int newPoints = points + 100;

                    User changeUser = new User(fName, lName, email, avatar, newPoints, first, second, third);

                    FirebaseUser userRegistration = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(changeUser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(QuizHard.this, "Point Total = " + newPoints, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(QuizHard.this, MenuActivity.class);
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
}