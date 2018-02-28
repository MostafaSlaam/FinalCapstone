package com.example.mostafa.premireLeague;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Section.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Section#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Section extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    ArrayAdapter homeAdapter;
    List<Integer> homeID;
    ArrayAdapter awayAdapter;
    List<Integer> awayID;
    String state;
    Context context;

    public Section() {
        // Required empty public constructor

    }

    public static Section newInstance(String param1, String param2) {
        Section fragment = new Section();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public void get_section(Context context, String state, ArrayAdapter homeAdapter,
                            List<Integer> homeID,
                            ArrayAdapter awayAdapter,
                            List<Integer> awayID) {

        this.state = state;
        this.homeAdapter = homeAdapter;
        this.homeID = homeID;
        this.awayAdapter = awayAdapter;
        this.awayID = awayID;
        this.context = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_stat);
        textView.setText(state);
        ListView listView1 = (ListView) view.findViewById(R.id.LV_home);
        listView1.setAdapter(homeAdapter);
        ListView listView2 = (ListView) view.findViewById(R.id.LV_away);
        listView2.setAdapter(awayAdapter);


        final Dialog dialog = new Dialog(context);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.setContentView(R.layout.player_view);
                dialog.setTitle(R.string.dialog_title);
                final int p_id = Integer.valueOf(homeID.get(position));
                final TextView name = (TextView) dialog.findViewById(R.id.TV_name);
                TextView team = (TextView) dialog.findViewById(R.id.TV_team);
                final TextView potion = (TextView) dialog.findViewById(R.id.TV_position);
                ImageView playerPhoto = (ImageView) dialog.findViewById(R.id.IV_player);
                ImageView logo = (ImageView) dialog.findViewById(R.id.IV_logo);
                final Player newplayer = getPlayer(p_id);
                Picasso.with(context).load(Extra.BaseImageUrl + newplayer.photo + getString(R.string.related_image_url)).into(playerPhoto);
                logo.setImageResource(Teams.getImages().get(newplayer.team_code - 1));
                String s1=newplayer.first_name;
                String s2= getString(R.string.space);
                String s3=newplayer.second_name;
                name.setText( s1+s2 + s3);
                team.setText(Teams.getNames().get(newplayer.team_code - 1));
                potion.setText(Teams.getElement_types().get(newplayer.postion - 1));
                dialog.show();
                ImageView close = (ImageView) dialog.findViewById(R.id.IV_close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button add_to_watchlist = (Button) dialog.findViewById(R.id.B_add);
                add_to_watchlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_id, String.valueOf(p_id));
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_title, name.getText().toString());
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_image, Extra.BaseImageUrl + newplayer.photo + getString(R.string.related_image_url));
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_postion, potion.getText().toString());
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_team, String.valueOf(newplayer.team_code - 1));
                        String[] args = {String.valueOf(p_id)};
                        Cursor c = context.getContentResolver().query(DataBaseContract.DataBaseEntry.MyUri,
                                null,
                                getString(R.string.check_player_id), args, null);

                        if (c.getCount() == 0) {
                            Uri uri = context.getContentResolver().insert(DataBaseContract.DataBaseEntry.MyUri, contentValues);
                            if (uri != null) {
                                Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.added_player, Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });


            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.setContentView(R.layout.player_view);
                dialog.setTitle(R.string.dialog_title);
                final int p_id = Integer.valueOf(awayID.get(position));
                final TextView name = (TextView) dialog.findViewById(R.id.TV_name);
                TextView team = (TextView) dialog.findViewById(R.id.TV_team);
                final TextView potion = (TextView) dialog.findViewById(R.id.TV_position);
                ImageView playerPhoto = (ImageView) dialog.findViewById(R.id.IV_player);
                ImageView logo = (ImageView) dialog.findViewById(R.id.IV_logo);
                final Player newplayer = getPlayer(p_id);
                Picasso.with(context).load(Extra.BaseImageUrl + newplayer.photo + getString(R.string.related_image_url)).into(playerPhoto);
                logo.setImageResource(Teams.getImages().get(newplayer.team_code - 1));
                String s1=newplayer.first_name;
                String s2=getString(R.string.space);
                String s3=newplayer.second_name;
                name.setText(s1 + s2 + s3);
                team.setText(Teams.getNames().get(newplayer.team_code - 1));
                potion.setText(Teams.getElement_types().get(newplayer.postion - 1));
                dialog.show();
                ImageView close = (ImageView) dialog.findViewById(R.id.IV_close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button add_to_watchlist = (Button) dialog.findViewById(R.id.B_add);
                add_to_watchlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_id, String.valueOf(p_id));
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_title, name.getText().toString());
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_image, Extra.BaseImageUrl + newplayer.photo + getString(R.string.related_image_url));
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_postion, potion.getText().toString());
                        contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_team, String.valueOf(newplayer.team_code - 1));
                        String[] args = {String.valueOf(p_id)};
                        Cursor c = context.getContentResolver().query(DataBaseContract.DataBaseEntry.MyUri,
                                null,
                                getString(R.string.check_player_id), args, null);

                        if (c.getCount() == 0) {
                            Uri uri = context.getContentResolver().insert(DataBaseContract.DataBaseEntry.MyUri, contentValues);
                            if (uri != null) {
                                Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.added_player, Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

    public Player getPlayer(int id) {

        try {

            JSONObject jsonObject = Extra.jsonArray.getJSONObject(id - 1);
            return new Player(id, jsonObject.getString(getString(R.string.player_code)), jsonObject.getString(getString(R.string.web_name)),
                    Integer.valueOf(jsonObject.getString(getString(R.string.team_player))),
                    Integer.valueOf(jsonObject.getString(getString(R.string.s_num))),
                    jsonObject.getString(getString(R.string.f_name)),
                    jsonObject.getString(getString(R.string.s_name)),
                    Integer.valueOf(jsonObject.getString(getString(R.string.postion)))

            );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        OnFragmentInteractionListener mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
