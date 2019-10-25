package com.example.os.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.os.R;
import com.example.os.model.Coment;

public class ComenteViewHolder extends RecyclerView.ViewHolder {


    private TextView tvUser;
    private TextView tvData;
    private TextView tvComent;
    public ComenteViewHolder(@NonNull View itemView) {
        super(itemView);

        tvUser = itemView.findViewById(R.id.tvUser_coment);
        tvComent = itemView.findViewById(R.id.tvComent_coment);
        tvData = itemView.findViewById(R.id.tvData_coment);
    }

    public void BindOnView(Coment coment) {
    }
}
