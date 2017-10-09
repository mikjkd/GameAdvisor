package com.miche.gameadvisorprova3.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.R;

/**
 * Created by miche on 08/10/2017.
 */

public class GiochiFragment extends android.support.v4.app.Fragment{
        private GiocoAdapter adapter;
        private transient DatabaseLinkParcel archivio;
        private Bundle arg;
        private final static String EXTRA_GIOCO = "gioco";
        public GiochiFragment() { }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.listagioco,container,false);
                ListView listGame = (ListView)rootView.findViewById(R.id.listGame);
                adapter = new GiocoAdapter(getActivity());
                arg = this.getArguments();
                archivio =  arg.getParcelable("ARCHIVIO");
               // archivio.logInAnonimo();
                archivio.osservaGiochi(new DatabaseLinkParcel.UpdateListener(){
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
                        extras.putSerializable(EXTRA_GIOCO,gioco);
                    Log.e("Prima erroe","Intent");
                        Intent intent = new Intent(getContext(),DettagliGiocoActivity.class);
                        intent.putExtras(extras);
                        getActivity().startActivity(intent);
                }
        });
        Log.e("restituisco adapter","rootView");
        return rootView;

        }
}
