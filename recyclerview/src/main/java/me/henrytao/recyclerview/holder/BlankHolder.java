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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BlankHolder extends BaseHolder {

  public BlankHolder(View itemView) {
    super(itemView);
  }

  public BlankHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutId) {
    super(inflater, parent, layoutId);
  }

  public BlankHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutId, boolean isFillParent) {
    super(inflater, parent, layoutId, isFillParent);
  }
}