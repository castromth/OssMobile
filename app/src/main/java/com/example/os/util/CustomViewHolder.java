package com.example.os.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
    }



    protected void setDrawable(Context c, ImageView iv,int drawableId){
        iv.setImageDrawable(ContextCompat.getDrawable(c,drawableId));
    }
}
