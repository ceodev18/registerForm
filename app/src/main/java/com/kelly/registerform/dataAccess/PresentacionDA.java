package com.kelly.registerform.dataAccess;

import android.app.Presentation;
import android.content.Context;

import com.kelly.registerform.model.OrganizacionRegional;
import com.kelly.registerform.model.Presentacion;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */

public class PresentacionDA {
    public static List<Presentacion> list=null;

    public PresentacionDA(Context context){
        list = Presentacion.listAll(Presentacion.class);
    }
}
