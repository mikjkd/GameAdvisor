package com.miche.gameadvisorprova3.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.DataGioco;
import com.miche.gameadvisorprova3.Model.DataGiocoDettaglio;
import com.miche.gameadvisorprova3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fabio on 12/10/2017.
 */

public class ELVAdapter extends BaseExpandableListAdapter{


    private static String Descrizione = "Descrizione";
    private static String Requisiti = "Requisiti";
    private static String Sviluppatore = "Sviluppatore";
    private static String Link = "Link";
    private static String ColoreBianco = "#FFFFFF";
    private static String ColoreGrigio = "#CFCFCF";
    private String[] groupName = {Descrizione, Requisiti,Sviluppatore, Link};
    private HashMap<String,String> child = new HashMap<>() ;
    private final Context context;
    DataGiocoDettaglio gioco;

    public ELVAdapter(Context context, DataGiocoDettaglio gioco) {
        this.gioco = gioco;
        child.put(Descrizione,gioco.getDescrizione());
        child.put(Requisiti,gioco.getRequisiti());
        child.put(Sviluppatore,gioco.getSviluppatore());
        child.put(Link,gioco.getLink());
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return 4;
    }

    @Override
    public int getChildrenCount(int childCount) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupName[groupPosition];
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return child.get(getGroup(groupPosition));
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
        txt.setTextColor(Color.parseColor(ColoreBianco));
        txt.setTextSize(25);
        return txt;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup parent) {

        TextView txt = new TextView(context);
        txt.setText(getChild(groupPosition, childPosition));
        txt.setPadding(100, 0, 0, 0);
        txt.setTextColor(Color.parseColor(ColoreGrigio));
        txt.setTextSize(20);
        return txt;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
