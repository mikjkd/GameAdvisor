package com.miche.gameadvisorprova3.Model;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miche on 03/10/2017.
 */

public class DatabaseLink {
    private String DB_GIOCHI = "Giochi";
    private String DB_GENERE = "Genere";
    private String DB_LISTGIOCHI="ListaGiochi";


    private ArrayList<DataGioco> giochi;
    private ArrayList<DataGenere> generi;
    private ArrayList<DataGioco> gbg;

    private static DatabaseLink databaseLink = null;
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static FirebaseStorage storage = FirebaseStorage.getInstance();

    private DataGiocoDettaglio gioco;
    private ValueEventListener listenerGiochi;
    private ValueEventListener listenerGenere;
    private ValueEventListener listenerGiochiByGenere;

    private DatabaseLink(){
        gioco = new DataGiocoDettaglio();
        gbg = new ArrayList<>();
        giochi= new ArrayList<>();
        generi = new ArrayList<>();
    }

    public static DatabaseLink getInstance(){
        if(databaseLink == null){
            databaseLink = new DatabaseLink();
            db.setPersistenceEnabled(true);
        }
        return databaseLink;
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
    public interface UpdateGBGListener{
        void gbgAggiornati();
    }
    public void osservaGiochi(final UpdateListener notifica){
        DatabaseReference ref = db.getReference().child(DB_LISTGIOCHI);

        listenerGiochi = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                giochi.clear();
                for(DataSnapshot e: dataSnapshot.getChildren()){
                    final DataGioco dg = e.getValue(DataGioco.class);
                    dg.setKey(e.getKey());
                    giochi.add(dg);
                    notifica.giochiAggiornati();
                    scaricaImmagine(dg, new DatabaseLink.BitmapListener(){
                        @Override
                        public void BitmapPronta() {
                            notifica.giochiAggiornati();
                        }
                    });
                }
                notifica.giochiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addValueEventListener(listenerGiochi);
    }

    public void cercaGioco(String keyGioco,final UpdateListener notifica){
        DatabaseReference ref = db.getReference().child(DB_GIOCHI).child(keyGioco);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gioco= dataSnapshot.getValue(DataGiocoDettaglio.class);
                gioco.setKey(dataSnapshot.getKey());
                if(dataSnapshot.hasChild("Commento")){
                    //Log.e("commento",dataSnapshot.child("Commento").getValue(String.class).toString());
                    Log.e("Ok ci sono","i commenti");
                    for (DataSnapshot c: dataSnapshot.child("Commento").getChildren())
                        gioco.addCommenti(c.getValue(String.class));

                }
                else {
                    Log.e("non ci sono","i commenti");
                    gioco.setCommenti(null);
                }
                Log.e("valori: ",dataSnapshot.getKey());
                scaricaImmagineHD(gioco,new DatabaseLink.BitmapListener(){
                    @Override
                    public void BitmapPronta() {
                        notifica.giochiAggiornati();
                    }
                });
                notifica.giochiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void nonOsservaGiochi(){
        if(listenerGiochi!=null){
            db.getReference().removeEventListener(listenerGiochi);
        }
    }

    public void osservaGenere(final UpdateGeneriListener notificaGenere){
        DatabaseReference ref = db.getReference().child(DB_GENERE);

        listenerGenere = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                generi.clear();
                for (DataSnapshot e: dataSnapshot.getChildren()){
                    final DataGenere gen = e.getValue(DataGenere.class);
                    gen.setKeyGenere(e.getKey());
                    generi.add(gen);

                }
                scaricaIcona(new DatabaseLink.BitmapListener(){
                    @Override
                    public void BitmapPronta() {
                        notificaGenere.generiAggiornati();
                    }
                });
                notificaGenere.generiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        ref.addValueEventListener(listenerGenere);
    }

    public void nonOsservaGeneri(){
        if(listenerGenere!=null){
            db.getReference().removeEventListener(listenerGenere);
        }
    }
    public void osservaGiocoByGenere(String genereCercato,final UpdateGBGListener notifica){
        DatabaseReference ref =db.getReference().child(DB_GENERE).child(genereCercato);

        listenerGiochiByGenere = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gbg.clear();
                for (DataSnapshot e: dataSnapshot.getChildren()){
                    final DataGenere gen = e.getValue(DataGenere.class);
                    gen.setKeyGioco(e.getKey());
                    gbg.add(giochi.get(cercaGiocoKey(gen.getKeyGioco())));
                    if(gbg == null)
                        Log.e("OsservaGioco","null");
                }
                notifica.gbgAggiornati();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        ref.addValueEventListener(listenerGiochiByGenere);
    }

    public void nonOsservaGiochiByGenere(){
        if(listenerGiochiByGenere!=null){
            db.getReference().removeEventListener(listenerGiochiByGenere);
        }
    }
    public void scaricaImmagine(final DataGioco dg,final BitmapListener immagineCaricata){
        StorageReference storageRef = storage.getReference();
        final File localFile;
        try {
            localFile = File.createTempFile(dg.getURLimg(),".jpg");
            storageRef.child("Iconegiochi/"+dg.getURLimg()+".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    dg.setUrlIconaLocale(localFile.getAbsolutePath());
                    immagineCaricata.BitmapPronta();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("ScaricaImmagine"," Fallito");
                }
            });
        } catch (IOException e) {
            Log.e("IOEXCEPTION","errore try catch");
        }
    }
    private void scaricaImmagineHD(final DataGiocoDettaglio dg,final BitmapListener immagineCaricata){
        StorageReference storageRef = storage.getReference();

        final File localFile;
        try {
            localFile = File.createTempFile("hd"+dg.getURLimg(),".jpg");
            storageRef.child("Giochi/"+dg.getURLimg()+".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    dg.setUrlImmagineLocale(localFile.getAbsolutePath());
                    immagineCaricata.BitmapPronta();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("ScaricaImmagineHD"," Fallito");
                }
            });
        } catch (IOException e) {
            Log.e("errore","errore try catch");
        }
    }
    public void scaricaIcona(final DatabaseLink.BitmapListener immagineCaricata ){

        StorageReference storageRef = storage.getReference();

        for (final DataGenere dge : generi){

            final File localFile;

            try{
                localFile = File.createTempFile(dge.getKeyGenere(),".jpg");
                storageRef.child("Iconegeneri/"+dge.getKeyGenere()+".jpg").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        dge.setPathlocale(localFile.getAbsolutePath());

                        immagineCaricata.BitmapPronta();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Miss","Fallito");
                    }
                });
            } catch (IOException e) {
                Log.e("Errore","Try Catch");
            }

        }

    }

    private int cercaGiocoKey(String key){
        boolean trovato=false;
        int index = 0;
        while(!trovato && index <giochi.size()){
            if(giochi.get(index).getKey().equals(key)){ return index;}
            ++index;
        }
        return -1;
    }


    public List<DataGioco> elencoGiochi(){
        return giochi;
    }
    public List<DataGenere> elencoGenere() { return generi; }
    public List<DataGioco> elencoGiocoByGenere(){return gbg;}
    public DataGiocoDettaglio giocoAggiornato(){return gioco;}

}
