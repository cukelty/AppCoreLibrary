package com.singlethread.rcore.widget.ImageView.transform;

/**
 * Copyright (C) 2015 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class CropSquareTransformation extends BitmapTransformation {

	private BitmapPool mBitmapPool;
	private int mWidth;
	private int mHeight;

	public CropSquareTransformation(Context context) {
		this(Glide.get(context).getBitmapPool());
	}

	public CropSquareTransformation(BitmapPool pool) {
		super(pool);
		this.mBitmapPool = pool;
	}

	@Override
	protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
		Bitmap source = toTransform;
		int size = Math.min(source.getWidth(), source.getHeight());

		mWidth = (source.getWidth() - size) / 2;
		mHeight = (source.getHeight() - size) / 2;

		Bitmap.Config config = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
		Bitmap bitmap = mBitmapPool.get(mWidth, mHeight, config);
		if (bitmap == null) {
			bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
		}

		return bitmap;
	}


	public String getId() {
		return "CropSquareTransformation(width=" + mWidth + ", height=" + mHeight + ")";
	}

//	@Override
//	public void updateDiskCacheKey(MessageDigest messageDigest) {
//		messageDigest.update(getId().getBytes(CHARSET));
//	}
}
