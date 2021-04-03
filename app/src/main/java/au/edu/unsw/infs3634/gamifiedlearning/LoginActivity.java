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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private TextView forgotPassword;
    private Button loginButton;
    private ProgressBar loginProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set Page Title
        setTitle("Login");

        mAuth = FirebaseAuth.getInstance();

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        loginProgress = (ProgressBar) findViewById(R.id.loginProgressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginVerification();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void loginVerification(){

        //Get inputs
        String email = loginEmail.getText().toString().toLowerCase().trim();
        String password = loginPassword.getText().toString().trim();

        //Check that Email was Provided
        if (email.isEmpty()){
            loginEmail.setError("An Email is Required");
            loginEmail.requestFocus();
            return;
        }

        //Check that a Valid Email was Provided
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.setError("A Valid Email is Required");
            loginEmail.requestFocus();
            return;
        }

        //Check that Password was Provided
        if (password.isEmpty()){
            loginPassword.setError("A Password is Required");
            loginPassword.requestFocus();
            return;
        }

        //once checks have been completed and processing begins - show progress bar
        loginProgress.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser loginUser = FirebaseAuth.getInstance().getCurrentUser();

                    //Once processing complete hide the progress bar
                    loginProgress.setVisibility(View.GONE);

                    //Check if the user's email has been verified
                    if (loginUser.isEmailVerified()){
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    } else{
                        loginUser.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Please Verify Email", Toast.LENGTH_LONG).show();
                    }
                } else{
                    //once processing has been completed hide the progress bar
                    loginProgress.setVisibility(View.GONE);

                    //display error message
                    Toast.makeText(LoginActivity.this, "Login Failed!!! Try Again!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Direct User to Forgot Password Page
    private void resetPassword(){
        Intent forgotPassword = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(forgotPassword);
    }
}