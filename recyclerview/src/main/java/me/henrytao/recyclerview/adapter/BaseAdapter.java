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

package me.henrytao.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.henrytao.recyclerview.holder.BlankHolder;

/**
 * Created by henrytao on 2/27/16.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter {

  public abstract void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int index);

  public abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int index);

  public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent, int index);

  public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int index);

  private final int mFooterCount;

  private final int mHeaderCount;

  private RecyclerView.AdapterDataObserver mAdapterDataObserver;

  private RecyclerView.Adapter mBaseAdapter;

  private boolean mBaseAdapterEnabled = true;

  public BaseAdapter(int headerCount, int footerCount, RecyclerView.Adapter baseAdapter) {
    mHeaderCount = headerCount;
    mFooterCount = footerCount;
    setBaseAdapter(baseAdapter, false);
  }

  public BaseAdapter(RecyclerView.Adapter baseAdapter) {
    this(0, 0, baseAdapter);
  }

  @Override
  public int getItemCount() {
    return getBaseItemCount() + getHeaderCount() + getFooterCount();
  }

  @Override
  public int getItemViewType(int position) {
    if (isHeaderView(position)) {
      return (getHeaderViewIndex(position) << ItemViewType.CHUNK_SIZE) | ItemViewType.HEADER.getValue();
    } else if (isFooterView(position)) {
      return (getFooterViewIndex(position) << ItemViewType.CHUNK_SIZE) | ItemViewType.FOOTER.getValue();
    } else if (isItemView(position)) {
      return mBaseAdapter.getItemViewType(getItemViewIndex(position)) << ItemViewType.CHUNK_SIZE | ItemViewType.ITEM.getValue();
    }
    return ItemViewType.BLANK.getValue();
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isHeaderView(position)) {
      onBindHeaderViewHolder(holder, getHeaderViewIndex(position));
    } else if (isFooterView(position)) {
      onBindFooterViewHolder(holder, getFooterViewIndex(position));
    } else if (isItemView(position)) {
      mBaseAdapter.onBindViewHolder(holder, getItemViewIndex(position));
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    int viewIndex = viewType >> ItemViewType.CHUNK_SIZE;
    viewType = ~(viewIndex << ItemViewType.CHUNK_SIZE) & viewType;

    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    RecyclerView.ViewHolder viewHolder = null;
    switch (ItemViewType.getValue(viewType)) {
      case HEADER:
        viewHolder = onCreateHeaderViewHolder(layoutInflater, parent, viewIndex);
        break;
      case ITEM:
        viewHolder = mBaseAdapter.onCreateViewHolder(parent, viewIndex);
        break;
      case FOOTER:
        viewHolder = onCreateFooterViewHolder(layoutInflater, parent, viewIndex);
        break;
    }
    return viewHolder != null ? viewHolder : onCreateBlankViewHolder(layoutInflater, parent);
  }

  public int getBaseItemCount() {
    if (mBaseAdapter == null || !isBaseAdapterEnabled()) {
      return 0;
    }
    return mBaseAdapter.getItemCount();
  }

  public int getFooterCount() {
    return mFooterCount;
  }

  public int getHeaderCount() {
    return mHeaderCount;
  }

  public int getItemViewIndex(int position) {
    return isItemView(position) ? position - getHeaderCount() : -1;
  }

  public int getItemViewPosition(int index) {
    return getHeaderCount() + index;
  }

  public boolean isBaseAdapterEnabled() {
    return mBaseAdapterEnabled;
  }

  public void setBaseAdapterEnabled(boolean enabled) {
    if (enabled != isBaseAdapterEnabled()) {
      mBaseAdapterEnabled = enabled;
      notifyDataSetChanged();
    }
  }

  public boolean isBlankView(int position) {
    return position >= getItemCount();
  }

  public boolean isFooterView(int position) {
    return getFooterViewIndex(position) >= 0;
  }

  public boolean isHeaderView(int position) {
    return getHeaderViewIndex(position) >= 0;
  }

  public boolean isItemView(int position) {
    return !isHeaderView(position) && !isFooterView(position) && !isBlankView(position);
  }

  public RecyclerView.ViewHolder onCreateBlankViewHolder(LayoutInflater inflater, ViewGroup parent) {
    return new BlankHolder(new View(parent.getContext()));
  }

  protected int getFooterViewIndex(int position) {
    if (getFooterCount() == 0) {
      return -1;
    }
    int startPosition = getBaseItemCount() + getHeaderCount();
    int endPosition = startPosition + getFooterCount() - 1;
    if (position < startPosition || position > endPosition) {
      return -1;
    }
    return position - startPosition;
  }

  protected int getFooterViewPosition(int index) {
    if (getFooterCount() == 0 || index < 0 || index >= getFooterCount()) {
      return -1;
    }
    int startPosition = getBaseItemCount() + getHeaderCount();
    return startPosition + index;
  }

  protected int getHeaderViewIndex(int position) {
    if (getHeaderCount() == 0) {
      return -1;
    }
    int startPosition = 0;
    int endPosition = startPosition + getHeaderCount() - 1;
    if (position < startPosition || position > endPosition) {
      return -1;
    }
    return position;
  }

  protected int getHeaderViewPosition(int index) {
    if (getHeaderCount() == 0 || index < 0 || index >= getHeaderCount()) {
      return -1;
    }
    return index;
  }

  protected void setBaseAdapter(RecyclerView.Adapter baseAdapter, boolean notifyDataSetChanged) {
    if (mBaseAdapter != null && mAdapterDataObserver != null) {
      mBaseAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
    }
    mBaseAdapter = baseAdapter;
    mAdapterDataObserver = null;
    if (mBaseAdapter != null) {
      mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
          super.onChanged();
          if (isBaseAdapterEnabled()) {
            notifyDataSetChanged();
          }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
          super.onItemRangeChanged(positionStart, itemCount);
          if (isBaseAdapterEnabled()) {
            notifyItemRangeChanged(getItemViewPosition(positionStart), itemCount);
          }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
          super.onItemRangeInserted(positionStart, itemCount);
          if (isBaseAdapterEnabled()) {
            notifyItemRangeInserted(getItemViewPosition(positionStart), itemCount);
          }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
          super.onItemRangeMoved(fromPosition, toPosition, itemCount);
          if (isBaseAdapterEnabled()) {
            notifyItemMoved(getItemViewPosition(fromPosition), getItemViewPosition(toPosition));
          }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
          super.onItemRangeRemoved(positionStart, itemCount);
          if (isBaseAdapterEnabled()) {
            notifyItemRangeRemoved(getItemViewPosition(positionStart), itemCount);
          }
        }
      };
      mBaseAdapter.registerAdapterDataObserver(mAdapterDataObserver);
    }
    if (notifyDataSetChanged) {
      notifyDataSetChanged();
    }
  }

  public enum ItemViewType {
    BLANK(0), ITEM(1), HEADER(2), FOOTER(3);

    protected static int CHUNK_SIZE = 2;

    public static ItemViewType getValue(int value) {
      for (ItemViewType type : values()) {
        if (type.getValue() == value) {
          return type;
        }
      }
      return BLANK;
    }

    private final int mValue;

    ItemViewType(int value) {
      mValue = value;
    }

    public int getValue() {
      return mValue;
    }
  }
}
