package com.example.macpro.fbtest.presenter.mainActivityPresenter;


import android.os.Bundle;
import android.util.Log;

import com.example.macpro.fbtest.tools.SharedPreferencesHelper;
import com.example.macpro.fbtest.view.activitys.IMainActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.macpro.fbtest.tools.Config.USER_EMAIL;
import static com.example.macpro.fbtest.tools.Config.USER_ID;
import static com.example.macpro.fbtest.tools.Config.USER_NAME;
import static com.example.macpro.fbtest.tools.Config.USER_PHOTO_URL;
import static com.example.macpro.fbtest.tools.Config.USER_YEAR_OF_BIRTH;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivity mIMainActivity;

    public MainActivityPresenter(IMainActivity mIMainActivity) {
        this.mIMainActivity = mIMainActivity;
    }

    @Override
    public void getUserDetailsFromFB(AccessToken accessToken) {

        GraphRequest req = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("1", object.toString());
                SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance();
                try {

                    if (object.getString("id") != null) {
                        sph.putStringValue(USER_ID, object.getString("id"));
                    }
                    if (object.getString("email") != null) {
                        sph.putStringValue(USER_EMAIL, object.getString("email"));
                    }
                    if (object.getString("name") != null) {
                        sph.putStringValue(USER_NAME, object.getString("name"));
                    }
                    if (object.getString("name") != null) {
                        sph.putStringValue(USER_PHOTO_URL, object.getJSONObject("picture").getJSONObject("data").getString("url"));
                    }
                    if (object.getString("birthday") != null) {
                        String birthday = object.getString("birthday");
                        sph.putStringValue(USER_YEAR_OF_BIRTH, birthday.substring(birthday.lastIndexOf('/') + 1));
                    }

                } catch (JSONException e) {
                    Log.d(getClass().getName(), e.getMessage());
                }
                mIMainActivity.gotoGallery();
            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,birthday,picture.type(large)");
        req.setParameters(parameters);
        req.executeAsync();
    }

    @Override
    public boolean isLogged() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
