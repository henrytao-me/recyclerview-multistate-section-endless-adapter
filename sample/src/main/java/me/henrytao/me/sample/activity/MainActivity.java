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

package me.henrytao.me.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.henrytao.me.sample.R;
import me.henrytao.me.sample.adapter.HeaderAdapter;
import me.henrytao.me.sample.adapter.HeaderFooterAdapter;
import me.henrytao.me.sample.adapter.MaterialAdapter;
import me.henrytao.me.sample.adapter.MultipleHeaderAdapter;
import me.henrytao.me.sample.adapter.SimpleAdapter;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.recycler_view)
  RecyclerView vRecyclerView;

  private HeaderAdapter mHeaderAdapter;

  private HeaderFooterAdapter mHeaderFooterAdapter;

  private MaterialAdapter mMaterialAdapter;

  private MultipleHeaderAdapter mMultipleHeaderAdapter;

  private SimpleAdapter mSimpleAdapter;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_simple_recyclerview:
        setTitle(R.string.text_simple_recyclerview);
        vRecyclerView.setAdapter(mSimpleAdapter);
        return true;
      case R.id.action_material_recyclerview:
        setTitle(R.string.text_material_recyclerview);
        vRecyclerView.setAdapter(mMaterialAdapter);
        return true;
      case R.id.action_header_recyclerview:
        setTitle(R.string.text_header_recyclerview);
        vRecyclerView.setAdapter(mHeaderAdapter);
        return true;
      case R.id.action_header_footer_recyclerview:
        setTitle(R.string.text_header_footer_recyclerview);
        vRecyclerView.setAdapter(mHeaderFooterAdapter);
        return true;
      case R.id.action_multiple_header_recyclerview:
        setTitle(R.string.text_multiple_header_recyclerview);
        vRecyclerView.setAdapter(mMultipleHeaderAdapter);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mSimpleAdapter = new SimpleAdapter();
    mMaterialAdapter = new MaterialAdapter(mSimpleAdapter);
    mHeaderAdapter = new HeaderAdapter(mSimpleAdapter);
    mHeaderFooterAdapter = new HeaderFooterAdapter(mSimpleAdapter);
    mMultipleHeaderAdapter = new MultipleHeaderAdapter(mSimpleAdapter);

    vRecyclerView.setHasFixedSize(false);
    vRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    setTitle(R.string.text_simple_recyclerview);
    vRecyclerView.setAdapter(mSimpleAdapter);
  }
}
