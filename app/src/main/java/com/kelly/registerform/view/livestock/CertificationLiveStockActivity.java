package com.kelly.registerform.view.livestock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kelly.registerform.R;
import com.kelly.registerform.view.commerce.InformationActivity;
import com.kelly.registerform.view.partner.ValidationActivity;
import com.kelly.registerform.view.transformation.ProcessActivity;

public class CertificationLiveStockActivity extends AppCompatActivity {
    private String list;
    private Context context;
    private Button b_next,b_back,b_file;
    private int VALOR_RETORNO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_live_stock);
        setElements();
        setActions();
    }
    private void setElements(){
        context=this;
        b_next = findViewById(R.id.b_next);
        list= getIntent().getStringExtra("list");
        b_back = findViewById(R.id.b_back);
        b_file= findViewById(R.id.b_file);
    }
    private void setActions(){
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("La salidad es: "+list);
                if(list.equals("")){
                    Intent intent = new Intent(context, ValidationActivity.class);
                    startActivity(intent);
                }else{
                    String []listCheck =list.split(",");
                    int ini = Integer.parseInt(listCheck[0]);
                    String newList = reList(list);
                    if(ini==3){
                        Intent i = new Intent(context,ProcessActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                    if(ini==4){
                        Intent i = new Intent(context,InformationActivity.class);
                        i.putExtra("list",newList);
                        startActivity(i);
                    }
                }
            }
        });
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        b_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);
            }
        });
    }
    private String reList(String listSplit){
        String []listCheck =listSplit.split(",");
        String out ="";
        if(listCheck.length==1){
            return "";
        }else{
            for(int i=1;i<listCheck.length;i++){
                out+=listCheck[i]+",";
            }
            return out;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO )) {
            //Procesar el resultado
            Uri uri = data.getData(); //obtener el uri content
        }
    }
}
