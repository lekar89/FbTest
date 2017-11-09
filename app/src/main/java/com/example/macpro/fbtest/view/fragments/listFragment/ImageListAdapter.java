package com.example.macpro.fbtest.view.fragments.listFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.macpro.fbtest.R;
import com.example.macpro.fbtest.model.data.ImageUrl;
import com.example.macpro.fbtest.presenter.listPresenter.IListPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder>  {

    private List<ImageUrl> mPhotos;
    private Context mContext;
    private IListPresenter mPresenter;

    public ImageListAdapter(Context context, List<ImageUrl> spacePhotos, IListPresenter mPresenter) {
        mContext = context;
        mPhotos = spacePhotos;
        this.mPresenter = mPresenter;
    }

    @Override
    public ImageListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View photoView = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(ImageListAdapter.MyViewHolder holder, int position) {

        String photo = mPhotos.get(position).getImageUrl();
        ImageView imageView = holder.mPhotoImageView;
        Picasso.with(mContext)
                .load(photo)
                .placeholder(R.drawable.com_facebook_favicon_blue)
                .error(R.drawable.com_facebook_favicon_blue)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return (mPhotos.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPhotoImageView;

        public MyViewHolder(View itemView) {

            super(itemView);
            mPhotoImageView = itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                mPresenter.getActivityNavigator(position);
            }
        }
    }
}

