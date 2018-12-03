package com.mcc.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zw on 2018/5/11.
 */
//分类
public class ItemConfigEntity implements Serializable {
    private List<ItemConfigEntity> classes;
    private int id;
    private String name;
    private boolean isCheck=false;
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<ItemConfigEntity> getClasses() {
        return classes;
    }

    public void setClasses(List<ItemConfigEntity> classes) {
        this.classes = classes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
