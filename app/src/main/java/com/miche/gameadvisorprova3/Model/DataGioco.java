package com.miche.gameadvisorprova3.Model;


/**
 * Created by miche on 03/10/2017.
 */

public class DataGioco {
    private String Genere;
    private String Link;
    private String Titolo;
    private String Descrizione;
    private String URLimg;
    public DataGioco(){}
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
}