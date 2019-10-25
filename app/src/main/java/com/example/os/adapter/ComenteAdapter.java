package com.example.os.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.os.R;
import com.example.os.model.Coment;

import java.util.ArrayList;
import java.util.List;

public class ComenteAdapter extends RecyclerView.Adapter<ComenteViewHolder> {

    private static final String TAG = "ComenteAdapter";
    private List<Coment> mList = new ArrayList();

    public ComenteAdapter(List<Coment> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ComenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario,parent,false);
        return new ComenteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComenteViewHolder holder, int position) {
        //Coment coment = mList.get(position);
        Coment coment = new Coment();
        holder.BindOnView(coment);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}

