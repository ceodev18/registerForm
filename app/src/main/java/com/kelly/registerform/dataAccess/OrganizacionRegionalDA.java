package com.kelly.registerform.dataAccess;

import android.content.Context;

import com.kelly.registerform.model.OrganizacionRegional;
import com.kelly.registerform.model.TipoSocio;

import java.util.List;

/**
 * Created by italo on 22/04/18.
 */



public class OrganizacionRegionalDA {
    public static List<OrganizacionRegional> list=null;

    public OrganizacionRegionalDA(Context context){
        list = OrganizacionRegional.listAll(OrganizacionRegional.class);
    }
}
