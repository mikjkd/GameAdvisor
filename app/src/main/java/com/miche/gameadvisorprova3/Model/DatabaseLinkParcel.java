package com.miche.gameadvisorprova3.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Created by miche on 03/10/2017.
 */

public class DatabaseLinkParcel implements Parcelable{
    private String DB_GIOCHI = "Giochi";
    private String DB_GENERE = "Genere";
    private String DB_AVVENTURA="Avventura";
    private String DB_AZIONE= "Azione";
    private String DB_CORSE = "Corse";
    private String DB_SPARATUTTO="Sparatutto";

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ArrayList<DataGioco> giochi;
    private ArrayList<DataGenere> generi;
    private ValueEventListener listenerGiochi;
    private ValueEventListener listenerGenere;
    private Bitmap immagine;
    public DatabaseLinkParcel(){
        giochi= new ArrayList<>();
        generi = new ArrayList<>();
    }
    public DatabaseLinkParcel(Parcel val){
        readFromParcel(val);
    }

    public static final Creator<DatabaseLinkParcel> CREATOR = new Creator<DatabaseLinkParcel>() {
        @Override
        public DatabaseLinkParcel createFromParcel(Parcel in) {
            return new DatabaseLinkParcel(in);
        }

        @Override
        public DatabaseLinkParcel[] newArray(int size) {
            return new DatabaseLinkParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest,int flags){
        dest.writeList(giochi);
        dest.writeList(generi);
    }
    public void readFromParcel(Parcel val){
        giochi = val.readArrayList(DataGioco.class.getClassLoader());
        generi = val.readArrayList(DataGenere.class.getClassLoader());
    }


    public interface UpdateListener{
        void giochiAggiornati();
    }
    public interface BitmapListener{
        void BitmapPronta();
    }
    public interface UpdateGeneriListener{
        void generiAggiornati();
    }

    public void logInAnonimo(){
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        mauth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
            }
        });
    }
    public void osservaGiochi(final UpdateListener notifica){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child(DB_GIOCHI);

        listenerGiochi = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giochi.clear();
                for(DataSnapshot e: dataSnapshot.getChildren()){
                    final DataGioco dg = e.getValue(DataGioco.class);
                    dg.setKey(e.getKey());
                    giochi.add(dg);
                }

                // notifica.giochiAggiornati();
                scaricaImmagine(new DatabaseLinkParcel.BitmapListener(){
                    @Override
                    public void BitmapPronta() {
                        notifica.giochiAggiornati();
                    }
                });
                scaricaImmagineHD();
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

    public void osservaGenere(final UpdateGeneriListener notificaGenere){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child(DB_GENERE);

        listenerGenere = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                generi.clear();
                for (DataSnapshot e: dataSnapshot.getChildren()){
                    //Log.e("datasnap: ",e.getKey());
                    final DataGenere gen = e.getValue(DataGenere.class);
                    gen.setKeyGenere(e.getKey());
                    generi.add(gen);

                }
                notificaGenere.generiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        ref.addValueEventListener(listenerGenere);
    }

    public void scaricaImmagine(final BitmapListener immagineCaricata){
        final Bitmap[] bmp = new Bitmap[1];
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        //Log.e("Scarica immagine","dim: "+giochi.size());
        for (final DataGioco dg : giochi){
            final File localFile;
            //Log.e("Scarica","Immagine"+dg.getURLimg());
            try {
                localFile = File.createTempFile(dg.getURLimg(),".jpg");
                storageRef.child("Iconegiochi/"+dg.getURLimg()+".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        dg.setUrlIconaLocale(localFile.getAbsolutePath());
                        Log.e("path: ",localFile.getAbsolutePath());
                        immagineCaricata.BitmapPronta();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Niente"," Fallito e che cazz");
                    }
                });
            } catch (IOException e) {
                Log.e("errore","errore try catch");
            }
        }
    }
    private void scaricaImmagineHD(){
        final Bitmap[] bmp = new Bitmap[1];
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        for (final DataGioco dg : giochi){
            final File localFile;
            //Log.e("Scarica","Immagine"+dg.getURLimg());
            try {
                localFile = File.createTempFile("hd"+dg.getURLimg(),".jpg");
                storageRef.child("Giochi/"+dg.getURLimg()+".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        dg.setUrlImmagineLocale(localFile.getAbsolutePath());
                        Log.e("path: ",localFile.getAbsolutePath());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Niente"," Fallito e che cazz");
                    }
                });
            } catch (IOException e) {
                Log.e("errore","errore try catch");
            }
        }
    }
    public List<DataGioco> elencoGiochi(){
        return giochi;
    }
    public List<DataGenere> elencoGenere() { return generi; }

}
