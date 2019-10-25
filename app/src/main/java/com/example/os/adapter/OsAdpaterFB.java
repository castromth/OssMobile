package com.example.os.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.os.R;
import com.example.os.interfaces.OssRvListener;
import com.example.os.model.Os;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OsAdpaterFB extends FirebaseRecyclerAdapter<Os, OsViewHolder> {


    private OssRvListener mClickListener;


    public OsAdpaterFB(@NonNull FirebaseRecyclerOptions<Os> options, OssRvListener listener) {
        super(options);
        mClickListener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull OsViewHolder osViewHolder, int i, @NonNull Os os) {
        Os model = os;
        osViewHolder.BindOnView(os);
    }

    @NonNull
    @Override
    public OsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oss,parent,false);


        return new OsViewHolder(v,mClickListener);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        mClickListener.onOssDataChangeListener();
    }
}
