package com.volcano.examonline.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;

import com.volcano.examonline.R;
import com.volcano.examonline.databinding.DialogEditBinding;

public class EditDialog extends Dialog {

    private Context mContext;
    private DialogEditBinding binding;

    public EditDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        initDialog();
    }

    /**
     * 初始化Dialog
     */
    public void initDialog() {
        Window win = getWindow();
        win.setGravity(Gravity.CENTER);
        win.getDecorView().setPadding(30,0,30,0);
        win.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogEditBinding.inflate(LayoutInflater.from(mContext));
        setContentView(binding.getRoot());
    }

    public void setTitle(String s) {
        binding.tvEditDialogTitle.setText(s);
    }

    public void setContent(String s) {
        binding.tvEditDialogContent.setText(s);
        binding.tvEditDialogContent.setVisibility(View.VISIBLE);
    }

    public void setEtVisibility(int vis) {
        binding.etEditDialog.setText("");
        binding.etEditDialog.setVisibility(vis);
    }

    public String getEtContent() {
        return binding.etEditDialog.getText().toString();
    }

    public void setCancelListener(String s, View.OnClickListener listener) {
        binding.tvCancel.setText(s);
        binding.tvCancel.setOnClickListener(listener);
    }

    public void setSureListener(String s, View.OnClickListener listener) {
        binding.tvSure.setText(s);
        binding.tvSure.setOnClickListener(listener);
    }


}