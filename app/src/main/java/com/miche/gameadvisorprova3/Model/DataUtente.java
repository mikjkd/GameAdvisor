package com.miche.gameadvisorprova3.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

/**
 * Created by miche on 15/10/2017.
 */

public class DataUtente implements Serializable {
    private String Username;
    private String Password;
    private String Email;
    private boolean Autenticated;
    private Context context;
    private String UID;
    private static final String autenticazione = "AUTENTICAZIONE";

    public DataUtente(Context context){
        this.context=context;
        SharedPreferences settings = context.getSharedPreferences(autenticazione,0);
        Autenticated = settings.getBoolean("authPref",false);
    }

    public DataUtente(String username, String password, String email, boolean autenticated,Context context) {
        Username = username;
        Password = password;
        Email = email;
        Autenticated = autenticated;
        this.context=context;
    }

    public String getUsername() { return Username;}

    public void setUsername(String username) {Username = username;}

    public String getPassword() { return Password;}

    public void setPassword(String password) { Password = password;}

    public String getEmail() { return Email; }

    public void setEmail(String email) {Email = email;}

    public boolean isAutenticated() {return Autenticated;}

    public void setAutenticated(boolean autenticated) {
        Autenticated = autenticated;
        SharedPreferences settings = context.getSharedPreferences(autenticazione,0);
        SharedPreferences.Editor editor = settings.edit().putBoolean("authPref",Autenticated);
        editor.apply();
    }

    public String getUID() {  return UID; }

    public void setUID(String UID) { this.UID = UID; }
}
