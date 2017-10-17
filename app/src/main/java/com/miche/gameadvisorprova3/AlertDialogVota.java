package com.miche.gameadvisorprova3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataGiocoDettaglio;
import com.miche.gameadvisorprova3.Model.DataUtente;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fabio on 16/10/2017.
 */

public class AlertDialogVota extends AlertDialog.Builder {

    private Context context;
    private AlertDialog mAlertDialog;
    private DataUtente utente;
    private AuthenticationClass auth;
    private DataGiocoDettaglio dataGiocoDettaglio;
    private FirebaseDatabase db;
    public AlertDialogVota(@NonNull Context context) {
        super(context);
        this.context=context;
        utente= new DataUtente();
        auth = new AuthenticationClass(utente,context);
    }
    public AlertDialogVota(@NonNull Context context, DataUtente utente, DataGiocoDettaglio dataGiocoDettaglio) {
        super(context);
        this.context=context;
        this.utente= utente;
        auth = new AuthenticationClass(utente,context);
        this.dataGiocoDettaglio = dataGiocoDettaglio;
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.voto_popup, null);
        final RatingBar rb = mView.findViewById(R.id.ratingBar2);
        final EditText etCommento = mView.findViewById(R.id.etCommento);
        Button AnnullaBtn = mView.findViewById(R.id.AnnullaBtn);
        Button OkBtn = mView.findViewById(R.id.OkBtn);

        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Map<String,Float> userData = new HashMap<String, Float>();
                db = FirebaseDatabase.getInstance();
                DatabaseReference ut =db.getReference().child("Utenti").child(utente.getUID());
                ut.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DatabaseReference ref;
                        if(dataSnapshot.child("Giochi").child(dataGiocoDettaglio.getKey()).exists()){
                            Float votazione = (Float) dataSnapshot.child("Giochi").child(dataGiocoDettaglio.getKey()).child("Votazione").getValue(Float.class);
                            Log.e("Votazione vecchia",votazione.toString());
                            Log.e("Numero utenti",dataGiocoDettaglio.getNumeroVotanti().toString());
                            ref = db.getReference().child("Giochi").child(dataGiocoDettaglio.getKey());
                            float vecchiovoto;
                            if(dataGiocoDettaglio.getNumeroVotanti()>1)
                                 vecchiovoto= ((dataGiocoDettaglio.getVotazione()*dataGiocoDettaglio.getNumeroVotanti())-votazione)/(dataGiocoDettaglio.getNumeroVotanti()-1);
                            else
                                vecchiovoto = 0;
                            float voto = ((vecchiovoto*(dataGiocoDettaglio.getNumeroVotanti()-1))+rb.getRating())/dataGiocoDettaglio.getNumeroVotanti();
                            ref.child("Votazione").setValue(
                                    voto
                            );
                            dataGiocoDettaglio.setVotazione(voto);
                            if(etCommento.getText().toString().isEmpty())
                                etCommento.setText("Nessun Commento");
                            ref.child("Commento").child(utente.getUID()).setValue(utente.getEmail()+": "+etCommento.getText().toString());
                            userData.put("Votazione",rb.getRating());
                            ref = db.getReference().child("Utenti").child(utente.getUID())
                                    .child("Giochi").child(dataGiocoDettaglio.getKey());
                            ref.setValue(userData);
                           ref.child("Commento").setValue(etCommento.getText().toString());

                        }else{
                            ref = db.getReference().child("Giochi").child(dataGiocoDettaglio.getKey());
                            float voto = ((dataGiocoDettaglio.getVotazione()*dataGiocoDettaglio.getNumeroVotanti())+rb.getRating())/
                                    (dataGiocoDettaglio.getNumeroVotanti()+1);
                            ref.child("Votazione").setValue(voto);
                            dataGiocoDettaglio.setVotazione(voto);
                            ref.child("Commento").child(utente.getUID()).setValue(utente.getEmail()+":"+etCommento.getText().toString());


                            dataGiocoDettaglio.setNumeroVotanti((dataGiocoDettaglio.getNumeroVotanti()+1));
                            ref.child("NumeroVotanti").setValue(dataGiocoDettaglio.getNumeroVotanti());
                            ref = db.getReference().child("Utenti").child(utente.getUID())
                                    .child("Giochi").child(dataGiocoDettaglio.getKey());
                            userData.put("Votazione",rb.getRating());
                            ref.setValue(userData);
                            ref.child("Commento").setValue(etCommento.getText().toString());

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                Toast.makeText(context,"Hai votato correttamente",Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss();
            }
        });
        AnnullaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"La votazione è stata annullata",Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss();
            }
        });

        mBuilder.setView(mView);
        mAlertDialog= mBuilder.create();
        mAlertDialog.show();
        return mAlertDialog;
    }
}
