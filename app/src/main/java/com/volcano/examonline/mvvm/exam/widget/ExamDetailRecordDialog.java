package com.volcano.examonline.mvvm.exam.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.volcano.examonline.R;

public class ExamDetailRecordDialog extends Dialog {


    RecyclerView mRvRecord;
    Button mBtnSubmit;

    public ExamDetailRecordDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
    }

    public ExamDetailRecordDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exam_detail_record);
        setCanceledOnTouchOutside(false);
        initView();
        refreshView();
        initEvent();
    }

    private void refreshView() {
    }

    private void initEvent() {
    }

    private void initView() {
        mRvRecord = (RecyclerView) findViewById(R.id.rv_exam_detail_record);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }
}
