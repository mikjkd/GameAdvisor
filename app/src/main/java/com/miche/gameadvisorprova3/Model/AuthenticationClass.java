package com.miche.gameadvisorprova3.Model;

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
import com.miche.gameadvisorprova3.MainActivity;

import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.util.concurrent.Executor;

/**
 * Created by miche on 15/10/2017.
 */

public class AuthenticationClass implements Serializable {
    private FirebaseAuth mAuth;
    private DataUtente utente;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public AuthenticationClass(){
        mAuth=FirebaseAuth.getInstance();
        utente = new DataUtente();
    }

    public AuthenticationClass(FirebaseAuth mAuth, DataUtente utente) {
        this.mAuth = mAuth;
        this.utente = utente;
    }
    public interface LoginUpdate{
        public void loginEffettuato();
        public void loginNonEffettuato();
    }
    public void logInAnonimo(){
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        mauth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                utente.setAutenticated(false);
                utente.setEmail("");
                utente.setPassword("");
            }
        });
    }
    public void login(final String Email, final String Password){
        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser()!=null){
                                utente.setAutenticated(true);
                                utente.setEmail(Email);
                                utente.setPassword(Password);

                            }
                        }
                        else{
                            Log.e("Errore ","Autenticazione non riuscita");
                        }
                    }
                });
    }

    public void signUp(final String Email,final String Password){
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser u = mAuth.getCurrentUser();
                            if(u != null){
                                utente.setAutenticated(true);
                                utente.setEmail(Email);
                                utente.setPassword(Password);
                                u.sendEmailVerification();
                            }
                        }
                    }
                });
    }


    public void createListener(final LoginUpdate u){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null && utente.isAutenticated()==true){
                    u.loginEffettuato();
                }
                else if(user!= null && utente.isAutenticated()==false){
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
