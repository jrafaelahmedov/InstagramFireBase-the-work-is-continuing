package com.example.rmaahmadov.instagramclone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.rmaahmadov.instagramclone.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends ArrayAdapter<String> {
    private Context mContex;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgUrls;

    public GridImageAdapter(Context context, int layoutResource, String append, ArrayList<String> imgUrls) {
        super(context, layoutResource, imgUrls);
        mContex = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResource = layoutResource;
        mAppend = append;
        this.imgUrls = imgUrls;
    }
    
    private static class ViewHolder{
        SquareImageView profileImage;
        ProgressBar mProgressbar;
        
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            convertView=mInflater.inflate(layoutResource,parent,false);
            holder=new ViewHolder();
            holder.mProgressbar=convertView.findViewById(R.id.gridImageProgressBar);
            holder.profileImage=convertView.findViewById(R.id.imageGridView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        String imgUrl=getItem(position);

        ImageLoader imageLoader =ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imgUrl, holder.profileImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(holder.mProgressbar!=null){
                    holder.mProgressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(holder.mProgressbar!=null){
                    holder.mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(holder.mProgressbar!=null){
                    holder.mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(holder.mProgressbar!=null){
                    holder.mProgressbar.setVisibility(View.GONE);
                }
            }

        });
     return convertView;
    }
}
