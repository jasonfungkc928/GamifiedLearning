package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public URI uri;

    private EditText enterFirstName;
    private EditText enterLastName;
    private EditText enterEmail;
    private EditText enterPassword;
    private EditText confirmPassword;
    private Button submitDetails;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        //Set Page Title
        setTitle("Sign Up");

        enterFirstName = (EditText) findViewById(R.id.enterFName);
        enterLastName = (EditText) findViewById(R.id.enterLName);
        enterEmail = (EditText) findViewById(R.id.enterEmail);
        enterPassword = (EditText) findViewById(R.id.enterPassword);
        confirmPassword = (EditText) findViewById(R.id.enterPassword2);
        submitDetails = (Button) findViewById(R.id.submitDetails);
        progressBar = (ProgressBar) findViewById(R.id.detailsProgressBar);

        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpDetails();
            }
        });
    }



    private void signUpDetails(){

        //Set Default Avatar
        int avatar = R.drawable.no_avatar;

        //Set Default Points
        int points = 0;

        //Set Default Place
        boolean first = false;
        boolean second = false;
        boolean third = false;

        //Get inputs
        String firstName = enterFirstName.getText().toString().trim().toLowerCase();
        String lastName = enterLastName.getText().toString().trim().toLowerCase();
        String email = enterEmail.getText().toString().trim().toLowerCase();
        String password = enterPassword.getText().toString().trim();
        String password2 = confirmPassword.getText().toString().trim();

        //Check that First Name was Provided
        if (firstName.isEmpty()){
            enterFirstName.setError("First Name is Required");
            enterFirstName.requestFocus();
            return;
        }

        //Check that Last Name was Provided
        if (lastName.isEmpty()){
            enterLastName.setError("Last Name is Required");
            enterLastName.requestFocus();
            return;
        }

        //Check that Email Address was Provided
        if (email.isEmpty()){
            enterEmail.setError("Email Address is Required");
            enterEmail.requestFocus();
            return;
        }

        //Check that Valid Email Address was Provided
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            enterEmail.setError("Please Enter a Valid Email Address");
            enterEmail.requestFocus();
            return;
        }

        //Check that Password was Provided
        if (password.isEmpty()){
            enterPassword.setError("Password is Required");
            enterPassword.requestFocus();
            return;
        }

        //Check Password Length
        if (password.length() < 8){
            enterPassword.setError("Password Must be at Least 8 Characters");
            enterPassword.requestFocus();
            return;
        }

        //Check that Confirmation Password was Provided
        if (password2.isEmpty()){
            confirmPassword.setError("Enter Password Again to Confirm");
            confirmPassword.requestFocus();
            return;
        }

        //Check that passwords match
        if (!password.equals(password2)){
            confirmPassword.setError("Passwords Do Not Match");
            confirmPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(firstName, lastName, email, avatar, points, first, second, third);

                            FirebaseUser userRegistration = FirebaseAuth.getInstance().getCurrentUser();
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        userRegistration.sendEmailVerification();
                                        Toast.makeText(SignUpActivity.this, "Registration Successful!!!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        //Direct User to Login Page
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Registration Failed! Try Again!!!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(SignUpActivity.this, "Registration Failed! Try Again!!!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}