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
import me.henrytao.recyclerview.config.Constants;
import me.henrytao.recyclerview.config.Visibility;

/**
 * Created by henrytao on 2/28/16.
 */
public abstract class RecyclerViewAdapter extends BaseAdapter implements MultiStateAdapter {

  private Map<Integer, Integer> mFooterStates = new HashMap<>();

  private Map<Integer, Integer> mHeaderStates = new HashMap<>();

  private List<OnVisibilityChangedListener> mOnVisibilityChangedListeners = new ArrayList<>();

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
    return (getVisibility(position, Constants.Type.HEADER) == View.GONE
        || getVisibility(getItemCount() - position - 1, Constants.Type.FOOTER) == View.GONE) ?
        ItemViewType.BLANK.getValue() : super.getItemViewType(position);
  }

  @Override
  public int getVisibility(int position) {
    @Visibility int visibility = getVisibility(getPosition(position, Constants.Type.HEADER), Constants.Type.HEADER);
    return visibility != View.VISIBLE ? visibility : getVisibility(getPosition(position, Constants.Type.FOOTER), Constants.Type.FOOTER);
  }

  @Override
  public int getVisibility(int index, Constants.Type type) {
    Map<Integer, Integer> states = getStates(type);
    if (states.containsKey(index)) {
      int visibility = states.get(index);
      return visibility == View.GONE ? View.GONE : (visibility == View.INVISIBLE ? View.INVISIBLE : View.VISIBLE);
    }
    return View.VISIBLE;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    try {
      holder.itemView.setVisibility(getVisibility(position) == View.VISIBLE ? View.VISIBLE : View.INVISIBLE);
      super.onBindViewHolder(holder, position);
    } catch (ClassCastException ignore) {
    }
  }

  @Override
  public void setVisibility(int position, @Visibility int visibility) {
    setVisibility(position, visibility, Constants.Type.HEADER);
  }

  @Override
  public void setVisibility(int index, @Visibility int visibility, Constants.Type type) {
    if (getVisibility(index, type) == visibility) {
      return;
    }
    getStates(type).put(index, visibility);
    notifyItemChanged(getPosition(index, type));
    onVisibilityChanged(index, visibility, type);
  }

  private int getPosition(int index, Constants.Type type) {
    return type == Constants.Type.HEADER ? index : getItemCount() - index - 1;
  }

  private Map<Integer, Integer> getStates(Constants.Type type) {
    return type == Constants.Type.HEADER ? mHeaderStates : mFooterStates;
  }

  private void onVisibilityChanged(int index, @Visibility int visibility, Constants.Type type) {
    int n = mOnVisibilityChangedListeners.size();
    for (int i = 0; i < n; i++) {
      mOnVisibilityChangedListeners.get(i).onVisibilityChanged(this, getPosition(index, type), visibility);
    }
  }
}
