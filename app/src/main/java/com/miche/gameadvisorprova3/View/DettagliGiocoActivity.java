package com.miche.gameadvisorprova3.View;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.R;

import java.io.Serializable;

public class DettagliGiocoActivity extends AppCompatActivity{

    private final static String EXTRA_GIOCO = "gioco";

    private TextView Titolo;
    private ImageView ImgGioco;
    private TextView Descrizione;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli_gioco);
        Titolo = (TextView) findViewById(R.id.tvTitolo);
        ImgGioco = (ImageView) findViewById(R.id.ivGioco);
        Descrizione = (TextView) findViewById(R.id.tvDescrizione);
        Intent intent = getIntent();
        DataGioco gioco = (DataGioco) intent.getSerializableExtra(EXTRA_GIOCO);
        if(gioco != null){
            Titolo.setText(gioco.getTitolo());
             if(gioco.getUrlIconaLocale()!=null)
                ImgGioco.setImageBitmap(BitmapFactory.decodeFile(gioco.getUrlImmagineLocale()));
            Descrizione.setText(gioco.getDescrizione());
        }
    }
}
