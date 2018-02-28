package com.example.mostafa.premireLeague;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mostafa on 2/16/2018.
 */

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.MyViewHolder> {

    final private ListItemClickListener mOnClickListener;
    private ArrayList<MatchData> nex_matches = new ArrayList<MatchData>();
    private Context context;


    public FirstAdapter(ArrayList<MatchData> nex_matches, Context context, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.nex_matches = nex_matches;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.match;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.imageView1.setImageResource(Teams.getImages().get(Integer.valueOf(nex_matches.get(position).team_home) - 1));
        holder.imageView2.setImageResource(Teams.getImages().get(Integer.valueOf(nex_matches.get(position).team_away) - 1));
        holder.textView1.setText(Teams.getNames().get(Integer.valueOf(nex_matches.get(position).team_home) - 1));
        holder.textView2.setText(Teams.getNames().get(Integer.valueOf(nex_matches.get(position).team_away) - 1));
        holder.textView3.setText(nex_matches.get(position).date);
        String ss= context.getString(R.string.False);
        if (nex_matches.get(position).Started.equals(ss)) {
            holder.textView4.setText(R.string.notStarted);
        } else {
            String s=nex_matches.get(position).team_home_score;
            holder.textView4.setText(s + context.getString(R.string.above)
                    + nex_matches.get(position).team_away_score);
        }
        if (nex_matches.get(position).finished.equals(context.getString(R.string.False))
                && nex_matches.get(position).Started.equals( context.getString(R.string.False))) {
            holder.textView5.setText(R.string.fixture_status);
        }
        if (nex_matches.get(position).finished.equals( context.getString(R.string.False))
                && nex_matches.get(position).Started.equals(context.getString(R.string.True))) {
            holder.textView5.setText(R.string.started);
        }
        if (nex_matches.get(position).finished.equals(context.getString(R.string.True))
                && nex_matches.get(position).Started.equals( context.getString(R.string.True))) {
            holder.textView5.setText(R.string.finished);
        } else if (nex_matches.get(position).finished.equals(context.getString(R.string.True))) {
            holder.textView5.setText(R.string.finished);
        }


    }

    @Override
    public int getItemCount() {
        return nex_matches.size();
    }

    public interface ListItemClickListener {
        void OnListItemClick(int index);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView1;
        ImageView imageView2;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.iv_home);
            imageView2 = (ImageView) itemView.findViewById(R.id.iv_away);
            textView1 = (TextView) itemView.findViewById(R.id.tv_home);
            textView2 = (TextView) itemView.findViewById(R.id.tv_away);
            textView3 = (TextView) itemView.findViewById(R.id.tv_date);
            textView4 = (TextView) itemView.findViewById(R.id.tv_result);
            textView5 = (TextView) itemView.findViewById(R.id.tv_status);
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            int ClickedPosition = getAdapterPosition();
            mOnClickListener.OnListItemClick(ClickedPosition);

        }
    }
}