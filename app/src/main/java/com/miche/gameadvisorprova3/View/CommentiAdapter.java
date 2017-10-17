package com.miche.gameadvisorprova3.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miche.gameadvisorprova3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabio on 17/10/2017.
 */

public class CommentiAdapter extends BaseAdapter {

    private  final Context context;
    private List<String> commenti = new ArrayList<>();

    public CommentiAdapter(Context context) {
        this.context = context;
    }

    public void update(List<String> aggiornamento){

        commenti = aggiornamento;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return commenti.size();
    }

    @Override
    public Object getItem(int i) {
        commenti.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view==null)
            view = LayoutInflater.from(context).inflate(R.layout.riga_commento, parent, false);
        TextView commento = (TextView)view.findViewById(R.id.tvCommento);













        return view;
    }
}
