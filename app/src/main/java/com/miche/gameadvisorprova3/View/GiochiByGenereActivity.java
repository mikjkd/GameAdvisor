package com.miche.gameadvisorprova3.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.AlertDialogUtente;
import com.miche.gameadvisorprova3.MainActivity;
import com.miche.gameadvisorprova3.Model.DataGenere;
import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.R;

import java.util.List;

/**
 * Created by miche on 09/10/2017.
 */

public class GiochiByGenereActivity  extends AppCompatActivity {
    private ListView listGame;
    private DatabaseLinkParcel archivio;
    private DataGenere genereClick;
    private GiocoAdapter adapter;
    private DataUtente utente;
    private final static String EXTRA_GIOCO = "GIOCOKEY";
    private final static String EXTRA_ARCHIVIO = "ARCHIVIO";
    private final static String EXTRA_GENERE = "GENERE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagiochibygenere);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        adapter = new GiocoAdapter(GiochiByGenereActivity.this);
        listGame =(ListView) findViewById(R.id.listGBG);
        archivio = intent.getParcelableExtra(EXTRA_ARCHIVIO);
        utente = (DataUtente)intent.getSerializableExtra("UTENTE");
        genereClick = (DataGenere) intent.getSerializableExtra(EXTRA_GENERE);
        if(genereClick!=null && archivio!=null){
           archivio.osservaGiocoByGenere(genereClick.getKeyGenere(),new DatabaseLinkParcel.UpdateGBGListener(){
               @Override
               public void gbgAggiornati() {
                   adapter.update(archivio.elencoGiocoByGenere());
               }
           });
            listGame.setAdapter(adapter);
            listGame.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle extras = new Bundle();
                    String giocoKey = adapter.getItem(i).getKey();
                    extras.putSerializable(EXTRA_GIOCO,giocoKey);
                    extras.putParcelable(EXTRA_ARCHIVIO,archivio);
                    extras.putSerializable("UTENTE",utente);
                    Intent intent = new Intent(GiochiByGenereActivity.this,NuovoDettagliGioco.class);
                    intent.putExtras(extras);
                    startActivityForResult(intent,1);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        archivio.nonOsservaGiochiByGenere();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.e("On Activity ","result");
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    utente =(DataUtente)data.getSerializableExtra("UTENTE");
                    if(utente!=null)
                        Log.e("Utente on activity",utente.getEmail());
                }
                break;
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle b = new Bundle();
                b.putSerializable("UTENTE",utente);
                Intent resultIntent = new Intent();
                resultIntent.putExtras(b);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
            case R.id.utente:
                AlertDialogUtente adu = new AlertDialogUtente(GiochiByGenereActivity.this,utente);
                adu.show();
                break;
        }

        return true;
    }
}
