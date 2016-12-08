package com.melody.education.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.melody.education.R;

/**
 * Created by troy379 on 15.03.16.
 */
public final class BindingAdapters {
    private BindingAdapters() {
        throw new AssertionError();
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void setImage(ImageView view, String url) {
        if (!url.isEmpty())
            Glide.with(view.getContext())
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(view);
    }

    @BindingAdapter("android:src")
    public static void loadImage(ImageView view, int url) {
        Glide.with(view.getContext())
                .load(url)
                .override(500, 300)
                .into(view);
    }
}
