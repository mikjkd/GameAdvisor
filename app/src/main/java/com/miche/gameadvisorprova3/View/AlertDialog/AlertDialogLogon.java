package com.miche.gameadvisorprova3.View.AlertDialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.R;

/**
 * Created by miche on 15/10/2017.
 */

public class AlertDialogLogon extends AlertDialog.Builder {
    private Context context;
    private AlertDialog mAlertDialog;
    private DataUtente utente;

    private AuthenticationClass auth ;
    public AlertDialogLogon(@NonNull Context context) {
        super(context);
        this.context=context;
        auth = AuthenticationClass.getInstance(this.context);
        utente = auth.getUtente();
    }


    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.logon_popup,null);
        final EditText mEmail = mView.findViewById(R.id.etEmailLo);
        final EditText mPassword = mView.findViewById(R.id.etPswLo);
        final EditText mPasswordR = mView.findViewById(R.id.etPswLoR);
        Button mAnnulla = mView.findViewById(R.id.AnnullaBtn);
        Button mConferma = mView.findViewById(R.id.ConfermaBtn);

        auth.createListener(new AuthenticationClass.LoginUpdate() {
            @Override
            public void loginEffettuato() {
                //Toast.makeText(context,"Login effettuato",Toast.LENGTH_LONG).show();
                mAlertDialog.dismiss();
            }

            @Override
            public void loginNonEffettuato() {
                //Toast.makeText(context,"Accesso come Ospite",Toast.LENGTH_LONG).show();
                mAlertDialog.dismiss();
            }

            @Override
            public void erroreAutenticazione() {
                Toast.makeText(context,"ERRORE LOGIN",Toast.LENGTH_LONG).show();
            }
        });

        mConferma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(mEmail.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty() || mPasswordR.getText().toString().isEmpty()  ){
                    Toast.makeText(context,"Riempire i campi obbligatori",Toast.LENGTH_LONG).show();
                }
                else if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty() && !mPasswordR.getText().toString().isEmpty()){
                    if(mPassword.getText().toString().equals(mPasswordR.getText().toString()) ){
                        auth.signUp(mEmail.getText().toString(), mPassword.getText().toString(), new AuthenticationClass.LoginUpdate() {
                            @Override
                            public void loginEffettuato() { }

                            @Override
                            public void loginNonEffettuato() {}

                            @Override
                            public void erroreAutenticazione() {
                                Toast.makeText(context,"Errore registrazione: Utente già esistente",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(context,"Password non corrispondenti",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialog.dismiss();
            }
        });
        mBuilder.setView(mView);
        mAlertDialog=mBuilder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        return mAlertDialog;
    }

}
