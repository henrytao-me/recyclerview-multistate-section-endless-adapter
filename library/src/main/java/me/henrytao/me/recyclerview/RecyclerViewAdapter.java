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

package me.henrytao.me.recyclerview;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by henrytao on 8/16/15.
 */
public abstract class RecyclerViewAdapter extends BaseAdapter implements EndlessAdapter, MultiStateAdapter {

  ConcurrentMap<Integer, ViewState> mViewState = new ConcurrentHashMap<>();

  private AtomicBoolean mAppendingData = new AtomicBoolean(false);

  private AtomicBoolean mEndlessEnabled = new AtomicBoolean(true);

  private int mEndlessThreshold = 0;

  private OnEndlessListener mOnEndlessListener;

  public RecyclerViewAdapter(RecyclerView.Adapter baseAdapter, int headerCount, int footerCount) {
    super(baseAdapter, headerCount, footerCount);
  }

  public RecyclerViewAdapter(RecyclerView.Adapter baseAdapter) {
    super(baseAdapter);
  }

  @Override
  public int getEndlessThreshold() {
    return mEndlessThreshold;
  }

  @Override
  public void setEndlessThreshold(int threshold) {
    mEndlessThreshold = threshold;
  }

  @Override
  public void hideViewState(int tag) {
    setViewStateVisibility(tag, View.GONE);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    if (mEndlessEnabled.get() && !mAppendingData.get() && mOnEndlessListener != null
        && position >= getItemCount() - 1 - getEndlessThreshold()) {
      mAppendingData.set(true);
      onReachThreshold();
    }

    int index = -1;
    if (isHeaderView(position)) {
      index = getHeaderViewIndex(position);
    } else if (isFooterView(position)) {
      index = getFooterViewIndex(position);
    }
    if (index >= 0) {
      for (Map.Entry<Integer, ViewState> entry : mViewState.entrySet()) {
        if (entry.getValue().isMatch(holder, index)) {
          entry.getValue().setHolder((BaseHolder) holder);
        }
      }
    }
  }

  @Override
  public void onNext() {
    mAppendingData.set(false);
  }

  @Override
  public void setEndlessEnabled(boolean enabled) {
    mEndlessEnabled.set(enabled);
  }

  @Override
  public void setOnEndlessListener(OnEndlessListener listener) {
    mOnEndlessListener = listener;
  }

  @Override
  public void setViewState(HeaderHolder holder, int index, int tag) {
    setViewState(holder, index, tag, View.GONE);
  }

  @Override
  public void setViewState(FooterHolder holder, int index, int tag) {
    setViewState(holder, index, tag, View.GONE);
  }

  @Override
  public void setViewState(HeaderHolder holder, int index, int tag, @Visibility int initVisibility) {
    setViewState((BaseHolder) holder, index, tag, initVisibility);
  }

  @Override
  public void setViewState(FooterHolder holder, int index, int tag, @Visibility int initVisibility) {
    setViewState((BaseHolder) holder, index, tag, initVisibility);
  }

  @Override
  public void setViewStateVisibility(int tag, @Visibility int visibility) {
    if (mViewState.containsKey(tag)) {
      mViewState.get(tag).setVisibility(visibility);
    }
  }

  @Override
  public void showViewState(int tag) {
    setViewStateVisibility(tag, View.VISIBLE);
  }

  protected void onReachThreshold() {
    new OnReachThresholdTask(mOnEndlessListener).execute();
  }

  protected void setViewState(BaseHolder holder, int index, int tag, @Visibility int initVisibility) {
    if (!mViewState.containsKey(tag)) {
      mViewState.put(tag, new ViewState(holder, index, initVisibility));
    } else {
      mViewState.get(tag).setHolder(holder);
    }
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

    private WeakReference<BaseHolder> mHolderWeakReference;

    private int mIndex;

    private ItemViewType mItemViewType;

    private int mVisibility;

    public ViewState(BaseHolder holder, int index, int visibility) {
      mIndex = index;
      mVisibility = visibility;
      setHolder(holder);
      refresh();
    }

    public BaseHolder getHolder() {
      return mHolderWeakReference.get();
    }

    public void setHolder(BaseHolder holder) {
      if (mHolderWeakReference != null) {
        mHolderWeakReference.clear();
      }
      mHolderWeakReference = new WeakReference<>(holder);
      mItemViewType = holder instanceof HeaderHolder ? ItemViewType.HEADER : (holder instanceof FooterHolder ? ItemViewType.FOOTER : null);
      refresh();
    }

    public boolean isMatch(RecyclerView.ViewHolder holder, int index) {
      return (holder instanceof HeaderHolder && mItemViewType == ItemViewType.HEADER && index == mIndex) ||
          (holder instanceof FooterHolder && mItemViewType == ItemViewType.FOOTER && index == index);
    }

    public void refresh() {
      setVisibility(mVisibility);
    }

    public void setVisibility(@Visibility int visibility) {
      mVisibility = visibility;
      if (getHolder() != null) {
        getHolder().setVisibility(mVisibility);
      }
    }
  }
}
