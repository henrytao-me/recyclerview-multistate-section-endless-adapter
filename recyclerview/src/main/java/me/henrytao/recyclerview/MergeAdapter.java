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
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.henrytao.recyclerview.adapter.ViewTypeManager;

/**
 * Created by henrytao on 5/30/16.
 */
public class MergeAdapter extends RecyclerView.Adapter {

  private List<AdapterDataObserver> mAdapterDataObservers = new ArrayList<>();

  private List<RecyclerView.Adapter> mAdapters = new ArrayList<>();

  private ViewTypeManager mViewTypeManager = new ViewTypeManager();

  public MergeAdapter(RecyclerView.Adapter... adapters) {
    for (int i = 0; i < adapters.length; i++) {
      addAdapter(adapters[i], false);
    }
  }

  @Override
  public int getItemCount() {
    int size = 0;
    int i = 0;
    for (int n = mAdapters.size(); i < n; i++) {
      size += mAdapters.get(i).getItemCount();
    }
    return size;
  }

  @Override
  public int getItemViewType(int position) {
    ViewTypeManager.Pointer info = getItemViewIndex(position);
    return info != null ? mViewTypeManager.encode(info.getType(), mAdapters.get(info.getType()).getItemViewType(info.getIndex())) : 0;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ViewTypeManager.Pointer info = getItemViewIndex(position);
    mAdapters.get(info.getType()).onBindViewHolder(holder, info.getIndex());
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTypeCode) {
    ViewTypeManager.Pointer info = mViewTypeManager.decode(viewTypeCode);
    return mAdapters.get(info.getType()).onCreateViewHolder(parent, info.getIndex());
  }

  public void addAdapter(RecyclerView.Adapter adapter) {
    addAdapter(adapter, true);
  }

  protected void addAdapter(RecyclerView.Adapter adapter, boolean shouldNotifyDataSetChanged) {
    mAdapters.add(adapter);
    AdapterDataObserver adapterDataObserver = new AdapterDataObserver(mAdapters.size() - 1) {

      @Override
      public void onChanged() {
        super.onChanged();
        notifyDataSetChanged();
      }

      @Override
      public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        notifyItemRangeChanged(getItemViewPosition(mAdapterIndex, positionStart), itemCount);
      }

      @Override
      public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        notifyItemRangeInserted(getItemViewPosition(mAdapterIndex, positionStart), itemCount);
      }

      @Override
      public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        notifyItemMoved(getItemViewPosition(mAdapterIndex, fromPosition), getItemViewPosition(mAdapterIndex, toPosition));
      }

      @Override
      public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        notifyItemRangeRemoved(getItemViewPosition(mAdapterIndex, positionStart), itemCount);
      }
    };

    mAdapterDataObservers.add(adapterDataObserver);
    adapter.registerAdapterDataObserver(adapterDataObserver);

    if (shouldNotifyDataSetChanged) {
      notifyDataSetChanged();
    }
  }

  protected ViewTypeManager.Pointer getItemViewIndex(int position) {
    int i = 0;
    int size = 0;
    int startIndex = 0;
    for (int n = mAdapters.size(); i < n; i++) {
      size += mAdapters.get(i).getItemCount();
      if (position < size) {
        break;
      }
      startIndex = size;
    }
    return new ViewTypeManager.Pointer(i, position - startIndex);
  }

  protected int getItemViewPosition(int adapterIndex, int itemIndex) {
    int position = itemIndex;
    int i = 0;
    for (int n = adapterIndex + 1; i < n; i++) {
      position += mAdapters.get(i).getItemCount();
    }
    return position;
  }

  protected static class AdapterDataObserver extends RecyclerView.AdapterDataObserver {

    protected final int mAdapterIndex;

    public AdapterDataObserver(int adapterIndex) {
      super();
      mAdapterIndex = adapterIndex;
    }
  }
}
