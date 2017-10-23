package com.miche.gameadvisorprova3.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.miche.gameadvisorprova3.Model.DatabaseLink;
import com.miche.gameadvisorprova3.R;
import com.miche.gameadvisorprova3.View.Adapter.GiocoAdapter;
import com.miche.gameadvisorprova3.View.Activity.NuovoDettagliGioco;


/**
 * Created by miche on 08/10/2017.
 */

public class GiochiFragment extends android.support.v4.app.Fragment{
        private GiocoAdapter adapter;
        private DatabaseLink archivio;
        private final static String EXTRA_GIOCO = "GIOCOKEY";

        public GiochiFragment() { }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.listagioco,container,false);
                ListView listGame = rootView.findViewById(R.id.listGame);
                archivio =  DatabaseLink.getInstance();
                adapter = new GiocoAdapter(getActivity());
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
                                String giocoKey = adapter.getItem(i).getKey();
                                extras.putSerializable(EXTRA_GIOCO,giocoKey);
                                Intent intent = new Intent(getContext(),NuovoDettagliGioco.class);
                                intent.putExtras(extras);
                                getActivity().startActivity(intent);
                          }
                 });
                return rootView;

        }

        @Override
        public void onDestroy() {
                super.onDestroy();
                archivio.nonOsservaGiochi();
        }
}
