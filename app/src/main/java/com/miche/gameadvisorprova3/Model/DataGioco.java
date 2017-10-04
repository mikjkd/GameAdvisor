package com.miche.gameadvisorprova3.Model;


/**
 * Created by miche on 03/10/2017.
 */

public class DataGioco {
    private String Genere;
    private String Link;
    private String Titolo;

    public DataGioco() { }
    public DataGioco(String Genere, String Link, String Titolo) {
        this.Titolo = Titolo;
        this.Link = Link;
        this.Genere = Genere;
    }

    public String getGenere() {
        return Genere;
    }
    public String getTitolo() {
        return Titolo;
    }
    public String getLink() {
        return Link;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }
    public void setLink(String link) {
        Link = link;
    }
    public void setGenere(String genere) {
        Genere = genere;
    }
}