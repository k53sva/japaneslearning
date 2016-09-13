package com.melody.education.model;

/**
 * Created by K53SV on 9/7/2016.
 */
public class SyllabariesModel {
    public String charJapanese;
    public String charLatinh;
    public String audioName;
    public int image;

    public SyllabariesModel(String charJapanese, String charLatinh, String audioName) {
        this.charJapanese = charJapanese;
        this.charLatinh = charLatinh;
        this.audioName = audioName;
    }

    public SyllabariesModel(String charJapanese, String charLatinh, String audioName, int image) {
        this.charJapanese = charJapanese;
        this.charLatinh = charLatinh;
        this.audioName = audioName;
        this.image = image;
    }
}
