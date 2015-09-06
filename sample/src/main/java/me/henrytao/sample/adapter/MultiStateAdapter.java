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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.henrytao.me.sample.R;
import me.henrytao.recyclerview.RecyclerViewAdapter;

/**
 * Created by henrytao on 8/16/15.
 */
public class MultiStateAdapter extends RecyclerViewAdapter {

  private static final int FOOTER_COUNT = 0;

  private static final int HEADER_COUNT = 4;

  private static final int INDEX_EMPTY = 1;

  private static final int INDEX_ERROR = 2;

  private static final int INDEX_HEADER = 3;

  private static final int INDEX_LOADING = 0;

  public MultiStateAdapter(RecyclerView.Adapter baseAdapter) {
    super(baseAdapter, HEADER_COUNT, FOOTER_COUNT);
    setHeaderViewState(R.id.tag_loading, INDEX_LOADING, View.GONE);
    setHeaderViewState(R.id.tag_empty, INDEX_EMPTY, View.GONE);
    setHeaderViewState(R.id.tag_error, INDEX_ERROR, View.GONE);
    setHeaderViewState(R.id.tag_header, INDEX_HEADER, View.GONE);
    showLoadingView();
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
    HeaderHolder holder = null;
    switch (index) {
      case INDEX_LOADING:
        holder = new HeaderHolder(inflater, parent, R.layout.holder_loading, true);
        ((TextView) holder.getItemView().findViewById(R.id.title)).setText("This is loading view. Click here to go to next state");
        holder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            showEmptyView();
          }
        });
        break;
      case INDEX_EMPTY:
        holder = new HeaderHolder(inflater, parent, R.layout.holder_empty, true);
        ((TextView) holder.getItemView().findViewById(R.id.title)).setText("This is empty view. Click here to go to next state");
        holder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            showErrorView();
          }
        });
        break;
      case INDEX_ERROR:
        holder = new HeaderHolder(inflater, parent, R.layout.holder_error, true);
        ((TextView) holder.getItemView().findViewById(R.id.title)).setText("This is error view. Click here to go to next state");
        holder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            showContentView();
          }
        });
        break;
      case INDEX_HEADER:
        holder = new HeaderHolder(inflater, parent, R.layout.holder_header);
        ((TextView) holder.getItemView().findViewById(R.id.title)).setText("This is header view. Click here to back to loading view");
        holder.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            showLoadingView();
          }
        });
        break;
    }
    return holder;
  }

  public void hideContentView() {
    setViewStateVisibility(R.id.tag_header, View.GONE);
    setBaseAdapterEnabled(false);
  }

  public void hideEmptyView() {
    setViewStateVisibility(R.id.tag_empty, View.GONE);
  }

  public void hideErrorView() {
    setViewStateVisibility(R.id.tag_error, View.GONE);
  }

  public void hideLoadingView() {
    setViewStateVisibility(R.id.tag_loading, View.GONE);
  }

  public void showContentView() {
    hideLoadingView();
    hideEmptyView();
    hideErrorView();
    setViewStateVisibility(R.id.tag_header, View.VISIBLE);
    setBaseAdapterEnabled(true);
  }

  public void showEmptyView() {
    hideLoadingView();
    hideErrorView();
    hideContentView();
    setViewStateVisibility(R.id.tag_empty, View.VISIBLE);
  }

  public void showErrorView() {
    hideLoadingView();
    hideEmptyView();
    hideContentView();
    setViewStateVisibility(R.id.tag_error, View.VISIBLE);
  }

  public void showLoadingView() {
    hideEmptyView();
    hideErrorView();
    hideContentView();
    setViewStateVisibility(R.id.tag_loading, View.VISIBLE);
  }
}
