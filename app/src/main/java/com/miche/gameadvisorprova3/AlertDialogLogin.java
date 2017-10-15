package com.miche.gameadvisorprova3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;

import static com.miche.gameadvisorprova3.R.id.AccediBtn;
import static com.miche.gameadvisorprova3.R.id.etEmail;
import static com.miche.gameadvisorprova3.R.id.etPsw;

/**
 * Created by Fabio on 15/10/2017.
 */

public class AlertDialogLogin extends AlertDialog.Builder {
    private Context context;
    private AlertDialog mAlertDialog;
    private AuthenticationClass auth = new AuthenticationClass();
    public AlertDialogLogin(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.login_popup,null);
        EditText mEmail = mView.findViewById(etEmail);
        EditText mPassword = mView.findViewById(etPsw);
        Button mAccedi = mView.findViewById(AccediBtn);

        auth.createListener(new AuthenticationClass.LoginUpdate() {
            @Override
            public void loginEffettuato() {
                Toast.makeText(context,"Login effettuato",Toast.LENGTH_LONG).show();
            }

            @Override
            public void loginNonEffettuato() {
                Toast.makeText(context,"Accesso come Ospite",Toast.LENGTH_LONG).show();
            }
        });

        mAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBuilder.setView(mView);
        mAlertDialog=mBuilder.create();
        mAlertDialog.show();
        return mAlertDialog;
    }

}
