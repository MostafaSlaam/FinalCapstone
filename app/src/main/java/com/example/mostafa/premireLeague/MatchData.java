package com.example.mostafa.premireLeague;

/**
 * Created by mostafa on 2/15/2018.
 */

public class MatchData {
    String id;
    String team_home;
    String team_away;
    String team_home_score;
    String team_away_score;
    String finished;
    String Started;
    String date;
    String minute;

    public MatchData(String id, String date, String team_home, String team_away, String team_home_score,
                     String team_away_score, String finished, String minute
            , String started)
    {
        this.id = id;
        this.date = date;
        this.finished = finished;
        this.team_home = team_home;
        this.team_away = team_away;
        this.team_home_score = team_home_score;
        this.team_away_score = team_away_score;
        this.minute = minute;
        this.Started = started;
    }
}
