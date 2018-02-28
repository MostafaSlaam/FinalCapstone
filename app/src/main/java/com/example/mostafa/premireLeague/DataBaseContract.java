package com.example.mostafa.premireLeague;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mostafa on 2/17/2018.
 */

public class DataBaseContract {
    public static final String AUTHORITY = "com.example.mostafa.premireLeague";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "CurrentUser";

    public static final class DataBaseEntry implements BaseColumns {

        public static final Uri MyUri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
        public static final String TABLE_name = "WatchList";
        public static final String COLUMN_id = "PlayerID";
        public static final String COLUMN_title = "PlayerName";
        public static final String COLUMN_image = "ImageUrlPlayer";
        public static final String COLUMN_postion = "PlayerPostion";
        public static final String COLUMN_number = "PlayerNumber";
        public static final String COLUMN_team = "PlayerTeam";

    }
}
