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

import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.henrytao.recyclerview.BaseAdapter.ItemViewType;

/**
 * Created by henrytao on 8/16/15.
 */
public interface MultiStateAdapter {

  void hideViewState(int tag);

  boolean isViewStateHidden(int tag);

  boolean isViewStateShowed(int tag);

  void onViewStateVisibilityChange(int tag, ItemViewType itemViewType, int index, @Visibility int visibility, int position);

  void setViewState(int tag, ItemViewType itemViewType, int index, @Visibility int initVisibility);

  void setViewStateVisibility(int tag, @Visibility int visibility);

  void showViewState(int tag);

  @IntDef({View.VISIBLE, View.GONE})
  @Retention(RetentionPolicy.SOURCE)
  @interface Visibility {

  }
}
