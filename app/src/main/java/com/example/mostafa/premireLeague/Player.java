package com.example.mostafa.premireLeague;

/**
 * Created by mostafa on 2/21/2018.
 */

public class Player {
    int id;
    String photo;
    String web_name;
    int team_code;
    int squad_number;
    String first_name;
    String second_name;
    int postion;

    public Player(int id, String p, String w, int team_code, int squad_number, String first_name, String second_name, int postion) {
        this.id = id;
        this.photo = p;
        this.web_name = w;
        this.team_code = team_code;
        this.squad_number = squad_number;
        this.first_name = first_name;
        this.second_name = second_name;
        this.postion = postion;
    }
}
