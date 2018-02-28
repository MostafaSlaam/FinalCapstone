package com.example.mostafa.premireLeague;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mostafa on 2/26/2018.
 */

public class WatclistAdapter extends RecyclerView.Adapter<WatclistAdapter.MyView> {

    private Cursor cursor;
    private Context context;

    public WatclistAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.watchlist_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyView viewHolder = new MyView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {

        int idIndex = cursor.getColumnIndex(DataBaseContract.DataBaseEntry._ID);
        cursor.moveToPosition(position);
        final int id = cursor.getInt(idIndex);
        holder.itemView.setTag(id);

        int Index1 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_title);
        int Index2 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_image);
        int Index3 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_team);
        int Index4 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_postion);
        holder.p_name.setText(cursor.getString(Index1));
        holder.p_postion.setText(cursor.getString(Index4));
        Picasso.with(context).load(cursor.getString(Index2))
                .into(holder.p_image);
        int team_code = Integer.valueOf(cursor.getString(Index3));
        holder.p_team_logo.setImageResource(Teams.getImages().get(team_code));
        holder.p_team.setText(Teams.getTeam_names().get(team_code));

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }


    public class MyView extends RecyclerView.ViewHolder {

        ImageView p_image;
        ImageView p_team_logo;
        TextView p_name;
        TextView p_postion;
        TextView p_team;

        public MyView(View itemView) {
            super(itemView);
            p_image = (ImageView) itemView.findViewById(R.id.p_image);
            p_team_logo = (ImageView) itemView.findViewById(R.id.p_team_logo);
            p_name = (TextView) itemView.findViewById(R.id.p_name);
            p_postion = (TextView) itemView.findViewById(R.id.p_postion);
            p_team = (TextView) itemView.findViewById(R.id.p_team);

        }
    }

}
