package com.miche.gameadvisorprova3.Model;


import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by miche on 03/10/2017.
 */

public class DataGioco implements Serializable{
    private String Genere;
    private String Titolo;
    private String URLimg;
    private String Key;
    private String UrlIconaLocale;


    public DataGioco(){}

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public DataGioco(String genere, String titolo,  String URLimg) {
        Genere = genere;

        Titolo = titolo;
        this.URLimg = URLimg;
    }

    public String getGenere() {
        return Genere;
    }

    public void setGenere(String genere) {
        Genere = genere;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public String getURLimg() {
        return URLimg;
    }

    public void setURLimg(String URLimg) {
        this.URLimg = URLimg;
    }

    public String getUrlIconaLocale() {
        return UrlIconaLocale;
    }

    public void setUrlIconaLocale(String urlIconaLocale) {
        UrlIconaLocale = urlIconaLocale;
    }

}