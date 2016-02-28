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

package me.henrytao.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.henrytao.recyclerview.adapter.BaseAdapter;
import me.henrytao.recyclerview.adapter.MultiStateAdapter;
import me.henrytao.recyclerview.config.Visibility;

/**
 * Created by henrytao on 2/28/16.
 */
public abstract class RecyclerViewAdapter extends BaseAdapter implements MultiStateAdapter {

  private List<OnVisibilityChangedListener> mOnVisibilityChangedListeners = new ArrayList<>();

  private Map<Integer, Integer> mStates = new HashMap<>();

  public RecyclerViewAdapter(int headerCount, int footerCount, RecyclerView.Adapter baseAdapter) {
    super(headerCount, footerCount, baseAdapter);
  }

  public RecyclerViewAdapter(RecyclerView.Adapter baseAdapter) {
    super(baseAdapter);
  }

  @Override
  public void addOnVisibilityChanged(OnVisibilityChangedListener onVisibilityChangedListener) {
    mOnVisibilityChangedListeners.add(onVisibilityChangedListener);
  }

  @Override
  public int getItemViewType(int position) {
    if (mStates.containsKey(position) && mStates.get(position) == View.GONE) {
      return ItemViewType.BLANK.getValue();
    }
    return super.getItemViewType(position);
  }

  @Override
  public int getVisibility(int position) {
    if (mStates.containsKey(position)) {
      int visibility = mStates.get(position);
      return visibility == View.GONE ? View.GONE : (visibility == View.INVISIBLE ? View.INVISIBLE : View.VISIBLE);
    }
    return View.VISIBLE;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    try {
      holder.itemView.setVisibility(mStates.containsKey(position) && mStates.get(position) == View.INVISIBLE ?
          View.INVISIBLE : View.VISIBLE);
      super.onBindViewHolder(holder, position);
    } catch (ClassCastException ignore) {
    }
  }

  @Override
  public void setVisibility(int position, @Visibility int visibility) {
    if (getVisibility(position) == visibility) {
      return;
    }
    mStates.put(position, visibility);
    notifyItemChanged(position);
    onVisibilityChanged(position, visibility);
  }

  private void onVisibilityChanged(int position, @Visibility int visibility) {
    int n = mOnVisibilityChangedListeners.size();
    for (int i = 0; i < n; i++) {
      mOnVisibilityChangedListeners.get(i).onVisibilityChanged(position, visibility);
    }
  }
}
