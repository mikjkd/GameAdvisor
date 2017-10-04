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
    private ArrayList<DataGioco> giochi;
    private ValueEventListener listenerGiochi;
    public DatabaseLink(){giochi= new ArrayList<>();}

    public interface UpdateListener{
        void giochiAggiornati();
    }

    public void osservaGiochi(final UpdateListener notifica){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        listenerGiochi = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giochi.clear();
                for(DataSnapshot e: dataSnapshot.getChildren()){
                    Log.w("children", e.toString());
                    DataGioco dg = e.getValue(DataGioco.class);
                    giochi.add(dg);
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
