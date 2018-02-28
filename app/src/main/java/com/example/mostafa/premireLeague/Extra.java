package com.example.mostafa.premireLeague;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mostafa on 2/18/2018.
 */

public class Extra {

    public static JSONArray jsonArray;
    public static JSONArray widgetArray;
    public static String BaseImageUrl = "https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p";
    public static String currentGame;
    public static String WidgetGameWeek;
    public static List<String> stat = new ArrayList<String>() {
        {
            add("goals_scored");
            add("assists");
            add("own_goals");
            add("penalties_saved");
            add("penalties_missed");
            add("yellow_cards");
            add("red_cards");
            add("saves");
            add("bonus");
            add("bps");

        }
    };
}
