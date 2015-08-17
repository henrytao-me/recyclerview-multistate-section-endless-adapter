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

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by henrytao on 8/16/15.
 */
public abstract class RecyclerViewAdapter extends BaseAdapter implements EndlessAdapter {

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
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    if (mEndlessEnabled.get() && !mAppendingData.get() && mOnEndlessListener != null
        && position >= getItemCount() - 1 - getEndlessThreshold()) {
      mAppendingData.set(true);
      onReachThreshold();
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
}
