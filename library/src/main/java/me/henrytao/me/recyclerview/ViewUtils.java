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

package me.henrytao.me.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by henrytao on 8/16/15.
 */
public class ViewUtils {

  public static View inflate(Context context, int layoutResId) {
    return inflate(context, layoutResId, null, false);
  }

  public static View inflate(Context context, int layoutResId, ViewGroup root) {
    return inflate(context, layoutResId, root, false);
  }

  public static View inflate(Context context, int layoutResId, ViewGroup root, boolean attachToRoot) {
    return context instanceof Activity ? ((Activity) context).getLayoutInflater().inflate(layoutResId, root, attachToRoot) : null;
  }
}
