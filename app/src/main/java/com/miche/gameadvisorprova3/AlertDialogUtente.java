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

/**
 * Created by miche on 16/10/2017.
 */

public class AlertDialogUtente extends AlertDialog.Builder {
    private Context context;
    private AlertDialog mAlertDialog;
    private DataUtente utente;

    public AlertDialogUtente(@NonNull Context context) {
        super(context);
        this.context=context;
        utente= new DataUtente(context);
    }
    public AlertDialogUtente(@NonNull Context context,DataUtente utente) {
        super(context);
        this.context=context;
        this.utente= utente;
    }

    @Override
    public AlertDialog show() {/*
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.utente_popup,null);
        final TextView mUtente = mView.findViewById(R.id.mUtente);
        Button mLog = mView.findViewById(R.id.logBtn);

        mAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmail.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty() ){
                    Toast.makeText(context,"Riempire i campi obbligatori",Toast.LENGTH_LONG).show();
                }
                else if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
                }
            }
        });
        mOspite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mIscriviti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogLogon adl = new AlertDialogLogon(context,utente);
                adl.show();
            }
        });

        mBuilder.setView(mView);
        mAlertDialog=mBuilder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        return mAlertDialog;*/
        return null;
    }


}
