package com.example.rmaahmadov.instagramclone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.rmaahmadov.instagramclone.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class UniversalImageLoader {

    private static final int defoultImage = R.drawable.profile;
    private Context mContex;

    public UniversalImageLoader(Context mContex) {
        this.mContex = mContex;
    }

    public ImageLoaderConfiguration getConfig() {
        DisplayImageOptions defoultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(defoultImage)
                .showImageForEmptyUri(defoultImage)
                .showImageOnFail(defoultImage)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(mContex)
                .defaultDisplayImageOptions(defoultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        return configuration;
    }

    public static void setImage(String imgUrl, ImageView image, final ProgressBar progressBar, String append) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(append + imgUrl, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}
