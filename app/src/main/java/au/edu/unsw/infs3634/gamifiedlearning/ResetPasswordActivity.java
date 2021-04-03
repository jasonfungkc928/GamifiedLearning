package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText accountEmail;
    private Button resetPasswordBtn;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Set Page Title
        setTitle("Forgot Password");

        accountEmail = (EditText) findViewById(R.id.accountEmail);
        resetPasswordBtn = (Button) findViewById(R.id.resetPasswordBtn);
        progressBar = (ProgressBar) findViewById(R.id.resetPasswordProgressBar);

        mAuth = FirebaseAuth.getInstance();

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    private void forgotPassword(){
        //Get Inputs
        String email = accountEmail.getText().toString().trim().toLowerCase();

        //Check for Errors
        //Check that an Email was Provided
        if (email.isEmpty()){
            accountEmail.setError("An Email is Required");
            accountEmail.requestFocus();
            return;
        }

        //Check that a Valid Email was Provided
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            accountEmail.setError("A Valid Email is Required");
            accountEmail.requestFocus();
            return;
        }

        //Show progress bar as processing begins
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //once processing is complete hide progress bar
                    progressBar.setVisibility(View.GONE);

                    //provide user feedback & instructions
                    Toast.makeText(ResetPasswordActivity.this, "Password Reset Email Sent", Toast.LENGTH_LONG).show();

                    //Redirect User to Login Screen
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    //once processing is complete hide progress bar
                    progressBar.setVisibility(View.GONE);

                    //provide user with error message
                    Toast.makeText(ResetPasswordActivity.this, "An Error Has Occurred Try Again!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}