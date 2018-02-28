package com.example.mostafa.premireLeague;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mostafa on 2/15/2018.
 */

public class Teams {
    private static final List<Integer> images = new ArrayList<Integer>() {
        {
            add(R.drawable.a1);
            add(R.drawable.a2);
            add(R.drawable.a3);
            add(R.drawable.a4);
            add(R.drawable.a5);
            add(R.drawable.a6);
            add(R.drawable.a7);
            add(R.drawable.a8);
            add(R.drawable.a9);
            add(R.drawable.a10);
            add(R.drawable.a11);
            add(R.drawable.a12);
            add(R.drawable.a13);
            add(R.drawable.a14);
            add(R.drawable.a15);
            add(R.drawable.a16);
            add(R.drawable.a17);
            add(R.drawable.a18);
            add(R.drawable.a19);
            add(R.drawable.a20);
        }
    };
    private static final List<Integer> names = new ArrayList<Integer>() {
        {
            add(R.string.t1);
            add(R.string.t2);
            add(R.string.t3);
            add(R.string.t4);
            add(R.string.t5);
            add(R.string.t6);
            add(R.string.t7);
            add(R.string.t8);
            add(R.string.t9);
            add(R.string.t10);
            add(R.string.t11);
            add(R.string.t12);
            add(R.string.t13);
            add(R.string.t14);
            add(R.string.t15);
            add(R.string.t16);
            add(R.string.t17);
            add(R.string.t18);
            add(R.string.t19);
            add(R.string.t20);


        }
    };


    private static final List<String> element_types = new ArrayList<String>() {
        {
            add("Goalkeeper");
            add("Defender");
            add("Midfielder");
            add("Forward");
        }
    };
    private static final List<String> team_names = new ArrayList<String>() {
        {
            add("Arsenal");
            add("Bournemouth");
            add("Brighton");
            add("Burnley");
            add("Chelsea");
            add("Crystal Palace");
            add("Everton");
            add("Huddersfield");
            add("Leicester");
            add("Liverpool");
            add("Man City");
            add("Man Utd");
            add("Newcastle");
            add("Southampton");
            add("Stoke");
            add("Swansea");
            add("Spurs");
            add("Watford");
            add("West Brom");
            add("West Ham");
        }
    };
    private static final List<String> intialMatches = new ArrayList<String>() {
        {
            add("GameWeek:27");
            add("Spurs 1:0 Arsenal");
            add("Everton 3:1 Crystal Palace");
            add("Stoke 1:1 Brighton");
            add("Swansea 1:0 Burnley");
            add("West Ham 2:0 Watford");
            add("Man City 5:1 Leicester");
            add("Huddersfield 4:1 Bournemouth");
            add("Newcastle 1:0 Man Utd");
            add("Southampton 0:2 Liverpool");
            add("Chelsea 3:0 West Brom");


        }
    };

    public static List<Integer> getImages() {
        return images;
    }

    public static List<Integer> getNames() {
        return names;
    }

    public static List<String> getElement_types() {
        return element_types;
    }

    public static List<String> getTeam_names() {
        return team_names;
    }

    public static List<String> getIntialMatches() {
        return intialMatches;
    }

}
