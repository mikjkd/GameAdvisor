package com.miche.gameadvisorprova3.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miche.gameadvisorprova3.MainActivity;
import com.miche.gameadvisorprova3.View.GiocoAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    private FirebaseStorage storage;
    private StorageReference storageRef;
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
                    try {
                        dg.setImmagine(scaricaImmagine(dg.getURLimg()));
                    }catch (Exception ex){
                        Log.w("non funziona ","marron");
                    }
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

    public Bitmap scaricaImmagine(final String imgName)throws  Exception{
        storage = FirebaseStorage.getInstance();
        final Bitmap img;
        final File localFile;
        storageRef = storage.getReference().child("Giochi/"+imgName);
        Log.w("Scaricata immagine: ",storageRef.toString());
        localFile = File.createTempFile(imgName, ".jpg");
        Log.w("creato temp file",localFile.toString());
        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.w("immagine scaricata: ", imgName.toString());
            }

        });
        Log.w("prima di creare bitmap",localFile.toString());
       img = BitmapFactory.decodeFile(localFile.toString());
       // return img;
        return img;
    }

    public List<DataGioco> elencoGiochi(){
        return giochi;
    }
}
