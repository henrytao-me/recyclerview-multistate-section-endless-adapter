/*
 * Copyright 2015 "Henry Tao <hi@henrytao.me>"
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
import android.view.ViewGroup;
import android.widget.TextView;

import me.henrytao.me.sample.R;
import me.henrytao.recyclerview.old.RecyclerViewAdapter;

/**
 * Created by SILONG on 10/8/15.
 */
public class MergeAdapter extends RecyclerViewAdapter {

  public MergeAdapter(RecyclerView.Adapter... baseAdapters) {
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
