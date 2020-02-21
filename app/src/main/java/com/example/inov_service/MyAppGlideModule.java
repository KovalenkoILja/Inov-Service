package com.example.inov_service;

import android.widget.ImageView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

  public static void LoadImageToView(String imgUrl, ImageView imgView) {
    if (!imgUrl.isEmpty())
      GlideApp.with(imgView)
          .load(imgUrl)
          .fitCenter()
          .diskCacheStrategy(DiskCacheStrategy.NONE)
          .into(imgView);
  }
}