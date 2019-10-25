package com.example.os.ui;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.os.R;

public class UIHelper {

    private static final String TAG = "UIHelper";




    public static void setColorTint(ImageView iv, Context c, int color) {
        Log.d(TAG, "setColorTint: ");
        iv.setColorFilter(ContextCompat.getColor(c,color));
    }

    public static void setDrawable(Context c, ImageView iv, int drawableId){
        Log.d(TAG, "setDrawable");
        iv.setImageDrawable(ContextCompat.getDrawable(c,drawableId));
    }

}
