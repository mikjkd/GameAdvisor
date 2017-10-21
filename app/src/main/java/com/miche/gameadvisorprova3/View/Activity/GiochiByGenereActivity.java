package com.miche.gameadvisorprova3.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.View.AlertDialog.AlertDialogUtente;
import com.miche.gameadvisorprova3.Model.DataGenere;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.Model.DatabaseLink;
import com.miche.gameadvisorprova3.R;
import com.miche.gameadvisorprova3.View.Adapter.GiocoAdapter;

/**
 * Created by miche on 09/10/2017.
 */

public class GiochiByGenereActivity  extends AppCompatActivity {
    private ListView listGame;
    private DataGenere genereClick;
    private GiocoAdapter adapter;
    private DataUtente utente;
    private final static String EXTRA_GIOCO = "GIOCOKEY";
    private final static String EXTRA_GENERE = "GENERE";

    private DatabaseLink archivio;
    private AuthenticationClass mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagiochibygenere);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        adapter = new GiocoAdapter(GiochiByGenereActivity.this);
        listGame =(ListView) findViewById(R.id.listGBG);
        archivio = DatabaseLink.getInstance();
        mAuth = AuthenticationClass.getInstance(this);
        utente = mAuth.getUtente();
        genereClick = (DataGenere) intent.getSerializableExtra(EXTRA_GENERE);
        if(genereClick!=null && archivio!=null){
           archivio.osservaGiocoByGenere(genereClick.getKeyGenere(),new DatabaseLink.UpdateGBGListener(){
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
                    Intent intent = new Intent(GiochiByGenereActivity.this,NuovoDettagliGioco.class);
                    intent.putExtras(extras);
                    startActivity(intent);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.utente:
                AlertDialogUtente adu = new AlertDialogUtente(GiochiByGenereActivity.this);
                adu.show();
                break;
        }

        return true;
    }

}
