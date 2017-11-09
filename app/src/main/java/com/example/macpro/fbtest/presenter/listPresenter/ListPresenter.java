package com.example.macpro.fbtest.presenter.listPresenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.macpro.fbtest.model.data.ImageUrl;
import com.example.macpro.fbtest.tools.GreenDaoHelper;
import com.example.macpro.fbtest.tools.InternetConnection;
import com.example.macpro.fbtest.tools.SharedPreferencesHelper;
import com.example.macpro.fbtest.view.fragments.listFragment.IListFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.macpro.fbtest.tools.Config.USER_ID;

public class ListPresenter implements IListPresenter {

    private IListFragment mListFragment;
    private Context mContext;
    private List<ImageUrl> mImageUrlList;

    public ListPresenter(Context mContext, IListFragment listFragment) {
        this.mListFragment = listFragment;
        this.mContext = mContext;
        mImageUrlList= new ArrayList<>();
    }

    @Override
    public void getActivityNavigator(int position) {
        mListFragment.navigateToViewPagerFragment(position);
    }

    @Override
    public void getPhotos() {
        String id = SharedPreferencesHelper.getInstance().getStringValue(USER_ID);

        if(!GreenDaoHelper.getListMapPartners().isEmpty()){
            mListFragment.setData(GreenDaoHelper.getListMapPartners());
            if (!InternetConnection.internetConnectionChecking(mContext)){
                Log.d("2","no innet");
                return;

            }

        }
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + id + "/albums",
                null,
                HttpMethod.GET,
                response -> {
                    Log.d("2", response.toString());
                    try {
                        JSONArray jsonArray = response
                                .getJSONObject()
                                .getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            getPhoto(jsonArray
                                    .getJSONObject(i)
                                    .getString("id"), i == jsonArray.length() - 1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        ).executeAsync();
    }

    private void getPhoto(String id, final boolean isLastAlbum) {



        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + id + "/photos",
                parameters,
                HttpMethod.GET,
                response -> {
                    Log.d("5", response.getJSONObject().toString());

                    try {
                        JSONArray dataJSONArray =
                                response.getJSONObject()
                                        .getJSONArray("data");

                        if (dataJSONArray.length() != 0) {
                            for (int i = 0; i < dataJSONArray.length(); i++) {

                                String photoUrl = dataJSONArray
                                        .getJSONObject(i)
                                        .getJSONArray("images")
                                        .getJSONObject(0)
                                        .getString("source");

                                Log.d("6", photoUrl);

//                                    mPhotoUrlList.add(photoUrl);
                                mImageUrlList.add(new ImageUrl(photoUrl));

                                if (isLastAlbum && i == dataJSONArray.length() - 1) {
                                    GreenDaoHelper.updateDao(mImageUrlList);
                                    mListFragment.setData(mImageUrlList);
                                }
                            }

                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
        ).executeAsync();
    }
}