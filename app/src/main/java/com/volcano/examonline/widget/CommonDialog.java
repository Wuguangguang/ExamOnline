package com.volcano.examonline.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.volcano.examonline.R;
import com.volcano.examonline.databinding.DialogCommonBinding;
import com.volcano.examonline.util.ToastUtils;

import java.util.ArrayList;

import ezy.ui.layout.LoadingLayout;

public class CommonDialog extends Dialog{

    private Context mContext;
    private DialogCommonBinding binding;
    private CommonDialogAdapter mAdapter;
    private ArrayList<String> datas;

    public CommonDialog(@NonNull Context context) {
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
        datas = new ArrayList<>();
        mAdapter = new CommonDialogAdapter(mContext, datas);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogCommonBinding.inflate(LayoutInflater.from(mContext));
        setContentView(binding.getRoot());
        binding.rvSelectList.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvSelectList.setAdapter(mAdapter);
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                dismiss();
            }
        });
    }

    public void setDatas(ArrayList<String> target) {
        if(target == null || target.isEmpty()) {
            dismiss();
            Toast.makeText(mContext, "系统开小差，请点击重试", Toast.LENGTH_SHORT).show();
        }else {
            datas.clear();
            datas.addAll(target);
            mAdapter.notifyDataSetChanged();
        }
    }


    public void setOnItemClickListener(CommonDialogOnItemClickListener listener) {
        mAdapter.setOnClickListener(listener);
    }

}

