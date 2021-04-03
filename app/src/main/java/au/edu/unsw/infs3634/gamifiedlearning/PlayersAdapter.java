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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.LearnViewHolder> implements Filterable {

    //Initialize variables
    private static final String TAG = "LearnAdapter";
    public static final int SORT_METHOD_POSITION = 1;
    public static final int SORT_METHOD_DESCENDING = 2;
    public static final int SORT_METHOD_ASCENDING = 3;
    public static final int SORT_METHOD_TEAMS = 4;
     List<Datum> mDatums;
     List<Datum> mDatumsFiltered;
     RecyclerViewClickListener mListener;

     //Creating constructor
    public PlayersAdapter(List<Datum> datums, RecyclerViewClickListener listener) {
        mDatums = datums;
        mDatumsFiltered = datums;
        mListener = listener;
    }
    //Filtering method and let user search players based on last name and first name
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mDatumsFiltered = mDatums;
                } else {
                    ArrayList<Datum> filteredList = new ArrayList<>();
                    for(Datum datum : mDatums) {
                        if(datum.getLastName().toLowerCase().contains(charString.toLowerCase()) || datum.getFirstName().toLowerCase().contains(charString.toLowerCase())) {
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
                mDatumsFiltered = (List<Datum>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, Integer mId);
    }

    @NonNull
    @Override
    //Inflating the learn1_list_row file, and return the view and listener back
    public LearnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn1_list_row, parent, false);
        Log.d(TAG, "onCreateViewHolder: RETURN LEARN VIEW HOLDER");
        return new LearnViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnViewHolder holder, int position) {
        Datum datum = mDatumsFiltered.get(position);
        //Set text for textView
        holder.playerName.setText(datum.getFirstName() + " " + datum.getLastName());
        holder.position.setText(datum.getPosition());
        holder.team.setText(datum.getTeam().getFullName());
        holder.itemView.setTag(datum.getId());
        Log.d(TAG, "onBindViewHolder: ON BIND VIEW HOLDER");
    }

    @Override
    public int getItemCount() {
        return mDatumsFiltered.size();
    }
    //Creating the viewHolder class to handle the games_list_row xml
    public static class LearnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Declare the views from the learn1_list_row xml
        public TextView playerName, position, team;
        private RecyclerViewClickListener listener;
        //Constructor
        public LearnViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            //Set a listener to the view
            this.listener = listener;
            itemView.setOnClickListener(this);
            //initialise views
            playerName = itemView.findViewById(R.id.tvPlayerName);
            position = itemView.findViewById(R.id.tvFullName);
            team = itemView.findViewById(R.id.tvTeams);
            Log.d(TAG, "LearnViewHolder: LEARN VIEW HOLDER");
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (Integer) v.getTag());
        }
    }
    //Add all the data to the list and notify data set changed
    public void setData(List<Datum> data) {
        mDatumsFiltered.addAll(data);
        notifyDataSetChanged();
    }

    //Sort method with different scenario: sort by position, ascending order, descending order and teams
    public void sort(final int sortMethod) {
        if (mDatumsFiltered.size() > 0) {
            Collections.sort(mDatumsFiltered, new Comparator<Datum>() {
                @Override
                public int compare(Datum o1, Datum o2) {
                    if(sortMethod == SORT_METHOD_POSITION) {
                        return o2.getPosition().compareTo(o1.getPosition());
                    }
                    if(sortMethod == SORT_METHOD_DESCENDING){
                        return o2.getFirstName().compareTo(o1.getFirstName());
                    }
                    if(sortMethod == SORT_METHOD_ASCENDING){
                        return o1.getFirstName().compareTo(o2.getFirstName());
                    }
                    if(sortMethod ==SORT_METHOD_TEAMS){
                        return o1.getTeam().getFullName().compareTo(o2.getTeam().getFullName());
                    }
                    return o2.getPosition().compareTo(o1.getPosition());
                }
            });
        }
        notifyDataSetChanged();
    }

}
