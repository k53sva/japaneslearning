package com.melody.education.binding.fields;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.melody.education.BR;


/**
 * Created by troy379 on 22.03.16.
 */
public class RecyclerConfiguration extends BaseObservable {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemAnimator itemAnimator;
    private RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView.Adapter adapter;

    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager);
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    @Bindable
    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
    }

    @Bindable
    public RecyclerView.ItemAnimator getItemAnimator() {
        return itemAnimator;
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        notifyPropertyChanged(BR.itemAnimator);
    }

    @Bindable
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @BindingAdapter("app:configuration")
    public static void configureRecyclerView(RecyclerView recyclerView, RecyclerConfiguration configuration) {
        recyclerView.setLayoutManager(configuration.getLayoutManager());
        recyclerView.setItemAnimator(configuration.getItemAnimator());
        recyclerView.addItemDecoration(configuration.getItemDecoration());
        recyclerView.setAdapter(configuration.getAdapter());

    }
}
