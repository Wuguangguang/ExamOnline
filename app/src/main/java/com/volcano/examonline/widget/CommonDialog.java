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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.volcano.examonline.R;
import com.volcano.examonline.databinding.DialogSelectBinding;

import java.util.ArrayList;

public class CommonDialog extends Dialog{

    private Context mContext;
    private DialogSelectBinding binding;
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
        binding = DialogSelectBinding.inflate(LayoutInflater.from(mContext));
        setContentView(binding.getRoot());
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                dismiss();
            }
        });
        binding.rvSelectList.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvSelectList.setAdapter(mAdapter);
    }

    public void setDatas(ArrayList<String> target) {
        datas.clear();
        datas.addAll(target);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(CommonDialogOnItemClickListener listener) {
        mAdapter.setOnClickListener(listener);
    }
}

