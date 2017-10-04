package com.miche.gameadvisorprova3;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.DatabaseLink;
import com.miche.gameadvisorprova3.View.GiocoAdapter;

public class MainActivity extends AppCompatActivity {
    private GiocoAdapter adapter;
    private DatabaseLink archivio = new DatabaseLink();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listGame = (ListView)findViewById(R.id.listGame);
        adapter = new GiocoAdapter(this);
        archivio.osservaGiochi(new DatabaseLink.UpdateListener(){
            @Override
            public void giochiAggiornati() {
                adapter.update(archivio.elencoGiochi());
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
