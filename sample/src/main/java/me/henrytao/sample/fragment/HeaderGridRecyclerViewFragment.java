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

package me.henrytao.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.me.sample.R;
import me.henrytao.sample.adapter.HeaderAdapter;
import me.henrytao.sample.adapter.SimpleAdapter;

public class HeaderGridRecyclerViewFragment extends Fragment {

  public static HeaderGridRecyclerViewFragment newInstance() {
    return new HeaderGridRecyclerViewFragment();
  }

  @Bind(android.R.id.list)
  RecyclerView vRecyclerView;

  private GridLayoutManager mGridLayoutManager;

  private HeaderAdapter mHeaderAdapter;

  private SimpleAdapter mSimpleAdapter;

  public HeaderGridRecyclerViewFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_simple, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mSimpleAdapter = new SimpleAdapter();
    mHeaderAdapter = new HeaderAdapter(mSimpleAdapter);
    mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
    mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        return mHeaderAdapter.isHeaderView(position) ? mGridLayoutManager.getSpanCount() : 1;
      }
    });
    vRecyclerView.setHasFixedSize(false);
    vRecyclerView.setLayoutManager(mGridLayoutManager);
    vRecyclerView.setAdapter(mHeaderAdapter);
  }
}
