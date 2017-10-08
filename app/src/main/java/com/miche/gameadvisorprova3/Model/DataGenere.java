package com.miche.gameadvisorprova3.Model;

/**
 * Created by miche on 07/10/2017.
 */

public class DataGenere {
    private String KeyGenere;
    private String Titolo;
    private String KeyGioco;
    public DataGenere(){}
    public DataGenere(String genere, String titolo, String key) {
        KeyGenere = genere;
        Titolo = titolo;
        KeyGioco = key;
    }

    public String getKeyGenere() { return KeyGenere; }

    public void setKeyGenere(String genere) {KeyGenere = genere; }

    public String getTitolo() { return Titolo;}

    public void setTitolo(String titolo) { Titolo = titolo;}

    public String getKeyGioco() { return KeyGioco; }

    public void setKeyGioco(String key) { KeyGioco = key; }
}
