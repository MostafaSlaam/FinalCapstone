package com.example.mostafa.premireLeague;

import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements FirstAdapter.ListItemClickListener {

    public static final String KEY_NEXT_WEEK = "next_week";
    public static final String KEY_MAKE_COMMENT = "make_comment";
    public static final String KEY_EDITTEXT = "edit_text";
    static int next_week = 0;
    static boolean make_comment = false;
    ArrayList<MatchData> nex_matches = new ArrayList<MatchData>();
    ProgressBar bar;
    ImageButton pre;
    ImageButton next;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    String nextGame;
    TextView textView;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth;
    AppWidgetManager appWidgetManager;
    Dialog dialog;
    EditText editText;
    ImageButton button;
    Button b_view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {

            AuthUI.getInstance().signOut(getApplicationContext()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    startActivity(new Intent(MainActivity.this, SignIn.class));
                    finish();
                }

            });
        } else if (item.getItemId() == R.id.Refresh) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null) {
                if (next_week == 0) {
                    new GetData().execute(getString(R.string.static_url), getString(R.string.f_path), getString(R.string.s_path));
                } else {
                    new GetData().execute(getString(R.string.event_url) + String.valueOf(next_week) +
                            getString(R.string.live), getString(R.string.fixtures),getString(R.string.free));
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.test, Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.WL) {
            Intent intent = new Intent(MainActivity.this, Watchlist.class);
            startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.comments_view);
        button = (ImageButton) dialog.findViewById(R.id.send_button);
        editText = (EditText) dialog.findViewById(R.id.message_text);
        listView = (ListView) dialog.findViewById(R.id.comments_listView);

        if (savedInstanceState != null) {
            next_week = savedInstanceState.getInt(KEY_NEXT_WEEK);
            make_comment = savedInstanceState.getBoolean(KEY_MAKE_COMMENT);
            if (make_comment) {
                make_comment(savedInstanceState.getString(KEY_EDITTEXT));
            }
        }

        b_view = (Button) findViewById(R.id.view_button);
        bar = (ProgressBar) findViewById(R.id.PB);
        mAuth = FirebaseAuth.getInstance();
        pre = (ImageButton) findViewById(R.id.b_pre);
        next = (ImageButton) findViewById(R.id.b_next);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        textView = (TextView) findViewById(R.id.textView);
        //  mDatabase = FirebaseDatabase.getInstance().getReference();
        adapter = new FirstAdapter(nex_matches, getApplicationContext(), this);

        //user_Data
        CircleImageView user_image = (CircleImageView) findViewById(R.id.user_image);
        TextView user_name = (TextView) findViewById(R.id.TV_user);
        Picasso.with(getApplicationContext()).load(mAuth.getCurrentUser().getPhotoUrl()).into(user_image);
        user_name.setText(mAuth.getCurrentUser().getDisplayName());

        //check internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null) {
            //for widget
            new GetMatches().execute(getString(R.string.static_url));
            if (Extra.widgetArray != null) {
                appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, MatchesWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.LV_widget);
            }
            //prepare adapter of recyclerView


            if (next_week == 0) {
                new GetData().execute(getString(R.string.static_url), getString(R.string.f_path), getString(R.string.s_path));
            } else {
                String s=getString(R.string.gameweek);
                textView.setText( s+ String.valueOf(next_week));
                new GetData().execute(getString(R.string.event_url) + String.valueOf(next_week) +
                        getString(R.string.live), getString(R.string.fixtures),getString(R.string.free));
            }

        } else {
            Toast.makeText(getApplicationContext(), R.string.test, Toast.LENGTH_SHORT).show();
        }

        //load jsonfile for player static data
        try {
            JSONObject jsonObject = new JSONObject(loadJSON());
            Extra.jsonArray = jsonObject.getJSONArray(getString(R.string.elemnts));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //buttons of matches
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next_week > 1) {
                    next_week--;
                    nex_matches.clear();
                    String s=getString(R.string.gameweek);
                    textView.setText(s + String.valueOf(next_week));
                    new GetData().execute(getString(R.string.event_url) + String.valueOf(next_week) +
                            getString(R.string.live), getString(R.string.fixtures),getString(R.string.free));

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next_week < 38) {
                    next_week++;
                    nex_matches.clear();
                    String s=getString(R.string.gameweek);
                    textView.setText(s+ String.valueOf(next_week));
                    new GetData().execute(getString(R.string.event_url) + String.valueOf(next_week) +
                            getString(R.string.live), getString(R.string.fixtures),getString(R.string.free));
                }
            }
        });


        //make comments
        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                make_comment(getString(R.string.free));
            }
        });


    }


    //for make a comment
    public void make_comment(String edit_text) {


        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextSize(15);
                tv.setPadding(0, 0, 0, 0);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                params.height = 70;
                view.setLayoutParams(params);
                return view;
            }
        };
        updateFirebase();
        dialog.show();
        editText.setText(edit_text);
        editText.setSelection(edit_text.length());
        make_comment = true;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals(getString(R.string.free))) {
                    Comments comment = new Comments(editText.getText().toString(),
                            mAuth.getCurrentUser().getEmail(),
                            String.valueOf(next_week), mAuth.getCurrentUser().getDisplayName());

                    mDatabase.child(getString(R.string.Comments)).push().setValue(comment);
                    updateFirebase();
                    editText.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), R.string.defult_comment, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                make_comment = false;
            }
        });

    }

    public void updateFirebase() {
        Query gameWeekComments = mDatabase;
        gameWeekComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayAdapter.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.child(getString(R.string.Comments)).getChildren()) {

                    if (postSnapshot.getValue(Comments.class).game_week.toString().equals(String.valueOf(next_week))) {

                        arrayAdapter.add(postSnapshot.getValue(Comments.class).user_name +
                                getString(R.string.above) +
                                postSnapshot.getValue(Comments.class).comment);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setAdapter(arrayAdapter);


    }

    //load static data
    public String loadJSON() {
        String json = null;
        try {

            InputStream is = getResources().openRawResource(R.raw.file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, getString(R.string.buffer));


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void OnListItemClick(int index) {

        if (nex_matches.get(index).Started.equals( getString(R.string.False))) {
            Toast.makeText(getApplicationContext(), R.string.match_status, Toast.LENGTH_SHORT).show();
        } else

        {
            Intent intent = new Intent(MainActivity.this, MatchDetails.class);
            intent.putExtra(getString(R.string.gw), String.valueOf(next_week));
            intent.putExtra(getString(R.string.index), String.valueOf(index));
            intent.putExtra(getString(R.string.ID), nex_matches.get(index).id);
            intent.putExtra(getString(R.string.home), nex_matches.get(index).team_home);
            intent.putExtra(getString(R.string.away), nex_matches.get(index).team_away);
            intent.putExtra(getString(R.string.h_score), nex_matches.get(index).team_home_score);
            intent.putExtra(getString(R.string.a_score), nex_matches.get(index).team_away_score);
            intent.putExtra(getString(R.string.finished), nex_matches.get(index).finished);
            intent.putExtra(getString(R.string.date), nex_matches.get(index).date);
            intent.putExtra(getString(R.string.minute), nex_matches.get(index).minute);
            intent.putExtra(getString(R.string.started), nex_matches.get(index).Started);
            startActivity(intent);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putInt(KEY_NEXT_WEEK, next_week);
        currentState.putBoolean(KEY_MAKE_COMMENT, make_comment);
        if (editText != null) {
            currentState.putString(KEY_EDITTEXT, editText.getText().toString());
        } else {
            currentState.putString(KEY_EDITTEXT, getString(R.string.free));
        }
    }

    //for recyclerView
    public class GetData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            new GetMatches().execute(getString(R.string.static_url));
            if (Extra.widgetArray != null) {
                appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), MatchesWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.LV_widget);
            }
            HttpURLConnection connection = null;
            Uri uri1 = Uri.parse(strings[0]);
            URL url1 = null;
            try {

                url1 = new URL(uri1.toString());
                connection = (HttpURLConnection) url1.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                Scanner scanner = new Scanner(stream);
                scanner.useDelimiter(getString(R.string.dlimater));
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    JSONObject parentObject = new JSONObject(scanner.next());

                    if (!strings[2].equals(getString(R.string.free))) {
                        nextGame = parentObject.getString(strings[2]);
                        next_week = Integer.valueOf(nextGame);
                        Extra.currentGame = parentObject.getString(getString(R.string.jsonObject));
                    }
                    JSONArray parentArray = parentObject.getJSONArray(strings[1]);
                    Log.d("aza2", String.valueOf(parentArray.length()));
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        MatchData matchData = new MatchData(
                                finalObject.getString(getString(R.string.get_string_id))
                                , finalObject.getString(getString(R.string.kickoff_time_formatted))
                                , finalObject.getString(getString(R.string.team_h))
                                , finalObject.getString(getString(R.string.team_a))
                                , finalObject.getString(getString(R.string.team_h_score))
                                , finalObject.getString(getString(R.string.team_a_score))
                                , finalObject.getString(getString(R.string.finished))
                                , finalObject.getString(getString(R.string.minute)),
                                finalObject.getString(getString(R.string.started))
                        );
                        nex_matches.add(matchData);
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.INVISIBLE);
            b_view.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            bar.setVisibility(View.INVISIBLE);

            if (String.valueOf(next_week) == nextGame) {
                String s=getString(R.string.gameweek);
                textView.setText(s + nextGame);
            }
            b_view.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            pre.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);


        }


    }

    //for widget
    public class GetMatches extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            HttpURLConnection connection = null;
            HttpURLConnection connection2 = null;
            Uri uri1 = Uri.parse(strings[0]);
            URL url1 = null;
            try {

                url1 = new URL(uri1.toString());
                connection = (HttpURLConnection) url1.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                Scanner scanner = new Scanner(stream);
                scanner.useDelimiter(getString(R.string.dlimater));
                boolean hasInput = scanner.hasNext();
                String curentWeek = getString(R.string.zero);
                if (hasInput) {
                    JSONObject parentObject = new JSONObject(scanner.next());
                    curentWeek = parentObject.getString(getString(R.string.jsonObject));
                    Extra.WidgetGameWeek = curentWeek;
                    Uri uri12 = Uri.parse(getString(R.string.event_url) + curentWeek +  getString(R.string.live));
                    URL url12 = null;
                    url12 = new URL(uri12.toString());
                    connection2 = (HttpURLConnection) url12.openConnection();
                    connection2.connect();
                    InputStream stream2 = connection2.getInputStream();
                    Scanner scanner2 = new Scanner(stream2);
                    scanner2.useDelimiter(getString(R.string.dlimater));
                    boolean hasInput2 = scanner2.hasNext();

                    if (hasInput2) {
                        JSONObject parentObject2 = new JSONObject(scanner2.next());
                        Extra.widgetArray = parentObject2.getJSONArray(getString(R.string.fixtures));
                    }

                }

                Log.d("vcv", String.valueOf(Extra.widgetArray.length()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                if (connection2 != null)
                    connection2.disconnect();
            }


            return null;
        }


    }
}
