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

package me.henrytao.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.henrytao.recyclerview.RecyclerViewAdapter;

/**
 * Created by henrytao on 8/16/15.
 */
public class EndlessAdapter extends RecyclerViewAdapter {

  public EndlessAdapter(RecyclerView.Adapter baseAdapter) {
    super(0, 0, baseAdapter);
  }

  @Override
  public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int index) {

  }

  @Override
  public RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return null;
  }

  @Override
  public RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent, int index) {
    return null;
  }
}
