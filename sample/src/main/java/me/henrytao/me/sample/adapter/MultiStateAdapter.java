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

package me.henrytao.me.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.me.recyclerview.RecyclerViewAdapter;
import me.henrytao.me.sample.R;

/**
 * Created by henrytao on 8/16/15.
 */
public class MultiStateAdapter extends RecyclerViewAdapter implements me.henrytao.me.recyclerview.MultiStateAdapter {

  private static final int TAG_LOADING = 1;

  public MultiStateAdapter(RecyclerView.Adapter baseAdapter) {
    super(baseAdapter, 2, 0);
    setViewState(TAG_LOADING, ItemViewType.HEADER, 1, View.VISIBLE);
  }

  @Override
  public FooterHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return null;
  }

  @Override
  public HeaderHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    HeaderHolder holder = null;
    switch (index) {
      case 0:
        holder = new HeaderHolder(inflater.inflate(R.layout.holder_header, parent, false));
        holder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (isViewStateShowed(TAG_LOADING)) {
              hideViewState(TAG_LOADING);
            } else {
              showViewState(TAG_LOADING);
            }
          }
        });
        break;
      case 1:
        holder = new HeaderHolder(inflater.inflate(R.layout.holder_loading, parent, false));
        break;
    }
    return holder;
  }
}
