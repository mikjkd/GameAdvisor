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

import com.miche.gameadvisorprova3.Model.DataGenere;
import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.Model.DatabaseLink;
import com.miche.gameadvisorprova3.R;

/**
 * Created by miche on 08/10/2017.
 */

public class GenereFragment extends android.support.v4.app.Fragment{
    private GenereAdapter adapter;
    private DatabaseLink archivio = new DatabaseLink();

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
        archivio.logInAnonimo();
        archivio.osservaGenere(new DatabaseLink.UpdateGeneriListener(){
            @Override
            public void generiAggiornati() {
                adapter.update(archivio.elencoGenere());
            }
        });
        listGen.setAdapter(adapter);

        Log.e("restituisco adapter","rootView");
        return rootView;

    }
}

