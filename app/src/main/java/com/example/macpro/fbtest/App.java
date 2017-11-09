package com.example.macpro.fbtest;

import android.app.Application;

import com.example.macpro.fbtest.model.data.DaoMaster;
import com.example.macpro.fbtest.model.data.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by San4o on 07.11.2017.
 */

public class App extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "partnerMap-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
