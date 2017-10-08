package com.miche.gameadvisorprova3;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.Model.DatabaseLink;
import com.miche.gameadvisorprova3.View.GenereAdapter;
import com.miche.gameadvisorprova3.View.GiocoAdapter;

public class MainActivity extends AppCompatActivity {
    private GenereAdapter adapter;
    private DatabaseLink archivio = new DatabaseLink();
    private final static String EXTRA_GIOCO = "gioco";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listGame = (ListView)findViewById(R.id.listGame);
        adapter = new GenereAdapter(this);

        archivio.logInAnonimo();
        /*
        archivio.osservaGiochi(new DatabaseLink.UpdateListener(){
            @Override
            public void giochiAggiornati() {
                adapter.update(archivio.elencoGiochi());

            }
        });
        listGame.setAdapter(adapter);

        listGame.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle extras = new Bundle();
                DataGioco gioco = adapter.getItem(i);
              //  extras.putParcelable("imagebitmap",gioco.getImmagine());

                Intent intent = new Intent(view.getContext(),DettagliGiocoActivity.class);
                intent.putExtra(EXTRA_GIOCO, gioco);
                startActivity(intent);
            }
        });*/
        archivio.osservaGenere(new DatabaseLink.UpdateGeneriListener() {
            @Override
            public void generiAggiornati() {
                adapter.update(archivio.elencoGenere());
            }
        });
        listGame.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        archivio.nonOsservaGiochi();
    }
}
