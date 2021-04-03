package au.edu.unsw.infs3634.gamifiedlearning;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.LearnViewHolder> {
    //Initialize variables
    private static final String TAG = "GamesAdapter";
    List<GamesDatum> mDatums;
    List<GamesDatum> mDatumsFiltered;
    GamesAdapter.RecyclerViewClickListener mListener;

    //Creating constructor
    public GamesAdapter(List<GamesDatum> datums, GamesAdapter.RecyclerViewClickListener listener) {
        mDatums = datums;
        mDatumsFiltered = datums;
        mListener = listener;
    }
    //Filter method, can search different players based on their first name or last name
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mDatumsFiltered = mDatums;
                } else {
                    ArrayList<GamesDatum> filteredList = new ArrayList<>();
                    for(GamesDatum datum : mDatums) {
                        if(datum.getPlayer().getFirstName().toLowerCase().contains(charString.toLowerCase()) || datum.getPlayer().getLastName().toLowerCase().contains(charString.toLowerCase())) {
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
                mDatumsFiltered = (List<GamesDatum>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, Integer mId);
    }

    @NonNull
    @Override
    //Inflating the games_list_row file, and return the view and listener back
    public GamesAdapter.LearnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_list_row, parent, false);
        Log.d(TAG, "onCreateViewHolder: RETURN LEARN VIEW HOLDER");
        return new GamesAdapter.LearnViewHolder(v, mListener);
    }

    @Override
    //Set text for textView
    public void onBindViewHolder(@NonNull GamesAdapter.LearnViewHolder holder, int position) {
        GamesDatum datum = mDatumsFiltered.get(position);
        holder.gamesName.setText(datum.getPlayer().getFirstName() + " " + datum.getPlayer().getLastName());
        holder.itemView.setTag(datum.getId());
        Log.d(TAG, "onBindViewHolder: ON BIND VIEW HOLDER");
    }

    @Override
    public int getItemCount() {
        return mDatumsFiltered.size();
    }

    //Creating the viewHolder class to handle the games_list_row xml
    public static class LearnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Declare the views from the games_list_row xml
        public TextView gamesName;
        private GamesAdapter.RecyclerViewClickListener listener;

        //Constructor
        public LearnViewHolder(@NonNull View itemView, GamesAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            //set a listener to the view
            itemView.setOnClickListener(this);
            //initialise views
            gamesName = itemView.findViewById(R.id.tvGamesName);
            Log.d(TAG, "LearnViewHolder: LEARN VIEW HOLDER");
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (Integer) v.getTag());
        }
    }

    public void setData(List<GamesDatum> data) {
//        mDatumsFiltered.clear();
        mDatumsFiltered.addAll(data);
        notifyDataSetChanged();
    }
}
