package com.github.lxs.peep.image;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.github.lxs.peep.Constants;

import android.content.Context;

public class GlideCache implements GlideModule {

	@Override
	public void applyOptions(Context context, GlideBuilder builder) {
		builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
		// 设置磁盘缓存目录（和创建的缓存目录相同）
		String downloadDirectoryPath = Constants.IMG_CACHE_PATH;
		// 设置缓存的大小为100M
		int cacheSize = 100 * 1000 * 1000;
		builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize));
	}

	@Override
	public void registerComponents(Context arg0, Glide arg1) {

	}
}
