package com.example.weatherapp.binding

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.databinding.BindingAdapter
//import com.android.example.github.testing.OpenForTesting
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import java.lang.StringBuilder
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
//@OpenForTesting
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {

    @BindingAdapter(value = ["iconFromPath"], requireAll = false)
    fun bindIconFromPath(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            val url = "https://openweathermap.org/img/wn/$imageUrl@2x.png"
            Glide.with(fragment).load(url).apply(RequestOptions().centerCrop()).into(view)
        }
    }

    @BindingAdapter(value = ["imageFromPath", "imageRequestListener"], requireAll = false)
    fun bindImageFromPath(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(fragment).load(url).apply(RequestOptions().centerCrop().dontTransform()).into(imageView)
        }
    }
}

