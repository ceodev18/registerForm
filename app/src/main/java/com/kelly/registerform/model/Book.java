package com.kelly.registerform.model;

import com.orm.SugarRecord;

/**
 * Created by KELLY on 13/02/2018.
 */

public class Book extends SugarRecord {
    String title;
    String edition;

    public Book(){
    }

    public Book(String title, String edition){
        this.title = title;
        this.edition = edition;
    }
}
