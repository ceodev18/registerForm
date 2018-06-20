package com.kelly.registerform.controllers;

import android.content.Context;

import com.kelly.registerform.dataAccess.DepartmentDA;
import com.kelly.registerform.utils.SaveDataBase;

/**
 * Created by KELLY on 20/02/2018.
 */

public class DeparmentController {
    private DepartmentDA departmentDA;

    public DeparmentController(Context context){
        departmentDA= new DepartmentDA(context);
    }

    public void getDepartment(){
        departmentDA.getDepartaments();
    }
}
