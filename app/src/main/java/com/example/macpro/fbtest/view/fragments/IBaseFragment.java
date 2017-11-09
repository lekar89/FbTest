package com.example.macpro.fbtest.view.fragments;

import android.view.View;

/**
 * Created by San4o on 07.11.2017.
 */

public interface IBaseFragment {

    void onBackPressed();

    String getCurrentTag();

    void init(View view);
}
