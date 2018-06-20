package com.kelly.registerform.controllers;

import android.content.Context;

import com.kelly.registerform.dataAccess.DepartmentDA;
import com.kelly.registerform.dataAccess.ProvinceDA;

/**
 * Created by bethzabe on 21/02/2018.
 */



public class ProvinceController {
    private ProvinceDA provinceDA;

    public ProvinceController(Context context){
        provinceDA= new ProvinceDA(context);
    }

    public void getProvinces(){
        provinceDA.getProvinces();
    }


}