package com.example.weatherapp.binding

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.widget.ImageViewCompat.setImageTintList
import com.google.android.material.textfield.TextInputEditText

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}