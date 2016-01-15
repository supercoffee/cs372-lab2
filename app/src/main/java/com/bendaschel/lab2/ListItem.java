package com.bendaschel.lab2;

/**
 * Created by ben on 1/15/16.
 */
public enum  ListItem {

    CINDY(R.drawable.cindy, "Cindy"),
    FRED(R.drawable.fred, "Fred"),
    KATE(R.drawable.kate, "Kate"),
    KEITH(R.drawable.keith, "Keith");

    private int id;
    private String name;

    ListItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return name;
    }
}
