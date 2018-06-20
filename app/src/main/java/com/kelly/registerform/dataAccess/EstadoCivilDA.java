package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.EstadoCivil;
import com.kelly.registerform.model.OrganizacionRegional;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class EstadoCivilDA {
    public static List<EstadoCivil> list=null;

    public EstadoCivilDA(Context context){
        list = EstadoCivil.listAll(EstadoCivil.class);
    }
}
