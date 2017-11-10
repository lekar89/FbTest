package com.example.macpro.fbtest.view.fragments;

import android.view.View;

public interface IBaseFragment {

    void onBackPressed();

    String getCurrentTag();

    void init(View view);
}
