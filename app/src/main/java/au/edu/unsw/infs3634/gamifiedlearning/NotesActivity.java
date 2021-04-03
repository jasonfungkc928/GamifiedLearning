package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    //Initialise variable
    private EditText editText;
    private Button btnAdd, btnReset;
    private RecyclerView recyclerView;

    List<NotesData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //Assign variable
        editText = findViewById(R.id.edit_text);
        btnAdd = findViewById(R.id.btnAdd);
        btnReset = findViewById(R.id.btnReset);
        recyclerView = findViewById(R.id.recycler_view);

        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.notesDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize Adapter
        adapter = new NotesAdapter(NotesActivity.this,dataList);
        //Set adapter
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get string from edit text
                String sText = editText.getText().toString().trim();
                //Check condition
                if (!sText.equals("")){
                    //When text is not empty
                    //Initialize notes data
                    NotesData data = new NotesData();
                    //Set text on notes data
                    data.setText(sText);
                    //Insert text in database
                    database.notesDao().insert(data);
                    //Clear edit text
                    editText.setText("");
                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.notesDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete all data from database
                database.notesDao().reset(dataList);
                //Notify when all data deleted
                dataList.clear();
                dataList.addAll(database.notesDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });

    }
}