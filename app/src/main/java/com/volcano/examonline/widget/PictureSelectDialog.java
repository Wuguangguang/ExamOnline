package com.volcano.examonline.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.volcano.examonline.R;
import com.volcano.examonline.databinding.DialogSelectPhotoBinding;

public class PictureSelectDialog extends Dialog{

    private Context mContext;
    private DialogSelectPhotoBinding binding;

    public PictureSelectDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        initDialog();
    }


    /**
     * 初始化Dialog
     */
    public void initDialog() {
        Window win = getWindow();
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.main_menu_animStyle);
        win.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogSelectPhotoBinding.inflate(LayoutInflater.from(mContext));

//        View view = View.inflate(mContext, R.layout.dialog_select_photo, null);
        setContentView(binding.getRoot());
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                dismiss();
            }
        });
    }

    public void setTakePhotoListener(View.OnClickListener listener) {
        binding.tvTakePhoto.setOnClickListener(listener);
    }

    public void setTakePicListener(View.OnClickListener listener) {
        binding.tvTakePic.setOnClickListener(listener);
    }
}
