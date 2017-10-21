package com.miche.gameadvisorprova3.View.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.miche.gameadvisorprova3.R;
import com.miche.gameadvisorprova3.View.Adapter.CommentiAdapter;

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
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.show();
        return mAlertDialog;
    }


}
