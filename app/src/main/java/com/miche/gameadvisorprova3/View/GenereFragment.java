package com.miche.gameadvisorprova3.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.miche.gameadvisorprova3.Model.DataGenere;
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
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listagenere,container,false);
        ListView listGen = rootView.findViewById(R.id.listGenere);
        adapter = new GenereAdapter(getActivity());
        //archivio.logInAnonimo();
        arg = this.getArguments();
        archivio =  arg.getParcelable("ARCHIVIO");
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
                Bundle extras = new Bundle();
                extras.putParcelable("ARCHIVIO",archivio);
                DataGenere gen = adapter.getItem(i);
                extras.putSerializable("GENERE",gen);
                Intent intent = new Intent(getContext(),GiochiByGenereActivity.class);
                intent.putExtras(extras);
                getActivity().startActivity(intent);
            }
        });

        Log.e("restituisco adapter","rootView");
        return rootView;

    }
}

