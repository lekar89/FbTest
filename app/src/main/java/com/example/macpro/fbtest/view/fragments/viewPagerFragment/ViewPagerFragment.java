package com.example.macpro.fbtest.view.fragments.viewPagerFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macpro.fbtest.R;
import com.example.macpro.fbtest.model.data.ImageUrl;
import com.example.macpro.fbtest.tools.CustomViewPager;
import com.example.macpro.fbtest.tools.GreenDaoHelper;
import com.example.macpro.fbtest.view.activitys.GalleryActivity;
import com.example.macpro.fbtest.view.fragments.listFragment.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment implements IViewPagerFragment {

    public static final String IMAGE_POSITION = "image_position";

    private int position;
    private CustomViewPager mViewPager;
    private List<ImageUrl> imageList;


    public static ViewPagerFragment newInstance(int position) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMAGE_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(IMAGE_POSITION);
        } else {
            position = 0;
        }
        imageList = GreenDaoHelper.getListMapPartners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        init(view);
        mViewPager.setAdapter(new ImagePagerAdapter(imageList, getContext()));
        mViewPager.setCurrentItem(position);
        return view;
    }


    @Override
    public void onBackPressed() {
        ( (GalleryActivity) getActivity()).navigatorBackPressed(ListFragment.newInstance());
    }

    @Override
    public String getCurrentTag() {
        return getClass().getName();
    }


    @Override
    public void init(View view) {
        mViewPager = view.findViewById(R.id.cviewpager);
    }

}
