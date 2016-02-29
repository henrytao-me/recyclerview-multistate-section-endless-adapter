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

package me.henrytao.sample.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import me.henrytao.me.sample.R;
import me.henrytao.sample.fragment.EndlessFragment;
import me.henrytao.sample.fragment.EndlessReserveFragment;
import me.henrytao.sample.fragment.HeaderFooterFragment;
import me.henrytao.sample.fragment.InfoFragment;
import me.henrytao.sample.fragment.MultiStateFragment;
import me.henrytao.sample.fragment.NestedWrapperAdapterFragment;

public class MainActivity extends AppCompatActivity {

  private Fragment mFragment;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (onOptionsItemSelected(item.getItemId())) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    onOptionsItemSelected(R.id.action_header_footer_recyclerview);
  }

  protected boolean onOptionsItemSelected(@IdRes int id) {
    Fragment fragment = null;
    switch (id) {
      case R.id.action_info:
        setTitle(getString(R.string.text_info));
        fragment = InfoFragment.newInstance();
        break;
      case R.id.action_header_footer_recyclerview:
        setTitle(R.string.text_header_footer_recyclerview);
        fragment = HeaderFooterFragment.newInstance();
        break;
      case R.id.action_nested_wrapper_adapter:
        setTitle(R.string.text_nested_wrapper_adapter);
        fragment = NestedWrapperAdapterFragment.newInstance();
        break;
      case R.id.action_multi_state_adapter:
        setTitle(R.string.text_multi_state_adapter);
        fragment = MultiStateFragment.newInstance();
        break;
      case R.id.action_endless_adapter:
        setTitle(R.string.text_endless_adapter);
        fragment = EndlessFragment.newInstance();
        break;
      case R.id.action_endless_reverse_adapter:
        setTitle(R.string.text_endless_reverse_adapter);
        fragment = EndlessReserveFragment.newInstance();
        break;
    }
    if (fragment != null) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      if (mFragment != null) {
        transaction = transaction.remove(mFragment);
      }
      transaction.replace(R.id.fragment, fragment).commit();
      mFragment = fragment;
    }
    return false;
  }
}
