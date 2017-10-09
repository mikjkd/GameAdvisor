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
    private String Link;
    private String Titolo;
    private String Descrizione;
    private String URLimg;
    private String Key;
    private String UrlIconaLocale;
    private String UrlImmagineLocale;
   // Bitmap immagine;


    public DataGioco(){}

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public DataGioco(String genere, String link, String titolo, String descrizione, String URLimg) {
        Genere = genere;
        Link = link;
        Titolo = titolo;
        Descrizione = descrizione;
        this.URLimg = URLimg;
    }

    public String getGenere() {
        return Genere;
    }

    public void setGenere(String genere) {
        Genere = genere;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public String getURLimg() {
        return URLimg;
    }

    public void setURLimg(String URLimg) {
        this.URLimg = URLimg;
    }
/*
    public Bitmap getImmagine() {
        if(immagine==null)
            Log.e("getImmagine null","Class DataGioco");
        return immagine;
    }

    public void setImmagine(Bitmap immagine) {
        this.immagine = immagine;
        if(this.immagine==null)
            Log.e("setImmagine not null","Class DataGioco");
    }
*/
    public String getUrlIconaLocale() {
        return UrlIconaLocale;
    }

    public void setUrlIconaLocale(String urlIconaLocale) {
        UrlIconaLocale = urlIconaLocale;
    }

    public String getUrlImmagineLocale() {   return UrlImmagineLocale; }

    public void setUrlImmagineLocale(String urlImmagineLocale) { UrlImmagineLocale = urlImmagineLocale; }
}