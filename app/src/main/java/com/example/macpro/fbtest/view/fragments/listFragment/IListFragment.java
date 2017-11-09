package com.example.macpro.fbtest.view.fragments.listFragment;

import com.example.macpro.fbtest.view.fragments.IBaseFragment;

import java.util.List;



public interface IListFragment extends IBaseFragment {
    void navigateToViewPagerFragment(int i);

    void setData(List photosUrl);
}
