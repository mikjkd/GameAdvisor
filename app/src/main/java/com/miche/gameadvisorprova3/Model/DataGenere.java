package com.miche.gameadvisorprova3.Model;

import java.io.Serializable;

/**
 * Created by miche on 07/10/2017.
 */

public class DataGenere implements Serializable {
    private String KeyGenere;
    private String Titolo;
    private String KeyGioco;

    private String URLico;
    private String Pathlocale;

    public DataGenere(){}
    public DataGenere(String genere, String titolo, String key, String URLico) {
        KeyGenere = genere;
        Titolo = titolo;
        KeyGioco = key;
        this.URLico = URLico;
    }

    public String getKeyGenere() { return KeyGenere; }

    public void setKeyGenere(String genere) {KeyGenere = genere; }

    public String getTitolo() { return Titolo;}

    public void setTitolo(String titolo) { Titolo = titolo;}

    public String getKeyGioco() { return KeyGioco; }

    public void setKeyGioco(String key) { KeyGioco = key; }

    public String getURLico() {
        return URLico;
    }

    public void setURLico(String URLico) {
        this.URLico = URLico;
    }

    public String getPathlocale() {
        return Pathlocale;
    }

    public void setPathlocale(String pathlocale) {
        Pathlocale = pathlocale;
    }
}
