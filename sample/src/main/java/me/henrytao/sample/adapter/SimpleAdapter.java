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

package me.henrytao.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.me.sample.R;

/**
 * Created by henrytao on 8/16/15.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ItemHolder> {

  private List<Integer> mData;

  public SimpleAdapter() {
    mData = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      mData.add(i);
    }
  }

  public SimpleAdapter(int itemCount) {
    mData = new ArrayList<>();
    for (int i = 0; i < itemCount; i++) {
      mData.add(i);
    }
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  @Override
  public void onBindViewHolder(SimpleAdapter.ItemHolder holder, int position) {
    holder.bind(mData.get(position));
  }

  @Override
  public SimpleAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
  }

  public void addMoreItems(int numOfItems) {
    int n = getItemCount();
    for (int i = 0; i < numOfItems; i++) {
      mData.add(n++);
    }
    notifyDataSetChanged();
  }

  public static class ItemHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.title)
    TextView vTitle;

    public ItemHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(int data) {
      vTitle.setText(String.format(Locale.US, "Item %d", data));
    }
  }
}
