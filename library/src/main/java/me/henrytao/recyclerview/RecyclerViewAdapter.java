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

package me.henrytao.recyclerview;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by henrytao on 8/16/15.
 */
public abstract class RecyclerViewAdapter extends BaseAdapter implements EndlessAdapter, MultiStateAdapter {

  private boolean mAppendingData = false;

  private boolean mEndlessEnabled = true;

  private int mEndlessThreshold = 0;

  private OnEndlessListener mOnEndlessListener;

  private Map<Integer, ViewState> mViewState = new HashMap<>();

  public RecyclerViewAdapter(RecyclerView.Adapter baseAdapter, int headerCount, int footerCount) {
    super(baseAdapter, headerCount, footerCount);
  }

  public RecyclerViewAdapter(RecyclerView.Adapter baseAdapter) {
    super(baseAdapter);
  }

  public RecyclerViewAdapter() {
    super();
  }

  @Override
  public int getEndlessThreshold() {
    return mEndlessThreshold;
  }

  /**
   * Fetching next page listener will be called if recyclerView is scrolled to threshold item (count from bottom)
   */
  @Override
  public void setEndlessThreshold(int threshold) {
    mEndlessThreshold = threshold;
  }

  @Override
  public int getItemViewType(int position) {
    ItemViewType itemViewType = null;
    int index = -1;
    if (isFooterView(position)) {
      itemViewType = ItemViewType.FOOTER;
      index = getFooterViewIndex(position);
    } else if (isHeaderView(position)) {
      itemViewType = ItemViewType.HEADER;
      index = getHeaderViewIndex(position);
    }
    if (itemViewType != null) {
      for (ViewState viewState : mViewState.values()) {
        if (viewState.isMatch(itemViewType, index) && viewState.getVisibility() == View.GONE) {
          return ItemViewType.BLANK.getValue() * getChunkSize();
        }
      }
    }
    return super.getItemViewType(position);
  }

  @Override
  public void hideViewState(int tag) {
    setViewStateVisibility(tag, View.GONE);
  }

  @Override
  public boolean isViewStateHidden(int tag) {
    if (mViewState.containsKey(tag)) {
      return mViewState.get(tag).getVisibility() == View.GONE;
    }
    return true;
  }

  @Override
  public boolean isViewStateShowed(int tag) {
    if (mViewState.containsKey(tag)) {
      return mViewState.get(tag).getVisibility() == View.VISIBLE;
    }
    return false;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    if (mEndlessEnabled && !mAppendingData && mOnEndlessListener != null
        && position >= getItemCount() - 1 - getEndlessThreshold()) {
      mAppendingData = true;
      onReachThreshold();
    }
  }

  /**
   * This method will tell recyclerView to fetch next page
   */
  @Override
  public void onNext() {
    mAppendingData = false;
  }

  @Override
  public void onViewStateVisibilityChange(int tag, ItemViewType itemViewType, int index, @Visibility int visibility,
      int position) {
    notifyItemChanged(position);
  }

  @Override
  public void setEndlessEnabled(boolean enabled) {
    mEndlessEnabled = enabled;
  }

  @Override
  public void setOnEndlessListener(OnEndlessListener listener) {
    mOnEndlessListener = listener;
  }

  @Override
  public void setViewState(int tag, ItemViewType itemViewType, int index, @Visibility int initVisibility) {
    if ((itemViewType == ItemViewType.HEADER && index < getHeaderCount()) ||
        (itemViewType == ItemViewType.FOOTER && index < getFooterCount())) {
      mViewState.put(tag, new ViewState(itemViewType, index, initVisibility));
    }
  }

  @Override
  public void setViewStateVisibility(int tag, @Visibility int visibility) {
    if (mViewState.containsKey(tag)) {
      ViewState viewState = mViewState.get(tag);
      if ((viewState.getVisibility() == View.GONE && visibility == View.GONE) ||
          (viewState.getVisibility() == View.VISIBLE && visibility == View.VISIBLE)) {
        return;
      }
      viewState.setVisibility(visibility);
      int position = -1;
      if (viewState.getItemViewType() == ItemViewType.FOOTER) {
        position = getFooterViewPosition(viewState.getIndex());
      } else if (viewState.getItemViewType() == ItemViewType.HEADER) {
        position = getHeaderViewPosition(viewState.getIndex());
      }
      if (position >= 0 && position < getItemCount()) {
        onViewStateVisibilityChange(tag, viewState.getItemViewType(), viewState.getIndex(), visibility, position);
      }
    }
  }

  @Override
  public void showViewState(int tag) {
    setViewStateVisibility(tag, View.VISIBLE);
  }

  public boolean isAppendingData() {
    return mAppendingData;
  }

  protected void onReachThreshold() {
    new OnReachThresholdTask(mOnEndlessListener).execute();
  }

  private static class OnReachThresholdTask extends AsyncTask<Void, Void, Void> {

    WeakReference<OnEndlessListener> mOnEndlessListenerWeakReference;

    public OnReachThresholdTask(OnEndlessListener onEndlessListener) {
      mOnEndlessListenerWeakReference = new WeakReference<>(onEndlessListener);
    }

    @Override
    protected Void doInBackground(Void... params) {
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      OnEndlessListener onEndlessListener = mOnEndlessListenerWeakReference.get();
      if (onEndlessListener != null) {
        onEndlessListener.onReachThreshold();
      }
    }
  }

  public static class ViewState {

    private int mIndex;

    private ItemViewType mItemViewType;

    private int mVisibility;

    public ViewState(ItemViewType itemViewType, int index, int initVisibility) {
      mItemViewType = itemViewType;
      mIndex = index;
      mVisibility = initVisibility;
    }

    public int getIndex() {
      return mIndex;
    }

    public ItemViewType getItemViewType() {
      return mItemViewType;
    }

    public int getVisibility() {
      return mVisibility;
    }

    public void setVisibility(int visibility) {
      mVisibility = visibility;
    }

    public boolean isMatch(ItemViewType itemViewType, int index) {
      return itemViewType == mItemViewType && index == mIndex;
    }
  }
}
