package com.ruth.articleapplication.utils;

import android.webkit.WebView;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingUtils {
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {

        if (url != null && !url.isEmpty()) {
            Picasso.get().load(url).into(imageView);
        }
        else imageView.setImageBitmap(null);
    }

    @BindingAdapter("loadUrl")
    public static void setWebviewUrl(WebView view, String url) {
        view.loadUrl(url);
    }
}
