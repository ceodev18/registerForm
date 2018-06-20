package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.SistemaRiego;
import com.kelly.registerform.model.certifications.TipoCertificadora;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class TipoCertificadoraDA {
    public static List<TipoCertificadora> list=null;

    public TipoCertificadoraDA(Context context){
        list = TipoCertificadora.listAll(TipoCertificadora.class);
    }
}
