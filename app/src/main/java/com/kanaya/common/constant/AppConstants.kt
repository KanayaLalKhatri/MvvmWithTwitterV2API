package com.kanaya.common.constant

import android.Manifest

class AppConstants {


    val permissionsArray = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    var lat:Double = 19.970169
    var long:Double =73.829849
    var avgLatIncrement= 0.105
    var avgLongIncrement= 0.101
}