package org.fitnessapp.ui.leaderboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.fitnessapp.R;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Helper;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<Users> feedItemList;
    private Context mContext;

     LeaderboardAdapter( Context context, List<Users> feedItemList) {
        this.mContext = context;
        this.feedItemList = feedItemList;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_leaderboard, parent, false);
        return new LeaderboardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, final int position) {
        //holder.titleTextView.setText(mList[position]);
        final Users feedItem = feedItemList.get(position);

        holder.rankPosition.setText(String.valueOf(position+1));
        holder.username.setText(String.format(mContext.getString(R.string.leaderboard_username),feedItem.getUsername()));
        holder.distanceWalked.setText(String.format(mContext.getString(R.string.leaderboard_distance), Helper.meterToMileConverter(feedItem.totalDistanceWalked)));
        holder.timeWalked.setText(String.format(mContext.getString(R.string.leaderboard_time), Helper.secondToMinuteConverter(feedItem.totalTimeWalked)));

    }
    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username, distanceWalked, timeWalked, rankPosition;

         ViewHolder(View itemView) {
            super(itemView);
            username =  itemView.findViewById(R.id.text_view_username);
            distanceWalked =  itemView.findViewById(R.id.text_view_distance_walked);
            timeWalked =  itemView.findViewById(R.id.text_view_time_walked);
            rankPosition =  itemView.findViewById(R.id.text_view_rank_position);

            itemView.setTag(itemView);
        }
    }
}

