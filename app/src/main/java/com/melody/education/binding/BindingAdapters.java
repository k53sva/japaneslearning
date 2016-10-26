package com.melody.education.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
            Picasso.with(view.getContext())
                    .load(url)
                    .into(view);
    }

    @BindingAdapter("android:src")
    public static void loadImage(ImageView view, int url) {
        Picasso.with(view.getContext())
                .load(url)
                .resize(500, 300)
                .into(view);
    }
}
