package org.fitnessapp.ui.loaderboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.fitnessapp.R;
import org.fitnessapp.data.db.model.Users;
import org.fitnessapp.util.Helper;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<Users> feedItemList;
    private Context mContext;

    public LeaderboardAdapter( Context context, List<Users> feedItemList) {
        this.mContext = context;
        this.feedItemList = feedItemList;
    }



    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_leaderboard, parent, false);
        return new LeaderboardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaderboardAdapter.ViewHolder holder, final int position) {
        //holder.titleTextView.setText(mList[position]);
        final Users feedItem = feedItemList.get(position);

        holder.username.setText(feedItem.getUsername());
        holder.distance_walked.setText(String.format(mContext.getString(R.string.daily_distance), Helper.meterToMileConverter(feedItem.totalDistanceWalked)));
        holder.time_walked.setText(String.format(mContext.getString(R.string.daily_time_data), Helper.secondToMinuteConverter(feedItem.totalTimeWalked)));

    }
    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username,distance_walked,time_walked;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.text_view_username);
            distance_walked = (TextView) itemView.findViewById(R.id.text_view_distance_walked);
            time_walked = (TextView) itemView.findViewById(R.id.text_view_time_walked);

            itemView.setTag(itemView);
        }
    }
}

