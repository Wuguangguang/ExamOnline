package com.volcano.examonline.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.volcano.examonline.R;

/**
 * 类描述：一个方便在多种状态切换的Layout
 * 注意：只能包含一个子布局。
 * Created by wally on 2017/9/8.
 */
public class MultipleStatusLayout extends FrameLayout {

    private final static int LOAD_VIEW_INDEX = 0;
    private final static int RETRY_VIEW_INDEX = 1;
    private final static int EMPTY_VIEW_INDEX = 2;
    public final static int CONTENT_VIEW_INDEX = 3;

    private View mLoadingView;
    private View mRetryView;
    private View mContentView;
    private View mEmptyView;
    private LayoutInflater mInflater;

    private TextView tv_refresh;
    private View.OnClickListener mRetryListener;

    private Status mStatus = Status.Main;

    public MultipleStatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        mLoadingView = generateLoadingLayoutView();
        mRetryView = generateRetryLayoutView();
        mEmptyView = generateEmptyLayoutView();
        addView(mLoadingView, LOAD_VIEW_INDEX);
        addView(mRetryView, RETRY_VIEW_INDEX);
        addView(mEmptyView, EMPTY_VIEW_INDEX);
    }


    public MultipleStatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MultipleStatusLayout(Context context) {
        this(context, null);
    }

    public void showLoading() {
        showView(mLoadingView);
    }

    public void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    public void showRetry() {
        showView(mRetryView);
    }

    public void showContent() {
        showView(mContentView);
    }

    public void showEmpty() {
        showView(mEmptyView);

    }

    private void showView(View view) {
        if (view == null) return;

        if (view == mLoadingView) {
            this.mStatus = Status.Loading;
            mLoadingView.setVisibility(View.VISIBLE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mRetryView) {
            this.mStatus = Status.Net_Error;
            mRetryView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mEmptyView) {
            this.mStatus = Status.Empty;
            mEmptyView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
        } else if (view == mContentView) {
            this.mStatus = Status.Main;
            mContentView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        }
    }

    public View generateLoadingLayoutView() {
        View loadingView = mInflater.inflate(R.layout.include_anim_loading, null);
        return loadingView;
    }

    public View generateRetryLayoutView() {
        View retryView = mInflater.inflate(R.layout.include_load_error, null);
        tv_refresh = (TextView) retryView.findViewById(R.id.tv_refresh);
        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onRefreshBtnClick(v);
            }
        });
        return retryView;
    }

    public View generateEmptyLayoutView() {
        View emptyView = mInflater.inflate(R.layout.include_empty_view, null);
        return emptyView;
    }

    public void showError(OnClickListener onClickListener) {
        showRetry();
        tv_refresh.setOnClickListener(onClickListener);
    }

    public void onRefreshBtnClick(View view) {
        if (mRetryListener != null) {
            mRetryListener.onClick(view);
        }
    }

    public void setRetryListener(OnClickListener listener) {
        this.mRetryListener = listener;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public enum Status {
        Main,
        Loading,
        Net_Error,
        Empty
    }

}
