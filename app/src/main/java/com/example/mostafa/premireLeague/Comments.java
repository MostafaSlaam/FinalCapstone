package com.example.mostafa.premireLeague;

/**
 * Created by mostafa on 2/19/2018.
 */

public class Comments {

    public String comment;
    public String user_email;
    public String game_week;
    public String user_name;

    public Comments() {
    }

    public Comments(String c, String e, String g, String n) {
        this.comment = c;
        this.game_week = g;
        this.user_email = e;
        this.user_name = n;
    }
}
