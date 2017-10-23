package com.miche.gameadvisorprova3.View.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataGenere;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.Model.DatabaseLink;
import com.miche.gameadvisorprova3.R;
import com.miche.gameadvisorprova3.View.Adapter.GenereAdapter;
import com.miche.gameadvisorprova3.View.Activity.GiochiByGenereActivity;

/**
 * Created by miche on 08/10/2017.
 */

public class GenereFragment extends android.support.v4.app.Fragment{
    private GenereAdapter adapter;
    private DatabaseLink archivio ;

    private final String EXTRA_GENERE = "GENERE";

    public GenereFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listagenere,container,false);
        ListView listGen = rootView.findViewById(R.id.listGenere);
        adapter = new GenereAdapter(getActivity());
        archivio = DatabaseLink.getInstance();

        archivio.osservaGenere(new DatabaseLink.UpdateGeneriListener(){
            @Override
            public void generiAggiornati() {
                adapter.update(archivio.elencoGenere());
            }
        });
        listGen.setAdapter(adapter);
        listGen.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle extras = new Bundle();
                DataGenere gen = adapter.getItem(i);
                extras.putSerializable(EXTRA_GENERE,gen);
                Intent intent = new Intent(getContext(),GiochiByGenereActivity.class);
                intent.putExtras(extras);
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        archivio.nonOsservaGeneri();
    }
}

