package com.miche.gameadvisorprova3.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.miche.gameadvisorprova3.AlertDialogLogin;
import com.miche.gameadvisorprova3.AlertDialogUtente;
import com.miche.gameadvisorprova3.AlertDialogVota;
import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataGiocoDettaglio;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.R;


public class NuovoDettagliGioco extends AppCompatActivity {
    private final static String EXTRA_GIOCO = "GIOCOKEY";
    private final static String EXTRA_ARCHIVIO="ARCHIVIO";
    private String key;
    private RatingBar voti;
    private TextView Titolo;
    private Button Votabtn;
    private ImageView ImgGioco;
    private TextView Descrizione;
    private DatabaseLinkParcel archivio;
    private DataGiocoDettaglio gioco;
    private DataUtente utente;
    private AuthenticationClass auth  ;
    private TextView mediaVoti;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli_gioco);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Titolo = (TextView) findViewById(R.id.tvTitolo);
        ImgGioco = (ImageView) findViewById(R.id.ivGioco);
        Votabtn =(Button) findViewById(R.id.Votabtn);
         mediaVoti = (TextView) findViewById(R.id.mediaVoti);
        voti = (RatingBar)findViewById(R.id.ratingBar);
        Intent intent = getIntent();
        archivio = intent.getParcelableExtra(EXTRA_ARCHIVIO);
        utente =(DataUtente)intent.getSerializableExtra("UTENTE");
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
                    mediaVoti.setText(String.valueOf(gioco.getVotazione()));
                    voti.setRating(gioco.getVotazione());
                    expandableListView.setAdapter(adapter);
                }
            }
        });
        Votabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!utente.isAutenticated()){
                    auth = new AuthenticationClass(utente,NuovoDettagliGioco.this);
                    auth.logout();
                    AlertDialogLogin adl = new AlertDialogLogin(NuovoDettagliGioco.this,utente);
                    adl.show();
                }
                else{
                    AlertDialogVota adv = new AlertDialogVota(NuovoDettagliGioco.this,utente);
                    adv.show();
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
