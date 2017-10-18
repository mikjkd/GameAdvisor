package com.miche.gameadvisorprova3.View.AlertDialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.R;


/**
 * Created by miche on 16/10/2017.
 */

public class AlertDialogUtente extends AlertDialog.Builder {
    private Context context;
    private AlertDialog mAlertDialog;
    private DataUtente utente;
    private AuthenticationClass auth ;

    public AlertDialogUtente(@NonNull Context context) {
        super(context);
        this.context=context;
        utente= new DataUtente();
        auth = new AuthenticationClass(utente,context);
    }
    public AlertDialogUtente(@NonNull Context context,DataUtente utente) {
        super(context);
        this.context=context;
        this.utente= utente;
        auth = new AuthenticationClass(utente,context);
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.utente_popup,null);
        final TextView mUtente = mView.findViewById(R.id.mUtente);
        Button mLog = mView.findViewById(R.id.logBtn);
        Log.e("autenticato? ",utente.isAutenticated() ? "SI":"NO");
        mLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.logout();
                AlertDialogLogin adl = new AlertDialogLogin(context,utente);
                mAlertDialog.dismiss();
                adl.show();
            }
        });
        if(utente.isAutenticated()){
            Log.e("utente",utente.getEmail());
            mUtente.setText(utente.getEmail());
            mLog.setText("Logout");
        }
        else if(!utente.isAutenticated()){
            Log.e("utente",utente.getEmail());
            mUtente.setText("Ospite");
            mLog.setText("Login");
        }

        mBuilder.setView(mView);
        mAlertDialog=mBuilder.create();
        //mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        return mAlertDialog;
    }


}
