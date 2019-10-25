package com.example.os.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.os.R;
import com.example.os.interfaces.OssRvListener;
import com.example.os.model.Os;

import java.util.List;

public class OsAdapter extends RecyclerView.Adapter<OsViewHolder> {

    private static final String TAG = "OsAdapter";
    private List<Os> mList;
    private OssRvListener mOssRvListener;


    public OsAdapter(List<Os> list){
        mList = list;
    }


    @NonNull
    @Override
    public OsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oss,parent,false);
        return new OsViewHolder(view, mOssRvListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Os model = mList.get(position);
        holder.BindOnView(model);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
    public void setOssRvClickListener(OssRvListener r) {
        this.mOssRvListener = r;
    }
    public void updateList(List<Os> list){
        mList = list;
        notifyDataSetChanged();
    }
    public void addNewItem(Os os){
        mList.add(os);
        notifyItemInserted(mList.size()-1);
    }
    public void addNewItens(List<Os> consultas){
        int i = mList.size()-1;
        mList.addAll(consultas);
        notifyItemRangeInserted(i,mList.size()-1-i);
    }
}
