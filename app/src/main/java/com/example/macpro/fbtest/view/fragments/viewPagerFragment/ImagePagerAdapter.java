package com.example.macpro.fbtest.view.fragments.viewPagerFragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.macpro.fbtest.R;
import com.example.macpro.fbtest.model.data.ImageUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


class ImagePagerAdapter extends PagerAdapter {
    private List<ImageUrl> mImageList;
    private Context mContext;

    ImagePagerAdapter(List<ImageUrl> imageList, Context mContext) {
        this.mImageList = imageList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return (null != mImageList) ? mImageList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        PhotoView photoView = new PhotoView(container.getContext());
        Picasso.with(mContext)
                .load(mImageList.get(position).getImageUrl())
                .placeholder(R.drawable.com_facebook_favicon_blue)
                .error(R.drawable.com_facebook_favicon_blue)
                .into(photoView);
        photoView.setMaximumScale(5.0F);
        photoView.setMediumScale(3.0F);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

