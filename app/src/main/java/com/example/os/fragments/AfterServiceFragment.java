package com.example.os.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.os.R;
import com.example.os.util.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AfterServiceFragment extends CustomFragment {

    private static final String TAG = "AfterServiceFragment";


    private static final int REQUEST_CAMERA = 14491;
    private static final int SELECT_FILE = 13778;


    private ImageView ivImagem;
    private Button btImagePicker;
    private ConstraintLayout layout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_after_service, container, false);

        btImagePicker = v.findViewById(R.id.bt_imagem_picker);
        ivImagem = v.findViewById(R.id.iv_imagem_service);
        layout = v.findViewById(R.id.layout_afterservice);

        btImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btImagePicker.setClickable(false);
                imagePicker();
                 btImagePicker.setClickable(true);
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== getActivity().RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                ivImagem.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                ivImagem.setImageURI(selectedImageUri);
            }



        }
    }

    private void imagePicker(){
        final CharSequence[] items = {"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add imagem");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }else if(items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    startActivityForResult(intent, SELECT_FILE);

                }else if(items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

}
