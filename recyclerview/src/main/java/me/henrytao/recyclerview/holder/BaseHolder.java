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

package me.henrytao.recyclerview.holder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseHolder extends RecyclerView.ViewHolder {

  protected static View inflate(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutId) {
    return inflater.inflate(layoutId, parent, false);
  }

  public BaseHolder(View itemView) {
    super(itemView);
  }

  public BaseHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutId) {
    this(inflater, parent, layoutId, false);
  }

  public BaseHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutId, boolean isFillParent) {
    super(inflate(inflater, parent, layoutId));
    if (isFillParent && parent != null) {
      getItemView().getLayoutParams().height = parent.getMeasuredHeight() - (parent.getPaddingTop() + parent.getPaddingBottom());
    }
  }

  public View getItemView() {
    return itemView;
  }

  public void setOnClickListener(View.OnClickListener listener) {
    itemView.setOnClickListener(listener);
  }
}