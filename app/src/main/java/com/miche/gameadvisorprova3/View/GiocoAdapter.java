package com.miche.gameadvisorprova3.View;

/**
 * Created by miche on 04/10/2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by miche on 03/10/2017.
 */

public class GiocoAdapter extends BaseAdapter {

    private final Context context;
    private List<DataGioco> giochi = Collections.emptyList();
    public  GiocoAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {return 0;}

    @Override
    public DataGioco getItem(int i) {return null;}

    @Override
    public long getItemId(int i) {return 0;}

    public void update(List<DataGioco> aggiornamento){
        giochi = aggiornamento;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null)
            view = LayoutInflater.from(context).inflate(R.layout.riga_gioco, parent,false);

        TextView titolo = (TextView)view.findViewById(R.id.titoloText);
        TextView genere = (TextView)view.findViewById(R.id.genereText);

        DataGioco g = giochi.get(position);
        titolo.setText(g.getTitolo());
        genere.setText(g.getGenere());
        return view;
    }

}
