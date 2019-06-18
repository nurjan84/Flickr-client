package com.nurzhan.flickr.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

class Utils {
    companion object{
        fun getProductSquareSize(activity: Activity, gap:Int, quantity:Int):Int{
            val  displayMetrics =  DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            val gapsSize = dpToPx(activity,gap)
            return (width-gapsSize)/quantity
        }
        fun dpToPx(context: Context, dp: Int):Int {
            val r = context.resources
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics).toInt()
        }
    }

}