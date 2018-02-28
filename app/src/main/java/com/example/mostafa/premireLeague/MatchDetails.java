package com.example.mostafa.premireLeague;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatchDetails extends AppCompatActivity {

    Intent intent;
    JSONArray array;
    ImageView imageView1;
    ImageView imageView2;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    int count = 1;
    ArrayAdapter homeAdapter;
    List<Integer> homeID;
    ArrayAdapter awayAdapter;
    List<Integer> awayID;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        intent = getIntent();
        imageView1 = (ImageView) findViewById(R.id.iv_home);
        imageView2 = (ImageView) findViewById(R.id.iv_away);
        textView1 = (TextView) findViewById(R.id.tv_home);
        textView2 = (TextView) findViewById(R.id.tv_away);
        textView3 = (TextView) findViewById(R.id.tv_date);
        textView4 = (TextView) findViewById(R.id.tv_result);
        textView5 = (TextView) findViewById(R.id.tv_status);

        imageView1.setImageResource(Teams.getImages().get(Integer.valueOf(intent.getStringExtra(getString(R.string.home))) - 1));
        imageView2.setImageResource(Teams.getImages().get(Integer.valueOf(intent.getStringExtra(getString(R.string.away))) - 1));
        textView1.setText(Teams.getTeam_names().get(Integer.valueOf(intent.getStringExtra(getString(R.string.home))) - 1));
        textView2.setText(Teams.getTeam_names().get(Integer.valueOf(intent.getStringExtra(getString(R.string.away))) - 1));
        textView3.setText(intent.getStringExtra(getString(R.string.date)));
        String s1=intent.getStringExtra(getString(R.string.h_score));
        String s2=getString(R.string.above);
        String s3= intent.getStringExtra(getString(R.string.a_score));
        textView4.setText( s1+ s2 +s3);
        if (intent.getStringExtra(getString(R.string.finished)) == getString(R.string.False)
                && intent.getStringExtra(getString(R.string.started)) == getString(R.string.True)) {
            textView5.setText(R.string.started);
        } else {
            textView5.setText(R.string.finished);
        }
        new getData().execute(intent.getStringExtra(getString(R.string.gw))
                , intent.getStringExtra(getString(R.string.index)));


    }


    public String getPlayerName(int id) {

        try {

            JSONObject jsonObject = Extra.jsonArray.getJSONObject(id - 1);
            return jsonObject.getString(getString(R.string.web_name));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class getData extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    homeAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);
                            tv.setTextSize(10);
                            tv.setPadding(0, 0, 0, 0);
                            ViewGroup.LayoutParams params = view.getLayoutParams();
                            tv.setTextColor(Color.BLACK);
                            tv.setGravity(Gravity.CENTER);
                            params.height = 40;
                            view.setLayoutParams(params);
                            return view;
                        }
                    };
                    homeID = new ArrayList<>();
                    awayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);
                            tv.setTextSize(10);
                            tv.setPadding(0, 0, 0, 0);
                            ViewGroup.LayoutParams params = view.getLayoutParams();
                            tv.setTextColor(Color.BLACK);
                            tv.setGravity(Gravity.CENTER);
                            params.height = 40;
                            view.setLayoutParams(params);
                            return view;
                        }
                    };

                    awayID = new ArrayList<>();
                    homeAdapter.clear();
                    homeID.clear();
                    awayAdapter.clear();
                    awayID.clear();
                    try {
                        JSONObject object = array.getJSONObject(i);
                        JSONObject object1 = object.getJSONObject(Extra.stat.get(i));


                        JSONArray away = object1.getJSONArray(getString(R.string.a));
                        for (int j = 0; j < away.length(); j++) {
                            JSONObject awaydata = away.getJSONObject(j);
                            awayID.add(Integer.valueOf(awaydata.getString(getString(R.string.element))));
                            if (Integer.valueOf(awaydata.getString(getString(R.string.value))) == 1) {
                                String name = getPlayerName(Integer.valueOf(awaydata.getString(getString(R.string.element))));
                                awayAdapter.add(name);
                            } else {
                                String name = getPlayerName(Integer.valueOf(awaydata.getString(getString(R.string.element))));
                                awayAdapter.add(name + getString(R.string.left_brac) + awaydata.getString(getString(R.string.value)) + getString(R.string.right_brac));
                            }
                        }


                        JSONArray home = object1.getJSONArray(getString(R.string.h));

                        for (int f = 0; f < home.length(); f++) {
                            JSONObject homedata = home.getJSONObject(f);
                            homeID.add(Integer.valueOf(homedata.getString(getString(R.string.element))));
                            if (Integer.valueOf(homedata.getString(getString(R.string.value))) == 1) {
                                String name = getPlayerName(Integer.valueOf(homedata.getString(getString(R.string.element))));
                                homeAdapter.add(name);
                            } else {
                                String name = getPlayerName(Integer.valueOf(homedata.getString(getString(R.string.element))));
                                homeAdapter.add(name +
                                        getString(R.string.left_brac) +
                                        homedata.getString(getString(R.string.value)) +
                                        getString(R.string.right_brac));
                            }


                        }


                        choiceFragment(Extra.stat.get(i));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void choiceFragment(String state) {
            if (homeAdapter.getCount() != 0 || awayAdapter.getCount() != 0) {
                switch (count) {
                    case 1: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container1, fragment);
                        fragmentTransaction.commit();


                        break;
                    }
                    case 2: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container2, fragment);
                        fragmentTransaction.commit();
                        break;
                    }
                    case 3: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container3, fragment);
                        fragmentTransaction.commit();

                        break;
                    }
                    case 4: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container4, fragment);
                        fragmentTransaction.commit();

                        break;
                    }
                    case 5: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container5, fragment);
                        fragmentTransaction.commit();

                        break;
                    }
                    case 6: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container6, fragment);
                        fragmentTransaction.commit();

                        break;
                    }
                    case 7: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container7, fragment);
                        fragmentTransaction.commit();

                        break;
                    }
                    case 8: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container8, fragment);
                        fragmentTransaction.commit();

                        break;
                    }
                    case 9: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container9, fragment);

                        fragmentTransaction.commit();

                        break;
                    }
                    case 10: {
                        count++;
                        Section fragment = new Section();
                        fragment.get_section(MatchDetails.this, state, homeAdapter, homeID, awayAdapter, awayID);
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.add(R.id.container10, fragment);

                        fragmentTransaction.commit();

                        break;
                    }
                    default:
                        break;
                }

            }

        }

        @Override
        protected Void doInBackground(String... strings) {
            HttpURLConnection connection = null;
            Uri uri1 = Uri.parse(getString(R.string.event_url) + strings[0] + getString(R.string.live));
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
                    JSONArray parentArray = parentObject.getJSONArray(getString(R.string.fixtures));
                    JSONObject finalObject = parentArray.getJSONObject(Integer.valueOf(strings[1]));
                    array = finalObject.getJSONArray(getString(R.string.stats));

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
    }
}
