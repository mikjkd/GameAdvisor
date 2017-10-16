package com.miche.gameadvisorprova3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataUtente;

import static com.miche.gameadvisorprova3.R.id.AccediBtn;
import static com.miche.gameadvisorprova3.R.id.etEmail;
import static com.miche.gameadvisorprova3.R.id.etPsw;
import static com.miche.gameadvisorprova3.R.id.up;
import static com.miche.gameadvisorprova3.R.id.utente;

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
        utente= new DataUtente(context);
        auth = new AuthenticationClass(utente);
    }
    public AlertDialogUtente(@NonNull Context context,DataUtente utente) {
        super(context);
        this.context=context;
        this.utente= utente;
        auth = new AuthenticationClass(utente);
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.utente_popup,null);
        final TextView mUtente = mView.findViewById(R.id.mUtente);
        Button mLog = mView.findViewById(R.id.logBtn);
        if(utente.isAutenticated()){
            mUtente.setText(utente.getEmail());
            mLog.setText("Logout");
        }
        else if(!utente.isAutenticated()){
            mUtente.setText("Ospite");
            mLog.setText("Login");
        }
        mLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.logout();
                AlertDialogLogin adl = new AlertDialogLogin(context,utente);
                mAlertDialog.dismiss();
                adl.show();
            }
        });

        mBuilder.setView(mView);
        mAlertDialog=mBuilder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        return mAlertDialog;
    }


}
