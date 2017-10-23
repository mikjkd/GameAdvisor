package com.miche.gameadvisorprova3.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miche on 15/10/2017.
 */

public class AuthenticationClass {
    private static final String Autenticazione = "AUTENTICAZIONE";
    private static final String Preferenze = "authPref";
    private static final String Ospite = "Ospite";
    private static final String Utenti = "Utenti";
    private static final String Giochi = "Giochi";
    private static final String Votazione = "Votazione";
    private static final String Commento = "Commento";
    private static final String NumeroVotanti ="NumeroVotanti";

    private static AuthenticationClass authenticationClass = null;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static Context cont;

    private DataUtente utente;
   private AuthenticationClass(){
       utente= new DataUtente();
       SharedPreferences settings = cont.getSharedPreferences(Autenticazione,0);
       utente.setAutenticated(settings.getBoolean(Preferenze,false));

   }

    public static AuthenticationClass getInstance(Context context){
        if(authenticationClass == null){
            cont = context;
            authenticationClass = new AuthenticationClass();
        }
        return authenticationClass;
    }

    public void setContext(Context context){
        this.cont=context;
    }
    public Context getContext(){
        return this.cont;
    }

    public interface LoginUpdate{
        void loginEffettuato();
        void loginNonEffettuato();
        void erroreAutenticazione();
    }

    private void setAuth(boolean val){
        utente.setAutenticated(val);
        SharedPreferences settings = cont.getSharedPreferences(Autenticazione, 0);
        SharedPreferences.Editor editor = settings.edit().putBoolean(Preferenze, utente.isAutenticated());
        editor.apply();
    }

    public void logInAnonimo(){
        setAuth(false);
        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                utente.setEmail(Ospite);
                utente.setPassword(Ospite);
            }
        });
    }
    public void login(final String Email, final String Password, final LoginUpdate loginUpdate){
        setAuth(true);
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
                            setAuth(false);
                            utente.setPassword(Ospite);
                            utente.setEmail(Ospite);
                            loginUpdate.erroreAutenticazione();
                        }
                    }
                });
    }

    public void signUp(final String Email,final String Password, final LoginUpdate loginUpdate){
        setAuth(true);
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser u = mAuth.getCurrentUser();
                            if(u != null){
                                utente.setEmail(Email);
                                utente.setPassword(Password);
                                u.sendEmailVerification();
                                db = FirebaseDatabase.getInstance();
                                //creazione utente nel database
                                Map<String,String> userData = new HashMap<>();
                                userData.put("Email",utente.getEmail());
                                DatabaseReference ref = db.getReference()
                                        .child(Utenti)
                                        .child(u.getUid());
                                ref.setValue(userData);
                                utente.setUID(u.getUid());
                            }
                        }
                        else {
                            Log.e("Errore ","Registrazione non riuscita");
                            setAuth(false);
                            utente.setPassword(Ospite);
                            utente.setEmail(Ospite);
                            loginUpdate.erroreAutenticazione();
                        }
                    }
                });
    }

    public void logout(){
        setAuth(false);
        utente.setEmail(Ospite);
        utente.setPassword(Ospite);
        utente.setUID("");
        mAuth.signOut();
    }


    public void vota(final DataGiocoDettaglio dataGiocoDettaglio, final String commento,final Float rating){
        final Map<String,Float> userData = new HashMap<String, Float>();
        db = FirebaseDatabase.getInstance();
        DatabaseReference ut =db.getReference()
                .child(Utenti)
                .child(utente.getUID());
        ut.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference ref;
                if(dataSnapshot.child(Giochi).child(dataGiocoDettaglio.getKey()).exists()){
                    Float votazione = (Float) dataSnapshot
                            .child(Giochi)
                            .child(dataGiocoDettaglio.getKey())
                            .child(Votazione)
                            .getValue(Float.class);
                    ref = db.getReference()
                            .child(Giochi)
                            .child(dataGiocoDettaglio.getKey());
                    float vecchiovoto;
                    if(dataGiocoDettaglio.getNumeroVotanti()>1)
                        vecchiovoto= ((dataGiocoDettaglio.getVotazione()*dataGiocoDettaglio.getNumeroVotanti())-votazione)/(dataGiocoDettaglio.getNumeroVotanti()-1);
                    else
                        vecchiovoto = 0;
                    float voto = ((vecchiovoto*(dataGiocoDettaglio.getNumeroVotanti()-1))+rating)/dataGiocoDettaglio.getNumeroVotanti();
                    ref.child(Votazione)
                            .setValue(
                                voto
                            );
                    dataGiocoDettaglio.setVotazione(voto);

                    ref.child(Commento)
                            .child(utente.getUID())
                            .setValue(utente.getEmail()+":  "+commento.toString());
                    userData.put(Votazione,rating);
                    ref = db.getReference()
                            .child(Utenti).child(utente.getUID())
                            .child(Giochi).child(dataGiocoDettaglio.getKey());
                    ref.setValue(userData);
                    ref.child(Commento).setValue(commento.toString());

                }else{
                    ref = db.getReference().child(Giochi).child(dataGiocoDettaglio.getKey());
                    float voto = ((dataGiocoDettaglio.getVotazione()*dataGiocoDettaglio.getNumeroVotanti())+rating)/
                            (dataGiocoDettaglio.getNumeroVotanti()+1);
                    ref.child(Votazione).setValue(voto);
                    dataGiocoDettaglio.setVotazione(voto);
                    ref.child(Commento).child(utente.getUID()).setValue(utente.getEmail()+":  "+commento.toString());


                    dataGiocoDettaglio.setNumeroVotanti((dataGiocoDettaglio.getNumeroVotanti()+1));
                    ref.child(NumeroVotanti).setValue(dataGiocoDettaglio.getNumeroVotanti());
                    ref = db.getReference().child(Utenti).child(utente.getUID())
                            .child(Giochi).child(dataGiocoDettaglio.getKey());
                    userData.put(Votazione,rating);
                    ref.setValue(userData);
                    ref.child(Commento).setValue(commento);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(cont,"Hai votato correttamente",Toast.LENGTH_SHORT).show();
    }

    public void createListener(final LoginUpdate u){
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.e("Autenticato: ",utente.isAutenticated() ? "Si ":"NO");

                if(user!=null && utente.isAutenticated()==true){
                    utente.setEmail(user.getEmail());
                    utente.setUID(user.getUid());

                    Log.e("Accesso","Effettuato");
                    u.loginEffettuato();

                }
                else if(user!= null && utente.isAutenticated()==false){
                    utente.setEmail(Ospite);
                    utente.setUID(Ospite);
                    Log.e("Accesso","Ospite");
                    u.loginNonEffettuato();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }
    

    public DataUtente getUtente(){return utente;}

}
