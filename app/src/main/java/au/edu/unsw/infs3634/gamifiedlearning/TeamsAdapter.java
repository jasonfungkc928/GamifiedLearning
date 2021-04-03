package au.edu.unsw.infs3634.gamifiedlearning;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.LearnViewHolder> {

    //Initialize variables
    private static final String TAG = "LearnAdapter";
    List<TeamDatum> mDatums;
    List<TeamDatum> mDatumsFiltered;
    RecyclerViewClickListener mListener;

    //Creating constructor
    public TeamsAdapter(List<TeamDatum> datums, RecyclerViewClickListener listener) {
        mDatums = datums;
        mDatumsFiltered = datums;
        mListener = listener;
    }
    //Filter method so that user can search the teams
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mDatumsFiltered = mDatums;
                } else {
                    ArrayList<TeamDatum> filteredList = new ArrayList<>();
                    for(TeamDatum datum : mDatums) {
                        if(datum.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(datum);
                        }
                    }
                    mDatumsFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDatumsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDatumsFiltered = (List<TeamDatum>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface RecyclerViewClickListener {
        void onClick(View view, Integer mId);
    }

    @NonNull
    @Override
    ////Inflating the team_list_row file, and return the view and listener back
    public LearnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_row, parent, false);
        Log.d(TAG, "onCreateViewHolder: RETURN LEARN VIEW HOLDER");
        return new LearnViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnViewHolder holder, int position) {
        TeamDatum datum = mDatumsFiltered.get(position);
        //Set text to textView
        holder.teamName.setText(datum.getName());
        holder.itemView.setTag(datum.getId());
        Log.d(TAG, "onBindViewHolder: ON BIND VIEW HOLDER");
    }

    @Override
    public int getItemCount() {
        return mDatumsFiltered.size();
    }
    //Creating the viewHolder class to handle the games_list_row xml
    public static class LearnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Declare the views from the team_list_row xml
        public TextView teamName;
        private RecyclerViewClickListener listener;
        //Constructor
        public LearnViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            //Set a listener to the view
            this.listener = listener;
            itemView.setOnClickListener(this);
            //Initialise views
            teamName = itemView.findViewById(R.id.tvTeamName);
            Log.d(TAG, "LearnViewHolder: LEARN VIEW HOLDER");
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (Integer) v.getTag());
        }
    }

    //Setting the data to the list
    public void setData(List<TeamDatum> data) {
        mDatumsFiltered.clear();
        mDatumsFiltered.addAll(data);
        notifyDataSetChanged();
    }
}