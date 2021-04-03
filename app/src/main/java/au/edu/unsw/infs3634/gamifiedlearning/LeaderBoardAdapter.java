package au.edu.unsw.infs3634.gamifiedlearning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderBoardAdapter extends FirebaseRecyclerAdapter<User, LeaderBoardAdapter.myViewHolder>{

    List<User> mUser;
    public static final int SORT_METHOD_SCORE = 1;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LeaderBoardAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull User user) {
        myViewHolder.name.setText(user.getFirstName() + " " + user.getLastName());
        myViewHolder.score.setText(String.valueOf(user.getPoints()));
        Glide.with(myViewHolder.img.getContext()).load(user.getAvatar()).into(myViewHolder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
       return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name, score;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nameText);
            score = (TextView) itemView.findViewById(R.id.scoreText);

        }
    }
    
}

