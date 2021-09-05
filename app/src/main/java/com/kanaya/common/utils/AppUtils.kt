@file:Suppress("DEPRECATION")

package com.kanaya.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.textfield.TextInputEditText
import com.kanaya.common.constant.AppConstants
import com.kanaya.mvvmexample.R
import com.makeramen.roundedimageview.RoundedImageView
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class AppUtils() {
    private val appConstants =  AppConstants()
    fun getCurrentDay(): String {
        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        val dayOfTheWeek = sdf.format(d)
        return dayOfTheWeek
    }

    fun getScreenWidth(context: Context): Int {
        /*var screenSize = 0
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics.widthPixels
        val dpHeight = displayMetrics.heightPixels
        val dpWidth = displayMetrics.widthPixels
        screenSize = dpWidth*/
        return context.resources.displayMetrics.widthPixels
    }

    fun isNetworkAvailable(context: Context): Boolean {


        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    fun loadImage(
        context: Context,
        imageUrl: String,
        imageView: RoundedImageView,
        dummyImageId: Int = R.drawable.ic_heart, isFromBase64: Boolean = true
    ) {
        print("Image URL -> $imageUrl")
        if (imageUrl == "N/A" || imageUrl.isNullOrEmpty()) {
            imageView.run { setImageResource(dummyImageId) }
        } else {
            if (isFromBase64) {
                Glide.with(context).asBitmap()
                    .load(decode(imageUrl, DEFAULT))
                    .into(imageView)
            } else {
                try {
                    Glide.with(context).load(imageUrl).apply(
                        object : RequestOptions() {}.error(
                            dummyImageId
                        ).placeholder(dummyImageId).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                    )
                        .thumbnail(.05f)
                        .into(imageView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }
    }

    fun generateLat(): Double {
        appConstants.lat = appConstants.lat+appConstants.avgLatIncrement
        return appConstants.lat

    }
    fun generateLong(): Double {
        appConstants.long = appConstants.long+appConstants.avgLongIncrement
        return appConstants.long
    }
    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap =
            Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    fun networkAlert(
        mContext: Context,
        alertTitle: String = mContext.getString(R.string.alert_title),
        alertMessage: String = mContext.getString(R.string.exit_screen_alert),
        yesButtonText: String = mContext.getString(R.string.ok), isNetwork: Boolean = true,


        ) {

        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        val promptsView = LayoutInflater.from(mContext).inflate(R.layout.app_network_alert, null)
        alertDialogBuilder.setView(promptsView)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(false)
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.transparent)

        val yesButton: TextView = promptsView.findViewById(R.id.yesButton)
        val noNetworkImage: ImageView = promptsView.findViewById(R.id.noNetworkImage)
        val title: TextView = promptsView.findViewById(R.id.user_name)
        val alertMsg: TextView = promptsView.findViewById(R.id.alertMessage)
        if (isNetwork)
            noNetworkImage.visibility = View.VISIBLE
        else
            noNetworkImage.visibility = View.GONE
        title.text = alertTitle
        alertMsg.text = alertMessage
        yesButton.text = yesButtonText
        yesButton.setOnClickListener {

            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    var REQUEST_CHECK_SETTINGS = 20001


}