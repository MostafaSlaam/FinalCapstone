package com.example.mostafa.premireLeague;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mostafa on 2/22/2018.
 */

public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteFactory(this.getApplicationContext());
    }
}

class ListViewRemoteFactory implements RemoteViewsService.RemoteViewsFactory {


    Context context;

    public ListViewRemoteFactory(Context applicationContext) {
        this.context = applicationContext;
        new GetMatches().execute(context.getString(R.string.static_url));
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {


        if (Extra.widgetArray == null) {
            return 11;
        } else {
            return Extra.widgetArray.length() + 1;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.listview_lable);

        JSONObject child = null;
        if (Extra.widgetArray != null) {
            if (position == 0) {
                views.setTextViewText(R.id.TV_lable, context.getString(R.string.gameweek) + Extra.WidgetGameWeek);
            } else {
                try {
                    child = Extra.widgetArray.getJSONObject(position);
                    MatchData match = get(child);
                    views.setTextViewText(R.id.TV_lable, (
                            Teams.getTeam_names().get(Integer.valueOf(match.team_home) - 1))
                            +
                            context.getString(R.string.space) + match.team_home_score +
                            context.getString(R.string.above) + match.team_away_score +
                            context.getString(R.string.space)+
                            Teams.getTeam_names().get(Integer.valueOf(match.team_away) - 1)

                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            views.setTextViewText(R.id.TV_lable, Teams.getIntialMatches().get(position));
        }
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public MatchData get(JSONObject child) {
        try {
            return new MatchData(null
                    , null
                    , child.getString(context.getString(R.string.team_h))
                    , child.getString(context.getString(R.string.team_a))
                    , child.getString(context.getString(R.string.team_h_score))
                    , child.getString(context.getString(R.string.team_a_score))
                    , null
                    , null
                    , null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

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
                scanner.useDelimiter(context.getString(R.string.dlimater));
                boolean hasInput = scanner.hasNext();
                String curentWeek = context.getString(R.string.zero);
                if (hasInput) {
                    JSONObject parentObject = new JSONObject(scanner.next());
                    curentWeek = parentObject.getString(context.getString(R.string.jsonObject));
                    Extra.WidgetGameWeek = curentWeek;
                }

                Uri uri12 = Uri.parse(context.getString(R.string.event_url) + curentWeek + context.getString(R.string.live));
                URL url12 = null;
                url12 = new URL(uri12.toString());
                connection2 = (HttpURLConnection) url12.openConnection();
                connection2.connect();
                InputStream stream2 = connection2.getInputStream();
                Scanner scanner2 = new Scanner(stream2);
                scanner2.useDelimiter(context.getString(R.string.dlimater));
                boolean hasInput2 = scanner2.hasNext();

                if (hasInput2) {
                    JSONObject parentObject = new JSONObject(scanner2.next());
                    Extra.widgetArray = parentObject.getJSONArray(context.getString(R.string.fixtures));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
                connection2.disconnect();
            }


            return null;
        }


    }


}

