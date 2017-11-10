package com.example.macpro.fbtest.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.macpro.fbtest.R;
import com.example.macpro.fbtest.presenter.mainActivityPresenter.IMainActivityPresenter;
import com.example.macpro.fbtest.presenter.mainActivityPresenter.MainActivityPresenter;
import com.example.macpro.fbtest.tools.SharedPreferencesHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private CallbackManager mCallbackManager;
    private IMainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginButton loginButton = findViewById(R.id.login_button);

        SharedPreferencesHelper.getInstance().initialize(this);
        mCallbackManager = CallbackManager.Factory.create();
        mPresenter = new MainActivityPresenter(this);

        if (mPresenter.isLogged()) {
            gotoGallery();
        }

        loginButton.setReadPermissions(Arrays.asList("email", "user_photos", "user_birthday", "public_profile"));
                loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        mPresenter.getUserDetailsFromFB(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Login", "cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("Login", exception.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void gotoGallery() {
        startActivity(new Intent(MainActivity.this, GalleryActivity.class));
        finish();
    }
}