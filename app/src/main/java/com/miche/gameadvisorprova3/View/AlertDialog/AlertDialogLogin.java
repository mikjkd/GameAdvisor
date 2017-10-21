package com.miche.gameadvisorprova3.View.AlertDialog;

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
import com.miche.gameadvisorprova3.R;

import static com.miche.gameadvisorprova3.R.id.AccediBtn;
import static com.miche.gameadvisorprova3.R.id.etEmail;
import static com.miche.gameadvisorprova3.R.id.etPsw;

/**
 * Created by Fabio on 15/10/2017.
 */

public class AlertDialogLogin extends AlertDialog.Builder {
    private Context context;
    private AlertDialog mAlertDialog;
    private DataUtente utente;
    private AuthenticationClass auth ;

    public AlertDialogLogin(@NonNull Context context) {
        super(context);
        this.context=context;
        auth = AuthenticationClass.getInstance(this.context);
        utente = auth.getUtente();
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.login_popup,null);
        final EditText mEmail = mView.findViewById(etEmail);
        final EditText mPassword = mView.findViewById(etPsw);
        TextView mOspite = mView.findViewById(R.id.tvOspite);
        Button mIscriviti = mView.findViewById(R.id.IscrivitiBtn);
        Button mAccedi = mView.findViewById(AccediBtn);

        auth.createListener(new AuthenticationClass.LoginUpdate() {
            @Override
            public void loginEffettuato() {
              //  Toast.makeText(context,"Effettuato login come: "+utente.getEmail(),Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss();
            }

            @Override
            public void loginNonEffettuato() {
                //Toast.makeText(context,"Accesso come Ospite",Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss();
            }

            @Override
            public void erroreAutenticazione() {
                Toast.makeText(context,"ERRORE LOGIN",Toast.LENGTH_LONG).show();
            }
        });

        mAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEmail.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty() ){
                    Toast.makeText(context,"Riempire i campi obbligatori",Toast.LENGTH_LONG).show();
                }
                else if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
                    auth.login(mEmail.getText().toString(), mPassword.getText().toString(), new AuthenticationClass.LoginUpdate() {
                        @Override
                        public void loginEffettuato() { mAlertDialog.dismiss(); }

                        @Override
                        public void loginNonEffettuato() {}

                        @Override
                        public void erroreAutenticazione() {
                            Toast.makeText(context,"I dati inseriti non sono corretti. Riprovare",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        mOspite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.logInAnonimo();
            }
        });

        mIscriviti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogLogon adl = new AlertDialogLogon(context);
                adl.show();
            }
        });
        mBuilder.setView(mView);
        mAlertDialog=mBuilder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
        return mAlertDialog;
    }



}
