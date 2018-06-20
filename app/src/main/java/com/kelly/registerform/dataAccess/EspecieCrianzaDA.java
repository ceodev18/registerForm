package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.EspecieCrianza;
import com.kelly.registerform.model.certifications.EmpresaCertificadora;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class EspecieCrianzaDA {
    public static List<EspecieCrianza> list=null;

    public EspecieCrianzaDA(Context context){
        list = EspecieCrianza.listAll(EspecieCrianza.class);
    }
}
