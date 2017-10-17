package com.miche.gameadvisorprova3;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miche.gameadvisorprova3.Model.AuthenticationClass;
import com.miche.gameadvisorprova3.Model.DataUtente;
import com.miche.gameadvisorprova3.View.CommentiAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miche on 17/10/2017.
 */

public class CommentiDialog  extends AlertDialog.Builder {
    private Context context;
    private AlertDialog mAlertDialog;
    private List<String> commenti;
    private CommentiAdapter commentiAdapter;
    public CommentiDialog(@NonNull Context context) {
        super(context);
        this.context=context;
        commenti= new ArrayList<>();
    }
    public CommentiDialog(@NonNull Context context,List<String> commenti) {
        super(context);
        this.context=context;
        this.commenti= commenti;
        commentiAdapter = new CommentiAdapter(context);
        commentiAdapter.update(commenti);
    }

    @Override
    public AlertDialog show() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.lista_commenti,null);
        mBuilder.setAdapter(commentiAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        mBuilder.setView(mView);
        mAlertDialog=mBuilder.create();
        //mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
        return mAlertDialog;
    }


}
