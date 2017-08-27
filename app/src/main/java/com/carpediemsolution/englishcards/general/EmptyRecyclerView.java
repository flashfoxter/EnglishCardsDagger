package com.carpediemsolution.englishcards.general;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Юлия on 18.08.2017.
 */
public class EmptyRecyclerView extends RecyclerView {

    @Nullable
    private View mEmptyView;

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void checkIfEmpty() {
        if (getAdapter().getItemCount() > 0) {
            showRecycler();
        } else {
            showEmptyView();
        }
    }

    public void setEmptyView(@NonNull View view) {
        mEmptyView = view;
    }

    @VisibleForTesting
    void showRecycler() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
    }

    @VisibleForTesting
    void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(VISIBLE);
        }
        setVisibility(GONE);
    }
}


