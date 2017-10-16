package com.miche.gameadvisorprova3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataUtente;

/**
 * Created by Fabio on 16/10/2017.
 */

public class AlertDialogVota extends AlertDialog.Builder {

    private Context context;
    private AlertDialog mAlertDialog;
    private DataUtente utente;
    private AuthenticationClass auth;

    public AlertDialogVota(@NonNull Context context) {
        super(context);
        this.context = context;
        utente = new DataUtente(context);
        auth = new AuthenticationClass(utente);
    }
    public AlertDialogVota(@NonNull Context context,DataUtente utente) {
        super(context);
        this.context = context;
        this.utente = utente;
        auth = new AuthenticationClass(utente);
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.voto_popup, null);
        RatingBar rb = mView.findViewById(R.id.ratingBar2);
        EditText etCommento = mView.findViewById(R.id.etCommento);
        Button AnnullaBtn = mView.findViewById(R.id.AnnullaBtn);
        Button OkBtn = mView.findViewById(R.id.OkBtn);



        return null;
    }
}
