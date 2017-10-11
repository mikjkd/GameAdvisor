package com.miche.gameadvisorprova3.View;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.DataGenere;
import com.miche.gameadvisorprova3.Model.DataGioco;
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
    private final static String EXTRA_GIOCO = "GIOCOKEY";
    private final static String EXTRA_ARCHIVIO = "ARCHIVIO";
    private final static String EXTRA_GENERE = "GENERE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagiochibygenere);
        Intent intent = getIntent();
        adapter = new GiocoAdapter(GiochiByGenereActivity.this);
        listGame =(ListView) findViewById(R.id.listGBG);
        archivio = intent.getParcelableExtra(EXTRA_ARCHIVIO);
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
                    Intent intent = new Intent(GiochiByGenereActivity.this,DettagliGiocoActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
        }
    }
}
