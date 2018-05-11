package com.mcc.data;

/**
 * Created by zw on 2018/5/5.
 */

public class MenuBar {
    private String name;
    private int id;

    public MenuBar(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
