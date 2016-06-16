package com.caibo.weidu.util;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.caibo.weidu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by snow on 2016/6/10.
 */
public class WDImageLoaderUtil {

    private static DisplayImageOptions config(int placeHolderId, ImageScaleType scaleType) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(scaleType)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .handler(new Handler());
        if(placeHolderId!=0){
            builder = builder.showImageForEmptyUri(placeHolderId).showImageOnFail(placeHolderId).showImageOnLoading(placeHolderId);
        }
        DisplayImageOptions options =builder.build();
        return options;
    }

    public static void displayImage(String uri, ImageView imageView, int placeHolderId) {

        if (imageView != null && uri != null) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(uri, imageView, config(placeHolderId, ImageScaleType.IN_SAMPLE_POWER_OF_2));
        }

    }


    private static DisplayImageOptions configWithoutCache(int placeHolderId, ImageScaleType scaleType){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .cacheOnDisk(false)
                .cacheInMemory(false)
                .imageScaleType(scaleType)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .handler(new Handler());
        if(placeHolderId!=0){
            builder = builder.showImageForEmptyUri(placeHolderId).showImageOnFail(placeHolderId).showImageOnLoading(placeHolderId);
        }
        DisplayImageOptions options =builder.build();
        return options;
    }

    public static void displayImageWithoutCache(String uri, ImageView imageView, int placeHolderId){

        if(imageView!=null && uri!=null){

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(uri, imageView, configWithoutCache(placeHolderId, ImageScaleType.NONE_SAFE));
        }
    }


    public static void loadImage(String uri, ImageLoadingListener imageLoadingListener){
        loadImage(uri, R.mipmap.account_image, imageLoadingListener);
    }

    public static void loadImage(String uri, int placeHolderId, ImageLoadingListener imageLoadingListener){

        if(uri!=null){
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.loadImage(uri, config(placeHolderId, ImageScaleType.NONE_SAFE), imageLoadingListener);
        }
    }
}
