package com.kelly.registerform.utils;

import android.text.Editable;
import android.text.Html;

import org.xml.sax.XMLReader;

/**
 * Created by admision on 23/01/2018.
 */

public class UlTagHandler implements Html.TagHandler{
    @Override
    public void handleTag(boolean opening, String tag, Editable output,
                          XMLReader xmlReader) {
        if(tag.equals("ul") && !opening) output.append("\n");
        if(tag.equals("li") && opening) output.append("\n\t•");
    }
}
