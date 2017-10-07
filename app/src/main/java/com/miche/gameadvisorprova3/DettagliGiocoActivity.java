package com.miche.gameadvisorprova3;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGioco;

public class DettagliGiocoActivity extends AppCompatActivity {

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
             if(gioco.getUrlImmagineLocale()!=null)
                ImgGioco.setImageBitmap(BitmapFactory.decodeFile(gioco.getUrlImmagineLocale()));
            //ImgGioco.setImageBitmap(gioco.getImgGioco);
            Descrizione.setText(gioco.getDescrizione());

        }


    }
}
