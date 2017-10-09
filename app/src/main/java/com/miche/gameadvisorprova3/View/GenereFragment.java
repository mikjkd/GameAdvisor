package com.miche.gameadvisorprova3.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.DatabaseLinkParcel;
import com.miche.gameadvisorprova3.R;

/**
 * Created by miche on 08/10/2017.
 */

public class GenereFragment extends android.support.v4.app.Fragment{
    private GenereAdapter adapter;
    private transient DatabaseLinkParcel archivio ;
    private Bundle arg;
    public GenereFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listagenere,container,false);
        ListView listGen = rootView.findViewById(R.id.listGenere);
        adapter = new GenereAdapter(getActivity());
        //archivio.logInAnonimo();
        arg = this.getArguments();
        archivio = (DatabaseLinkParcel) arg.getParcelable("ARCHIVIO");
        archivio.osservaGenere(new DatabaseLinkParcel.UpdateGeneriListener(){
            @Override
            public void generiAggiornati() {
                adapter.update(archivio.elencoGenere());
            }
        });
        listGen.setAdapter(adapter);
        listGen.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        Log.e("restituisco adapter","rootView");
        return rootView;

    }
}

