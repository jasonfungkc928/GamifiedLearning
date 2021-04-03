package au.edu.unsw.infs3634.gamifiedlearning;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    //Initialize variables
    private List<NotesData> dataList;
    private Activity context;
    private RoomDB database;

    //Create constructor
    public NotesAdapter(Activity context, List<NotesData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_notes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize notes data
        NotesData data = dataList.get(position);
        //Initialize database
        database = RoomDB.getInstance(context);
        //Set text on text view
        holder.textView.setText(data.getText());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize notes data
                NotesData d = dataList.get(holder.getAdapterPosition());
                //Get id
                int sID = d.getID();
                //Get text
                String sText = d.getText();

                //Create dialog
                Dialog dialog = new Dialog(context);
                //Set content view
                dialog.setContentView(R.layout.dialog_update);
                //Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width,height);
                //Show dialog
                dialog.show();

                //Initialize and assign variables
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);
                //Set text on edit text
                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();
                        //Get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        //Update text in database
                        database.notesDao().update(sID,uText);
                        //Notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.notesDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize notes data
                NotesData d = dataList.get(holder.getAdapterPosition());
                //Delete text from database
                database.notesDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            //Initialize variable
            super(itemView);
            //Assign variable
            textView = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);

        }
    }
}