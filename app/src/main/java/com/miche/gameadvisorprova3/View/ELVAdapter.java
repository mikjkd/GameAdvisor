package com.miche.gameadvisorprova3.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabio on 12/10/2017.
 */

public class ELVAdapter extends BaseExpandableListAdapter{



    String[] groupName = {"Descrizione", "Requisiti", "Link"};


    Context context;
    private List<DataGioco> giochi = new ArrayList<>();

    public ELVAdapter(Context context) {this.context = context;}
    public void update(List<DataGioco> aggiornamento){
        giochi = aggiornamento;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return giochi.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        TextView txt = new TextView(context);
        txt.setText(groupName[i]);
        txt.setPadding(100, 0, 0, 0);
        txt.setTextColor(Color.BLUE);
        txt.setTextSize(20);
        return txt;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup parent) {


        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
