package com.miche.gameadvisorprova3.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.miche.gameadvisorprova3.R;

import static android.app.PendingIntent.getActivity;

public class NuovoDettagliGioco extends AppCompatActivity {

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*expandableListView = (ExpandableListView) findViewById(R.id.ELVgioco);

        ELVAdapter adapter = new ELVAdapter(NuovoDettagliGioco.this);

        expandableListView.setAdapter(adapter); */
    }
}
