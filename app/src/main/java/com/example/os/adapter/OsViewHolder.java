package com.example.os.adapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.os.R;
import com.example.os.interfaces.OssRvListener;
import com.example.os.model.Os;
import com.example.os.ui.UIHelper;
import com.example.os.util.CustomViewHolder;



import de.hdodenhof.circleimageview.CircleImageView;

public class OsViewHolder extends CustomViewHolder implements View.OnClickListener {
    private static final String TAG = "OsViewHolder";

    private OssRvListener mOssRvListener;

    private View v;

    private TextView tvDesc;
    private TextView tvLocal;
    private TextView tvData;
    private TextView tvId;
    private ImageView ivState;
    private CircleImageView ivContrato;

    public OsViewHolder(@NonNull View itemView, OssRvListener clickListener) {
        super(itemView);
        v = itemView;
        v.setOnClickListener(this);
        mOssRvListener = clickListener;
        ivContrato = v.findViewById(R.id.iv_oss_contrato);
        tvDesc = itemView.findViewById(R.id.tv_oss_descri);
        tvData = itemView.findViewById(R.id.tv_oss_data);
        tvLocal = itemView.findViewById(R.id.tv_oss_local);
        tvId = itemView.findViewById(R.id.tv_oss_id);
        ivState = itemView.findViewById(R.id.iv_oss_state);
    }


    public void BindOnView(Os os){
        tvDesc.setText(os.getDesc());
        tvLocal.setText(os.getLocal());
        tvData.setText(os.getData());
        tvId.setText(os.getId());
        setContrato(os.getContrato());
        setOsStatus(os.getStatus(),ivState,v.getContext());
    }

    private void setContrato(String contrato) {
    }

    public void setOsStatus(int status, ImageView iv, Context c){
        switch (status){
            case 0:
                UIHelper.setDrawable(c,iv, R.drawable.ic_time);
                UIHelper.setColorTint(iv,c,R.color.colorTime);
                break;
            case 1:
                UIHelper.setDrawable(c,iv,R.drawable.ic_done);
                UIHelper.setColorTint(iv,c,R.color.colorGreen);
                break;
            case 2:
                UIHelper.setDrawable(c,iv,R.drawable.ic_parts);
                UIHelper.setColorTint(iv,c,R.color.colorBlue);
                break;
            case 3:
                UIHelper.setDrawable(c,iv,R.drawable.ic_cancel);
                UIHelper.setColorTint(iv,c,R.color.colorDivider);
                break;
            case 4:
                UIHelper.setDrawable(c,iv,R.drawable.ic_close);
                UIHelper.setColorTint(iv,c,R.color.colorBlack);
                break;
            default:
                break;
        }
    } 


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        if(mOssRvListener != null){
            mOssRvListener.onOssClickListener(view,getAdapterPosition());
        }
    }
}
