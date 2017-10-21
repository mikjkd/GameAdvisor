package com.miche.gameadvisorprova3.View.AlertDialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataGiocoDettaglio;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fabio on 16/10/2017.
 */

public class AlertDialogVota extends AlertDialog.Builder {

    private Context context;
    private AlertDialog mAlertDialog;
    private DataUtente utente;
    private AuthenticationClass auth;
    private DataGiocoDettaglio dataGiocoDettaglio;

    public AlertDialogVota(@NonNull Context context, DataGiocoDettaglio dataGiocoDettaglio) {
        super(context);
        this.context=context;auth = AuthenticationClass.getInstance(this.context);
        utente= auth.getUtente();
        this.dataGiocoDettaglio = dataGiocoDettaglio;
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.voto_popup, null);
        final RatingBar rb = mView.findViewById(R.id.ratingBar2);
        final EditText etCommento = mView.findViewById(R.id.etCommento);
        Button AnnullaBtn = mView.findViewById(R.id.AnnullaBtn);
        Button OkBtn = mView.findViewById(R.id.OkBtn);

        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.vota(dataGiocoDettaglio,etCommento.getText().toString(),rb.getRating());
                mAlertDialog.dismiss();
            }
        });
        AnnullaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"La votazione è stata annullata",Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss();
            }
        });

        mBuilder.setView(mView);
        mAlertDialog= mBuilder.create();
        mAlertDialog.show();
        return mAlertDialog;
    }
}
