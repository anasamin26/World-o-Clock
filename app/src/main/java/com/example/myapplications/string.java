package com.example.myapplications;

import java.io.Serializable;

public class string implements Serializable
{

    public String city;
    public String time;
    public int id;
    boolean check=false;
    private boolean isChecked;
    public boolean isSelected() {
        return isChecked;
    }
    public void setSelected(boolean selected) {
        isChecked= selected;
    }
    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return city;
    }
}
