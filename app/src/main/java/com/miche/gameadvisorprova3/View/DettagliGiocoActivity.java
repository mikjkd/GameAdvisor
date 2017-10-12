package com.miche.gameadvisorprova3.View;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.Model.DataGiocoDettaglio;
import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.R;

import java.io.Serializable;

public class DettagliGiocoActivity extends AppCompatActivity{

    private final static String EXTRA_GIOCO = "GIOCOKEY";
    private final static String EXTRA_ARCHIVIO="ARCHIVIO";
    private String key;
    private TextView Titolo;
    private ImageView ImgGioco;
    private TextView Descrizione;
    private DatabaseLinkParcel archivio;
    private DataGiocoDettaglio gioco;

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli_gioco);


        //expandableListView = (ExpandableListView) findViewById(R.id.elvBio);

        Titolo = (TextView) findViewById(R.id.tvTitolo);
        ImgGioco = (ImageView) findViewById(R.id.ivGioco);
        //Descrizione = (TextView) findViewById(R.id.tvDescrizione);
        Intent intent = getIntent();
        archivio = intent.getParcelableExtra(EXTRA_ARCHIVIO);
        key =(String) intent.getSerializableExtra(EXTRA_GIOCO);
        archivio.cercaGioco(key, new DatabaseLinkParcel.UpdateListener() {
            @Override
            public void giochiAggiornati() {
               if( (gioco=archivio.giocoAggiornato())!=null){
                   Titolo.setText(gioco.getTitolo());
                   if(gioco.getUrlImmagineLocale()!=null)
                       ImgGioco.setImageBitmap(BitmapFactory.decodeFile(gioco.getUrlImmagineLocale()));
                   //Descrizione.setText(gioco.getDescrizione());
               }
            }
        });
    }
}
