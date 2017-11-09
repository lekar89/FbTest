package com.example.macpro.fbtest.tools;

import com.example.macpro.fbtest.App;
import com.example.macpro.fbtest.model.data.DaoSession;
import com.example.macpro.fbtest.model.data.ImageUrl;
import com.example.macpro.fbtest.model.data.ImageUrlDao;

import java.util.List;

public class GreenDaoHelper {

    public static List<ImageUrl> getListMapPartners(){
        DaoSession daoSession = App.getDaoSession();
        ImageUrlDao dao = daoSession.getImageUrlDao();
        return dao.loadAll();
    }

    public static void updateDao(List<ImageUrl> list){
        DaoSession daoSession = App.getDaoSession();
        ImageUrlDao dao = daoSession.getImageUrlDao();
        dao.deleteAll();
        dao.insertInTx(list);
    }
}
