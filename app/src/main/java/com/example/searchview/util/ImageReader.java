package com.example.searchview.util;

import android.content.Context;
import android.widget.ImageView;

import com.google.android.gms.fido.fido2.api.common.RequestOptions;

public class ImageReader {
    public static void setCircleImage(Context context, ImageView image, String uri) {
        Glide.with(context)
                .load(uri)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.no_photo)
                        .error(R.drawable.no_photo)
                        .circleCrop())
                .into(image);
    }
}
