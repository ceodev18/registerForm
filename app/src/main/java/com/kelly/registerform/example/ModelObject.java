package com.kelly.registerform.example;

import com.kelly.registerform.R;

/**
 * Created by KELLY on 27/01/2018.
 */

public enum ModelObject {

    RED(R.string.red, R.layout.page_farm1),
    BLUE(R.string.blue, R.layout.page_farm2),
    GREEN(R.string.green, R.layout.sample_page);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}