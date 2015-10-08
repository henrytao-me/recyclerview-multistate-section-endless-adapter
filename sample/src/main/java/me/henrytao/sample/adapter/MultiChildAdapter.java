package me.henrytao.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import me.henrytao.me.sample.R;
import me.henrytao.recyclerview.RecyclerViewAdapter;

/**
 * Created by SILONG on 10/8/15.
 */
public class MultiChildAdapter extends RecyclerViewAdapter {

  public MultiChildAdapter(RecyclerView.Adapter... baseAdapters) {
    super(2, 0, baseAdapters);
  }

  @Override
  public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return null;
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    HeaderHolder holder = null;
    switch (index) {
      case 0:
        holder = new HeaderHolder(inflater, parent, R.layout.holder_header);
        break;
      case 1:
        holder = new HeaderHolder(inflater, parent, R.layout.holder_header);
        ((TextView) holder.getItemView().findViewById(R.id.title)).setText("This is another header");
        break;
    }
    return holder;
  }
}
