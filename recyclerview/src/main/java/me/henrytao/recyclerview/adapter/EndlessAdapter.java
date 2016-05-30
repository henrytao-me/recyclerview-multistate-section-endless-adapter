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

/**
 * Created by henrytao on 8/16/15.
 */
public interface EndlessAdapter {

  int getEndlessThreshold();

  void setEndlessThreshold(int threshold);

  /**
   * This should be called before new items are added to adapter
   */
  void onNext(int numberOfNewAddedItems);

  void setEndlessEnabled(boolean enabled);

  void setOnEndlessListener(OnEndlessListener listener);

  interface OnEndlessListener {

    void onReachThreshold(EndlessAdapter adapter);
  }
}
