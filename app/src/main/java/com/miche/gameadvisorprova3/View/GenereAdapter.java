package com.miche.gameadvisorprova3.View;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGenere;
import com.miche.gameadvisorprova3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicola on 08/10/2017.
 */

public class GenereAdapter extends BaseAdapter {
    private final Context context;
    private List<DataGenere> generi = new ArrayList<DataGenere>();

    public GenereAdapter(Context context) { this.context = context; }

    public void update(List<DataGenere> aggiornamento){
        generi = aggiornamento;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {return generi.size();}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.provariga_genere,viewGroup,false);
        TextView titolo = (TextView)view.findViewById(R.id.textView);
        ImageView icoGenere = (ImageView)view.findViewById(R.id.ivGenere);

        DataGenere g = generi.get(i);
        titolo.setText(g.getKeyGenere());
        if(g.getPathlocale()!=null)
            icoGenere.setImageBitmap(BitmapFactory.decodeFile(g.getPathlocale()));
        return view;
    }

    @Override
    public DataGenere getItem(int i) { return generi.get(i); }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
