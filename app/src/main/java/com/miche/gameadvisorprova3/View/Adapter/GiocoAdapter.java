package com.miche.gameadvisorprova3.View.Adapter;

/**
 * Created by miche on 04/10/2017.
 */


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miche on 03/10/2017.
 */

public class GiocoAdapter extends BaseAdapter {

    private final Context context;
    private List<DataGioco> giochi = new ArrayList<DataGioco>();

    public  GiocoAdapter(Context context){
        this.context = context;
    }

    public void update(List<DataGioco> aggiornamento){
        giochi = aggiornamento;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {return giochi.size();}

    @Override
    public DataGioco getItem(int i) {return giochi.get(i);}

    @Override
    public long getItemId(int i) {return 0;}

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null)
            view = LayoutInflater.from(context).inflate(R.layout.provariga_gioco, parent,false);
        TextView titolo = (TextView)view.findViewById(R.id.textView2);
        TextView genere = (TextView)view.findViewById(R.id.textView);
        ImageView img = (ImageView)view.findViewById(R.id.ivIMG);
        DataGioco g = giochi.get(position);
        titolo.setText(g.getTitolo());
        genere.setText(g.getGenere());
        if(g.getUrlIconaLocale()!=null)
            img.setImageBitmap(BitmapFactory.decodeFile(g.getUrlIconaLocale()));
        return view;
    }

}
