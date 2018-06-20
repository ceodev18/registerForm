package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.certifications.EmpresaCertificadora;
import com.kelly.registerform.model.certifications.TipoCertificadora;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class EmpresaCertificadoraDA {
    public static List<EmpresaCertificadora> list=null;

    public EmpresaCertificadoraDA(Context context){
        list = EmpresaCertificadora.listAll(EmpresaCertificadora.class);
    }
}
