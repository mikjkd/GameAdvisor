package com.miche.gameadvisorprova3.Model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miche.gameadvisorprova3.MainActivity;

import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by miche on 15/10/2017.
 */

public class AuthenticationClass implements Serializable {
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private DataUtente utente;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public AuthenticationClass(){

        mAuth=FirebaseAuth.getInstance();
    }
    public AuthenticationClass(DataUtente utente){   mAuth=FirebaseAuth.getInstance();this.utente=utente; }

    public AuthenticationClass(FirebaseAuth mAuth, DataUtente utente) {
        this.mAuth = mAuth;
        this.utente = utente;
    }
    public interface LoginUpdate{
        void loginEffettuato();
        void loginNonEffettuato();
        void erroreAutenticazione();
    }
    public void logInAnonimo(){
        utente.setAutenticated(false);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        mauth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                utente.setEmail("");
                utente.setPassword("");
            }
        });
    }
    public void login(final String Email, final String Password, final LoginUpdate loginUpdate){
        Log.e("Login","class");
        utente.setAutenticated(true);
        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser()!=null){
                                Log.e("login","Riuscito");
                                utente.setPassword(Password);
                                utente.setEmail(Email);
                                utente.setUID(mAuth.getCurrentUser().getUid());
                                loginUpdate.loginEffettuato();
                            }
                        }
                        else{
                            Log.e("Errore ","Autenticazione non riuscita");
                            utente.setAutenticated(false);
                            utente.setPassword("");
                            utente.setEmail("");
                            loginUpdate.erroreAutenticazione();
                        }
                    }
                });
    }

    public void signUp(final String Email,final String Password, final LoginUpdate loginUpdate){
        utente.setAutenticated(true);
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser u = mAuth.getCurrentUser();
                            if(u != null){
                                utente.setAutenticated(true);
                                utente.setEmail(Email);
                                utente.setPassword(Password);
                                u.sendEmailVerification();
                                db = FirebaseDatabase.getInstance();
                                //creazione utente nel database
                                Map<String,String> userData = new HashMap<String, String>();
                                userData.put("Email",utente.getEmail());
                                DatabaseReference ref = db.getReference().child("Utenti").child(u.getUid());
                                ref.setValue(userData);
                                utente.setUID(u.getUid());
                            }
                        }
                        else {
                            Log.e("Errore ","Registrazione non riuscita");
                            utente.setAutenticated(false);
                            utente.setPassword("");
                            utente.setEmail("");
                            loginUpdate.erroreAutenticazione();
                        }
                    }
                });
    }


    public void createListener(final LoginUpdate u){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.e("Autenticato: ",utente.isAutenticated() ? "Si ":"NO");
                if(user!=null && utente.isAutenticated()==true){
                    Log.e("Accesso","Effettuato");
                    u.loginEffettuato();

                }
                else if(user!= null && utente.isAutenticated()==false){
                    Log.e("Accesso","Ospite");
                    u.loginNonEffettuato();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void removeListener(){
        mAuth.removeAuthStateListener(mAuthListener);
    }

    public DataUtente logUtente(){return utente;}
}
