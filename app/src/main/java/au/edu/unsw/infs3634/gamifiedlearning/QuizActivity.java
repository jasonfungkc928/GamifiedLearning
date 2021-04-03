package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends AppCompatActivity {

    private Button easyBtn;
    private Button mediumBtn;
    private Button hardBtn;
    private Button rtnMainMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setTitle("Quiz - Difficulty Selection");

        easyBtn = findViewById(R.id.easyBtn);
        mediumBtn = findViewById(R.id.mediumBtn);
        hardBtn = findViewById(R.id.hardBtn);
        rtnMainMenu = findViewById(R.id.rtnMainMenu);

        easyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                navigateToEasy();
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                navigateToMedium();
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                navigateToHard();
            }
        });

        rtnMainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                navigateToMainMenu();
            }
        });


    }

    //Select quiz difficulty
    public void navigateToEasy() {
        Intent myIntent = new Intent(QuizActivity.this, QuizEasy.class);
        startActivity(myIntent);
    }

    public void navigateToMedium() {
        Intent myIntent = new Intent(QuizActivity.this, QuizMedium.class);
        startActivity(myIntent);
    }

    public void navigateToHard() {
        Intent myIntent = new Intent(QuizActivity.this, QuizHard.class);
        startActivity(myIntent);
    }

    public void navigateToMainMenu() {
        Intent myIntent = new Intent(QuizActivity.this, MenuActivity.class);
        startActivity(myIntent);
    }
}