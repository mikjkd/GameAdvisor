package com.miche.gameadvisorprova3.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.miche.gameadvisorprova3.AlertDialogUtente;
import com.miche.gameadvisorprova3.Model.DataGiocoDettaglio;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.R;


public class NuovoDettagliGioco extends AppCompatActivity {
    private final static String EXTRA_GIOCO = "GIOCOKEY";
    private final static String EXTRA_ARCHIVIO="ARCHIVIO";
    private String key;
    private TextView Titolo;
    private ImageView ImgGioco;
    private TextView Descrizione;
    private DatabaseLinkParcel archivio;
    private DataGiocoDettaglio gioco;
    private DataUtente utente;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli_gioco);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Titolo = (TextView) findViewById(R.id.tvTitolo);
        ImgGioco = (ImageView) findViewById(R.id.ivGioco);
        Intent intent = getIntent();
        archivio = intent.getParcelableExtra(EXTRA_ARCHIVIO);
        utente =(DataUtente)intent.getSerializableExtra("UTENTE");
        Log.e("autenticato? ",utente.isAutenticated() ? "SI":"NO");
        key =(String) intent.getSerializableExtra(EXTRA_GIOCO);
        archivio.cercaGioco(key, new DatabaseLinkParcel.UpdateListener() {
            @Override
            public void giochiAggiornati() {
                if( (gioco=archivio.giocoAggiornato())!=null){
                    Titolo.setText(gioco.getTitolo());
                    if(gioco.getUrlImmagineLocale()!=null)
                        ImgGioco.setImageBitmap(BitmapFactory.decodeFile(gioco.getUrlImmagineLocale()));
                    //Descrizione.setText(gioco.getDescrizione());
                    expandableListView = (ExpandableListView) findViewById(R.id.elvBio);
                    ELVAdapter adapter = new ELVAdapter(NuovoDettagliGioco.this,gioco);
                    expandableListView.setAdapter(adapter);
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("UTENTE",utente);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
            case R.id.utente:
                Log.e("autenticato? ",utente.isAutenticated() ? "SI":"NO");
                AlertDialogUtente adu = new AlertDialogUtente(NuovoDettagliGioco.this,utente);
                adu.show();
                break;
        }

        return true;
    }
}
