package com.melody.education.model;

/**
 * Created by K53SV on 9/6/2016.
 */
public class ExpandedMenuModel {
    public String iconName;
    public int iconImg;

    public ExpandedMenuModel(String iconName, int iconImg) {
        this.iconName = iconName;
        this.iconImg = iconImg;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconImg() {
        return iconImg;
    }

    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }
}
