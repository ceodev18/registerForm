package com.kelly.registerform.model.customviews;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.view.partner.WelcomePartnerActivity;

/**
 * Created by italo on 8/04/18.
 */

public class ToolbarView extends LinearLayout {
    private ImageView iv_toolbar_back,iv_out;
    private Activity activity;

    public ToolbarView(final Context context) {
        super(context);
        activity = (Activity)context;
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        LinearLayout layout = (LinearLayout) li.inflate(R.layout.custom_toolbar_activity, this, true);
        this.iv_toolbar_back = (ImageView) layout.findViewById(R.id.iv_toolbar_back);
        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });
        this.iv_out = (ImageView) layout.findViewById(R.id.iv_out);
        iv_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salir de la aplicación")
                        .setMessage("¿Estás seguro de querer salir de la aplicación?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Stop the activity

                                activity.moveTaskToBack(true);
                                Process.killProcess(Process.myPid());
                                System.exit(1);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    public ToolbarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        activity = (Activity)context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToolbarView);

        String toolbarTitle = a.getString(R.styleable.ToolbarView_toolbar_title);
        int supportImage = a.getInt(R.styleable.ToolbarView_toolbar_support_image,-1);

        toolbarTitle = (toolbarTitle == null) ? "" : toolbarTitle;

        String service = Context.LAYOUT_INFLATER_SERVICE;

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        LinearLayout layout = (LinearLayout) li.inflate(R.layout.custom_toolbar_activity, this, true);
        this.iv_toolbar_back = (ImageView) layout.findViewById(R.id.iv_toolbar_back);

        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        this.iv_out = (ImageView) layout.findViewById(R.id.iv_out);
        iv_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salir de la aplicación")
                        .setMessage("¿Estás seguro de querer salir de la aplicación?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Stop the activity
                                activity.moveTaskToBack(true);
                                Process.killProcess(Process.myPid());
                                System.exit(1);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        a.recycle();
    }

    public ToolbarView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        activity = (Activity)context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToolbarView);

        String toolbarTitle = a.getString(R.styleable.ToolbarView_toolbar_title);
        int supportImage = a.getInt(R.styleable.ToolbarView_toolbar_support_image,-1);

        toolbarTitle = (toolbarTitle == null) ? "" : toolbarTitle;

        String service = Context.LAYOUT_INFLATER_SERVICE;

        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        LinearLayout layout = (LinearLayout) li.inflate(R.layout.custom_toolbar_activity, this, true);
        this.iv_toolbar_back = (ImageView) layout.findViewById(R.id.iv_toolbar_back);


        this.iv_out = (ImageView) layout.findViewById(R.id.iv_out);
        iv_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salir de la aplicación")
                        .setMessage("¿Estás seguro de querer salir de la aplicación?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //close application
                               activity.moveTaskToBack(true);
                                Process.killProcess(Process.myPid());
                                System.exit(1);

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });
        a.recycle();
    }

    public ImageView getToolbarBack() {
        return iv_toolbar_back;
    }


    public void setToolbarTitle(String toolbarTitle) {

    }


    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void finishActivity(){
        activity.finish();
    }
}
