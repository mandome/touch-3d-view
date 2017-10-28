package com.bruce.simple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bruce.touch.Touch3DView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruce on 2017/7/6.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;
    private Context mContext;

    private class ViewHolder extends RecyclerView.ViewHolder {

        private Button clickBtn;
        private Touch3DView myCardView;

        private ViewHolder(View v) {
            super(v);
            clickBtn = (Button) v.findViewById(R.id.click_btn);
            myCardView = v.findViewById(R.id.card_view);
        }
    }

    public ItemAdapter(Context context) {
        mContext = context;
        mItemList = new ArrayList<>();
    }

    public void setItemList(List<String> mItemList) {
        this.mItemList = mItemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String item = mItemList.get(position);
        ((ItemAdapter.ViewHolder) holder).clickBtn.setText(item);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

}
