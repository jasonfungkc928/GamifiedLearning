package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizEasy extends AppCompatActivity {

    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private Button nextBtn;
    private TextView question;
    private TextView pPlayer;
    private TextView ans;
    private TextView option1;
    private TextView option2;
    private TextView option3;
    private TextView option4;
    public String angy = "";

    private static final String TAG = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_easy);

        setTitle("Easy");

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        nextBtn = findViewById(R.id.nextBtn);
        question = findViewById(R.id.question);
        pPlayer = findViewById(R.id.pPlayer);
        ans = findViewById(R.id.ans);
        ans.setVisibility(View.VISIBLE);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);


        int[] correct = generateRandom();

        populateQuestion(correct[0], correct[1], correct[2]);

        populateAnswer(generateRandom()[1], generateRandom()[2], correct[0]);







    }

    public int[] generateRandom() {
        int max = 3;
        int pMax = 24;
        int pMin = -1;
        int min = 1;
        int iMin = 1;
        int iMax = 2;
        int x = (int)Math.round((Math.random() * ((max - min) + 1)) + min);
        int y = (int)Math.round((Math.random() * ((pMax - pMin) + 1)) + pMin);
        int z = (int)Math.round((Math.random() * ((iMax - iMin) + 1)) + pMin);
        int[] rand = {x, y, z};
        return rand;

    }

    public void populateQuestion(int x, int y, int i) {
        pPlayer.setVisibility(View.INVISIBLE);
        fromApi(y, i, x);
        ArrayList<QuestionBank> questions = QuestionBank.getQuestions();
        Log.d(TAG, "q is "+x);
        for (final QuestionBank questionBank : questions) {
            if (questionBank.getQId() == x) {
                question.setText(questionBank.getQuestion());
            }
        }


    }


    public void markQuiz() {

    }
    public void next() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });
    }

    public void fromApi(int y, int i, int q) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LearnService service = retrofit.create(LearnService.class);
        Log.d(TAG, "list index is "+y);
        Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
        playerResponseCall.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> playerResponse) {
                List<Datum> datums = playerResponse.body().getData();
                Datum z = datums.get(y);
                int cID = z.getId();
                Log.d(TAG, "comparator ID is "+ cID);
                for (final Datum datum : datums) {
                    if (datum.getId() == z.getId()) {
                        pPlayer.setText(datum.getFirstName() + " " + datum.getLastName());
                        pPlayer.setVisibility(View.VISIBLE);
                        String check = "";
                        switch(q){
                            case 1:
                                String height = datum.getHeightFeet() + "'" + datum.getHeightInches();
                                if(datum.getHeightFeet()== null) {
                                    check = reselect("null");
                                }else {
                                    check = reselect(datum.getHeightFeet().toString());
                                } ans.setText(check);

                                break;
                            case 2:
                                String weight  = datum.getWeightPounds()+"";

                                        if(datum.getWeightPounds()==null){
                                        check = reselect("null");}
                                        else {
                                            check = reselect(weight);}
                                        ans.setText(check);

                                break;
                            case 3:
                                String pos = datum.getPosition();
                                check = reselect(pos);
                                ans.setText(check);

                                break;
                            case 4:
                                String team = datum.getTeam().getFullName();
                                check = reselect(team);
                                ans.setText(team);

                                break;
                            default:
                                Log.d(TAG, "uh oh");
                        }
                    } else { Log.d(TAG, "Something fked up"); }
                } Log.d(TAG, ans.getText().toString());
            }
            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
            }
        });

    }

    public String reselect(String check) {
        if(check.equals("null")) {
            Log.d(TAG, "select a new number my man");
            return "and i oop";
        } else
            Log.d(TAG, "all g bruvva its "+check);
        return check;
    }

    public String generate (int y, int i, int q) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.balldontlie.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        LearnService service = retrofit.create(LearnService.class);
        Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
        playerResponseCall.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> playerResponse) {
                List<Datum> datums = playerResponse.body().getData();
                Datum z = datums.get(y);
                int cID = z.getId();
                Log.d(TAG, "alt answer is"+cID);
                for (final Datum datum : datums) {
                    if (datum.getId() == z.getId()) {
                        switch(q){
                            case 1:
                                String height = datum.getHeightFeet() + "'" + datum.getHeightInches();
                                String check;
                                if(datum.getHeightFeet()== null) {
                                    check = reselect("null");
                                }else {
                                    check = reselect(datum.getHeightFeet().toString());
                                }
                                option1.setText(check);
                                break;
                            case 2:
                                String weight  = datum.getWeightPounds()+"";
                                String check1;
                                if(datum.getWeightPounds()==null){
                                    check1 = reselect("null");}
                                else {
                                    check1 = reselect(weight);}
                                option1.setText(check1);
                                break;
                            case 3:
                                String pos = datum.getPosition();
                                String check2 = reselect(pos);
                                option1.setText(check2);
                                break;
                            case 4:
                                String team = datum.getTeam().getFullName();
                                String check3 = reselect(team);
                                option1.setText(team);
                                break;
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
            }
        });
        return option1.getText().toString();
    }

    public void populateAnswer(int qNo, int index, int page){
        String a1 = "a1";//generate(index, page, qNo);
        String a2 = "a2";//generate(index, page, qNo);
        String a3 = "a3";//generate(index, page, qNo);
        Log.d(TAG, "inserting answers on buttons");
        String a4 = ans.getText().toString();
        String[] answers = {a1, a2, a3, a4};
        for(int j = 0; j<3; j++) {
            answers[j] = generate(index, page, qNo);
            Log.d(TAG, j+ "option"+answers[j]);
        }
        List<String> answerList = Arrays.asList(answers);
        Collections.shuffle(answerList);
        answerList.toArray(answers);
        option1.setText(answers[0].toString());
        option2.setText(answers[1].toString());
        option3.setText(answers[2].toString());
        option4.setText(answers[3].toString());


    }


}