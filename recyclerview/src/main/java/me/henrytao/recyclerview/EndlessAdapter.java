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
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by henrytao on 10/23/16.
 */
public abstract class EndlessAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerViewAdapter {

  public abstract VH onCreateEndlessLoadingViewHolder(LayoutInflater inflater, ViewGroup parent);

  public EndlessAdapter(RecyclerView.Adapter baseAdapter) {
    super(0, 1, baseAdapter);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int index) {
    onBindEndlessLoadingViewHolder((VH) holder);
  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int index) {
  }

  @Override
  public RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return onCreateEndlessLoadingViewHolder(inflater, parent);
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return null;
  }

  public void onBindEndlessLoadingViewHolder(VH holder) {
  }
}
