package me.henrytao.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.henrytao.me.sample.R;
import me.henrytao.recyclerview.RecyclerViewAdapter;

/**
 * Created by henrytao on 8/16/15.
 */
public class HeaderFooterAdapter extends RecyclerViewAdapter {

  public HeaderFooterAdapter(RecyclerView.Adapter baseAdapter) {
    super(baseAdapter, 1, 1);
  }

  @Override
  public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return new FooterHolder(inflater.inflate(R.layout.holder_footer, parent, false));
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return new HeaderHolder(inflater.inflate(R.layout.holder_header, parent, false));
  }
}
