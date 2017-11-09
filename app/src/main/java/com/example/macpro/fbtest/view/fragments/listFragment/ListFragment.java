package com.example.macpro.fbtest.view.fragments.listFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macpro.fbtest.R;
import com.example.macpro.fbtest.presenter.listPresenter.IListPresenter;
import com.example.macpro.fbtest.presenter.listPresenter.ListPresenter;
import com.example.macpro.fbtest.view.activitys.GalleryActivity;
import com.example.macpro.fbtest.view.fragments.viewPagerFragment.ViewPagerFragment;

import java.util.List;


public class ListFragment extends Fragment implements IListFragment {

    private RecyclerView mRvListImages;
    private IListPresenter mPresenter;
    //    private List<String> list;
    private ImageListAdapter mAdapter;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = new ListPresenter(getContext(), this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        init(view);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRvListImages.setHasFixedSize(true);
        mRvListImages.setLayoutManager(layoutManager);
        mPresenter.getPhotos();
        return view;
    }

    @Override
    public void onBackPressed() {
        getActivity().finish();
    }

    @Override
    public String getCurrentTag() {
        return getClass().getName();
    }

    @Override
    public void init(View view) {
        mRvListImages = view.findViewById(R.id.rv_list_images);
    }

    @Override
    public void navigateToViewPagerFragment(int i) {
        ViewPagerFragment f = ViewPagerFragment.newInstance(i);
        ((GalleryActivity) getActivity()).navigator(f, f.getCurrentTag());
    }

    @Override
    public void setData(List photoUrls) {
        mAdapter = new ImageListAdapter(getContext(), photoUrls, mPresenter);
        mRvListImages.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
