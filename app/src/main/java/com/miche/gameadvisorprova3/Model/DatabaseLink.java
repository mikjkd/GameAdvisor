package com.miche.gameadvisorprova3.Model;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miche on 03/10/2017.
 */

public class DatabaseLink {
    private String DB_GIOCHI = "Giochi";
    private String DB_GENERE = "Genere";
    private String DB_AVVENTURA="Avventura";
    private String DB_AZIONE= "Azione";
    private String DB_CORSE = "Corse";
    private String DB_SPARATUTTO="Sparatutto";

    private ArrayList<DataGioco> giochi;
    private ValueEventListener listenerGiochi;
    public DatabaseLink(){giochi= new ArrayList<>();}

    public interface UpdateListener{
        void giochiAggiornati();
    }

    public void osservaGiochi(final UpdateListener notifica){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child(DB_GIOCHI);
        listenerGiochi = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giochi.clear();
                for(DataSnapshot e: dataSnapshot.getChildren()){

                    DataGioco dg = e.getValue(DataGioco.class);
                    dg.setKey(e.getKey());
                    giochi.add(dg);
                    Log.w("children", dg.getKey());
                    Log.d("genere", "Value Genere is: " + dg.getGenere());
                }
                notifica.giochiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addValueEventListener(listenerGiochi);
    }
    public void nonOsservaGiochi(){
        if(listenerGiochi!=null){
            FirebaseDatabase.getInstance().getReference("gameadvisorprova-ab95b").removeEventListener(listenerGiochi);
        }
    }
    public List<DataGioco> elencoGiochi(){
        return giochi;
    }
}
