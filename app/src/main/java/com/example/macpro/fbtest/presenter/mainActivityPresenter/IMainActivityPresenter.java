package com.example.macpro.fbtest.presenter.mainActivityPresenter;


import com.facebook.AccessToken;

public interface IMainActivityPresenter {
   void getUserDetailsFromFB(AccessToken accessToken);
    boolean isLogged();
}
