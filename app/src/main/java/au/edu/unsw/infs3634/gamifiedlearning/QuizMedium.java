package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizMedium extends AppCompatActivity {

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
    public int mark = 0;
    private int culMark = 0;
    private int qCount = 0;
    public static String MARK_MESSAGE = "";
    public static String Q_MESSAGE = "";

    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_medium);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            culMark = Integer.valueOf(bundle.getString(MARK_MESSAGE));
            qCount = Integer.valueOf(bundle.getString(Q_MESSAGE));

        }

        setTitle("Medium");

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
        int[] sample1 = generateRandom();
        int[] sample2 = generateRandom();
        int[] sample3 = generateRandom();

        populateQuestion(correct[0], correct[1], correct[2]);
        populateO2(sample1[1],sample1[2], correct[0]);
        populateO3(sample2[1],sample2[2], correct[0]);
        populateO4(sample3[1],sample3[2], correct[0]);

        answer1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String bruh = ans.getText().toString();
                String selection = option1.getText().toString();
                System.out.println("bruh is "+bruh);
                System.out.println("chosen is "+selection);
//                if (selection.equals(bruh)) {
//                    mark = 1;
//                } else {
//                    mark = 0;
//                }
                qCount++;
                culMark = culMark + mark;
                Bundle bundle = new Bundle();
                bundle.putString(MARK_MESSAGE, String.valueOf(culMark));
                bundle.putString(Q_MESSAGE, String.valueOf(qCount));
                Intent intent = new Intent(QuizMedium.this, QuizMedium.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        answer2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                if (option2.getText().equals(ans.getText())) {
//                    mark++;
//                }
            }
        });
        answer3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                if (option3.getText().equals(ans.getText())) {
//                    mark++;
//                }
            }
        });
        answer4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                if (option4.getText().equals(ans.getText())) {
//                    mark++;
//                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mark==1) {
                    System.out.println("correct");
                }
                System.out.println("qcount is "+qCount);
                System.out.println("culmark is "+culMark);
            }
        });

    }

    public int[] generateRandom() {
        int max = 3;
        int pMax = 23;
        int pMin = 0;
        int min = 1;
        int iMin = 1;
        int iMax = 2;
        int question = (int)Math.round((Math.random() * ((max - min) + 1)) + min);
        int index = (int)Math.round((Math.random() * ((pMax - pMin) + 1)) + pMin);
        int page = (int)Math.round((Math.random() * ((iMax - iMin) + 1)) + pMin);
        int[] rand = {question, index, page};
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

    public void fromApi(int y, int i, int q) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LearnService service = retrofit.create(LearnService.class);
        Log.d(TAG, "o1 list index is "+y);
        Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
        playerResponseCall.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> playerResponse) {
                List<Datum> datums = playerResponse.body().getData();
                Datum z = datums.get(y);
                int cID = z.getId();
                int j =1;
                Log.d(TAG, "o1 comparator ID is "+ cID);
                for (final Datum datum : datums) {
                    if (datum.getId() == z.getId()) {
                            pPlayer.setText(datum.getFirstName() + " " + datum.getLastName());
                            pPlayer.setVisibility(View.VISIBLE);
                        String check = "";
                        String option = "o1";
                        switch(q){
                            case 1:
                                String height = datum.getHeightFeet() + "'" + datum.getHeightInches();
                                if(datum.getHeightFeet()== null) {
                                    check = "wrong";
                                }else {
                                    check = reselect(datum.getHeightFeet().toString(), option);
                                } textSetter(j, check);
                                break;
                            case 2:
                                String weight  = datum.getWeightPounds()+"";

                                if(datum.getWeightPounds()==null){
                                    check = reselect("null", option);}
                                else {
                                    check = reselect(weight, option);}
                                textSetter(j, check);
                                break;
                            case 3:
                                String pos = datum.getPosition();
                                check = reselect(pos, option);
                                textSetter(j, check);
                                break;
                            case 4:
                                String team = datum.getTeam().getFullName();
                                check = reselect(team, option);
                                textSetter(j, check);
                                break;
                            default:
                                Log.d(TAG, " o1 uh oh");
                        }
                    } else { Log.d(TAG, "o1 Something fked up"); }
                } Log.d(TAG, ans.getText().toString());
            }
            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
            }
        });

    }

    public String reselect(String check, String option) {
        if(check.equals("null")) {
            Log.d(TAG, option+ " select a new number my man");
            return "null value";
        } else
            Log.d(TAG, option + " all g bruvva its "+check);
        return check;
    }

    public void textSetter(int i , String answer) {
        switch(i) {
            case 1:
                System.out.println("Option1 Filled");
                option1.setText(answer);
                if(answer.equals("wrong")) {
                    ans.setText("boke");
                }
                break;
            case 2:
                System.out.println("Option2 Filled");
                option2.setText(answer);
                break;
            case 3:
                option3.setText(answer);
                break;
            case 4:
                option4.setText(answer);
                break;
        }
    }

    public void populateO2(int y, int i, int q) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LearnService service = retrofit.create(LearnService.class);
        Log.d(TAG, "o2 list index is "+y);
        Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
        playerResponseCall.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> playerResponse) {
                List<Datum> datums = playerResponse.body().getData();
                Datum z = datums.get(y);
                int cID = z.getId();
                int j = 2;
                Log.d(TAG, "o2 comparator ID is "+ cID);
                String option = "o2";
                for (final Datum datum : datums) {
                    if (datum.getId() == z.getId()) {

                        String check = "";

                        switch(q){
                            case 1:
                                String height = datum.getHeightFeet() + "'" + datum.getHeightInches();
                                if(datum.getHeightFeet()== null) {
                                    check = reselect("null", option);
                                }else {
                                    check = reselect(datum.getHeightFeet().toString(), option);
                                }
                                textSetter(j, check);
                                break;
                            case 2:
                                String weight  = datum.getWeightPounds()+"";

                                if(datum.getWeightPounds()==null){
                                    check = reselect("null", option);}
                                else {
                                    check = reselect(weight, option);}

                                textSetter(j, check);
                                break;
                            case 3:
                                String pos = datum.getPosition();
                                check = reselect(pos, option);

                                textSetter(j, check);
                                break;
                            case 4:
                                String team = datum.getTeam().getFullName();
                                check = reselect(team, option);

                                textSetter(j, check);
                                break;
                            default:
                                Log.d(TAG, option +" uh oh");
                        }
                    } else { Log.d(TAG, option + " Something fked up"); }
                } Log.d(TAG, option2.getText().toString());
            }
            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
            }
        });

    }

    public void populateO3(int y, int i, int q) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LearnService service = retrofit.create(LearnService.class);
        Log.d(TAG, "o3 list index is "+y);
        Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
        playerResponseCall.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> playerResponse) {
                List<Datum> datums = playerResponse.body().getData();
                Datum z = datums.get(y);
                int cID = z.getId();
                int j = 3;
                Log.d(TAG, "o3 comparator ID is "+ cID);
                String option = "o3";
                for (final Datum datum : datums) {
                    if (datum.getId() == z.getId()) {

                        String check = "";

                        switch(q){
                            case 1:
                                String height = datum.getHeightFeet() + "'" + datum.getHeightInches();
                                if(datum.getHeightFeet()== null) {
                                    check = reselect("null", option);
                                }else {
                                    check = reselect(datum.getHeightFeet().toString(), option);
                                }
                                textSetter(j, check);
                                break;
                            case 2:
                                String weight  = datum.getWeightPounds()+"";

                                if(datum.getWeightPounds()==null){
                                    check = reselect("null", option);}
                                else {
                                    check = reselect(weight, option);}

                                textSetter(j, check);
                                break;
                            case 3:
                                String pos = datum.getPosition();
                                check = reselect(pos, option);

                                textSetter(j, check);
                                break;
                            case 4:
                                String team = datum.getTeam().getFullName();
                                check = reselect(team, option);

                                textSetter(j, check);
                                break;
                            default:
                                Log.d(TAG, option +" uh oh");
                        }
                    } else { Log.d(TAG, option + " Something fked up"); }
                } Log.d(TAG, option2.getText().toString());
            }
            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
            }
        });

    }
    public void populateO4(int y, int i, int q) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.balldontlie.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LearnService service = retrofit.create(LearnService.class);
        Log.d(TAG, "o4 list index is "+y);
        Call<PlayerResponse> playerResponseCall = service.getPlayerResponse(String.valueOf(i));
        playerResponseCall.enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> playerResponse) {
                List<Datum> datums = playerResponse.body().getData();
                Datum z = datums.get(y);
                int cID = z.getId();
                int j = 4;
                Log.d(TAG, "o4 comparator ID is "+ cID);
                String option = "o4";
                for (final Datum datum : datums) {
                    if (datum.getId() == z.getId()) {

                        String check = "";

                        switch(q){
                            case 1:
                                String height = datum.getHeightFeet() + "'" + datum.getHeightInches();
                                if(datum.getHeightFeet()== null) {
                                    check = reselect("null", option);
                                }else {
                                    check = reselect(datum.getHeightFeet().toString(), option);
                                }
                                textSetter(j, check);
                                break;
                            case 2:
                                String weight  = datum.getWeightPounds()+"";

                                if(datum.getWeightPounds()==null){
                                    check = reselect("null", option);}
                                else {
                                    check = reselect(weight, option);}

                                textSetter(j, check);
                                break;
                            case 3:
                                String pos = datum.getPosition();
                                check = reselect(pos, option);

                                textSetter(j, check);
                                break;
                            case 4:
                                String team = datum.getTeam().getFullName();
                                check = reselect(team, option);

                                textSetter(j, check);
                                break;
                            default:
                                Log.d(TAG, option +" uh oh");
                        }
                    } else { Log.d(TAG, option + " Something fked up"); }
                } Log.d(TAG, option2.getText().toString());
            }
            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
            }
        });

    }
}