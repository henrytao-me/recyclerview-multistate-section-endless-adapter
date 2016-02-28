/*
 * Copyright 2016 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henrytao.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.me.sample.R;
import me.henrytao.recyclerview.SimpleRecyclerViewAdapter;
import me.henrytao.recyclerview.holder.FooterHolder;
import me.henrytao.recyclerview.holder.HeaderHolder;

/**
 * Created by henrytao on 8/16/15.
 */
public class MultiStateAdapter extends SimpleRecyclerViewAdapter {

  private final OnItemClickListener mOnItemClickListener;

  public MultiStateAdapter(RecyclerView.Adapter baseAdapter, OnItemClickListener onItemClickListener) {
    super(baseAdapter);
    mOnItemClickListener = onItemClickListener;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.itemView.setTag(R.id.tag_position, position);
  }

  @Override
  public RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
    return new FooterHolder(inflater, parent, R.layout.holder_footer);
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
    return new HeaderHolder(inflater, parent, R.layout.holder_header);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mOnItemClickListener != null) {
          mOnItemClickListener.onClick(view, (int) view.getTag(R.id.tag_position));
        }
      }
    });
    return viewHolder;
  }

  public interface OnItemClickListener {

    void onClick(View view, int position);
  }
}
